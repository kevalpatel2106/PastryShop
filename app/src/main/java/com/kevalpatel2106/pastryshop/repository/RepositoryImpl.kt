/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.repository

import com.kevalpatel2106.pastryshop.bin.Contact
import com.kevalpatel2106.pastryshop.bin.Pages
import com.kevalpatel2106.pastryshop.repository.db.PagesDao
import com.kevalpatel2106.pastryshop.repository.network.Endpoint
import com.kevalpatel2106.pastryshop.repository.network.Network
import com.kevalpatel2106.pastryshop.utils.SharedPrefsProvider
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.exceptions.Exceptions

/**
 * Created by Keval on 01/06/18.
 *
 * @param network [Network] for  saving data into network
 * @param pagesDao [PagesDao] for saving data into database
 * @param sharedPrefsProvider [SharedPrefsProvider] for saving data into shared preferences
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class RepositoryImpl(private val network: Network,
                              private val pagesDao: PagesDao,
                              private val sharedPrefsProvider: SharedPrefsProvider) : Repository {

    override fun refreshData(): Single<ArrayList<Pages>> {

        return network.getRetrofitClient()
                .create(Endpoint::class.java)
                .getData()  // Make network request
                .map {

                    // Save the contact to the database
                    sharedPrefsProvider.savePreferences(Contact.PREF_KEY_PHONE, it.contactInfo.phone)
                    sharedPrefsProvider.savePreferences(Contact.PREF_KEY_EMAIL, it.contactInfo.email)
                    sharedPrefsProvider.savePreferences(Contact.PREF_KEY_TWITTER, it.contactInfo.twitter)


                    // Convert the response into the list of pages to save in database.
                    // Mark the updated time to the current UNIX milliseconds.
                    val updateTime = System.currentTimeMillis()
                    val pages = ArrayList<Pages>()

                    it.pagesItem.map { it.toPage() }
                            .forEach {
                                it.updateMills = updateTime
                                pages.add(it)
                            }

                    //Save to each page into database
                    pages.forEach {

                        if (pagesDao.getCardFromId(it.id) == null) {

                            // There is no page for this id present into the db.
                            // Insert new raw.
                            pagesDao.insert(it)
                        } else {

                            // Information for this page is already present.
                            // Update the card raw.
                            pagesDao.update(it)
                        }
                    }

                    // Delete all the old pages which where not returned from the server in the response.
                    if (pages.isNotEmpty()) {
                        pagesDao.deleteNotUpdatedCards(pages[0].updateMills)
                    }
                    return@map pages
                }
    }

    /**
     * Get the list of [Pages] to display in the view. This will load the cached data from the
     * database and emits the new result every time there are any changes made to the database. If
     * there are not any cached data into the database, this method will retrieve the data from the
     * server and cache it to the database for future use.
     *
     * @return [Observable] of list of [Pages]. Once the subscriber subscribes to this [Observable],
     * it will keep emitting whenever there are any changes into the data until the stream gets dispose.
     */
    override fun getPages(): Observable<ArrayList<Pages>> {
        return pagesDao.getCount()
                .map {

                    // Check the number of items in the database.
                    if (it == 0) {

                        // If the number of items are 0, database is not synced before.
                        // Make a network call to load the list of pages from the server.
                        refreshData().subscribe({
                            // Do nothing
                        }, {
                            // Propagate the exception.
                            // We are ending the stream.
                            Exceptions.propagate(it)
                        })

                        return@map true
                    }
                    return@map false
                }
                .flatMapObservable {
                    // Read all the pages from the database & start observing database changes.
                    pagesDao.getAllCards().toObservable()
                }
                .map { it as ArrayList<Pages> } //Map list as array list. (Room doesn't support array list as output type)
    }

    override fun getPage(pageId: Long): Observable<Pages> {
        return pagesDao.hasPage(pageId)
                .map {
                    if (it <= 0) {

                        // We don't have page for this id.
                        // It's rare case. But we should be prepare!!!
                        // Make a network call to load the list of pages from the server.
                        refreshData()
                    }
                    return@map it
                }
                .flatMapObservable {
                    pagesDao.observePage(pageId).toObservable()
                }
    }
}