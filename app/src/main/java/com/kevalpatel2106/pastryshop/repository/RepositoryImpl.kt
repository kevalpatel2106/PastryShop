/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.repository

import com.kevalpatel2106.pastryshop.bin.HomeCards
import com.kevalpatel2106.pastryshop.repository.db.CardsDao
import com.kevalpatel2106.pastryshop.repository.network.Endpoint
import com.kevalpatel2106.pastryshop.repository.network.Network
import io.reactivex.Observable

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class RepositoryImpl(private val network: Network,
                              private val cardsDao: CardsDao) : Repository {

    override fun refreshCards(): Observable<ArrayList<HomeCards>> {

        return network.getRetrofitClient()
                .create(Endpoint::class.java)
                .getData()  // Make network request
                .map {
                    // Convert the response into the list of cards to save in database.
                    // Mark the updated time to the current UNIX milliseconds.
                    val updateTime = System.currentTimeMillis()
                    val homeCards = ArrayList<HomeCards>()

                    it.cards.map { it.toHomeCards() }
                            .forEach {
                                it.updateMills = updateTime
                                homeCards.add(it)
                            }
                    return@map homeCards
                }
                .doOnNext {
                    //Save to each card into database
                    it.forEach {

                        if (cardsDao.getCardFromId(it.id) == null) {

                            // There is no card for this id present into the db.
                            // Insert new raw.
                            cardsDao.insert(it)
                        } else {

                            // Information for this card is already present.
                            // Update the card raw.
                            cardsDao.update(it)
                        }
                    }

                    // Delete all the old cards which where not returned from the server in the response.
                    if (it.isNotEmpty()) {
                        cardsDao.deleteNotUpdatedCards(it[0].updateMills)
                    }
                }
    }

    override fun getCards(): Observable<ArrayList<HomeCards>> {
        return cardsDao.getCount()
                .toObservable()
                .flatMap {

                    // Check the number of items in the database.
                    return@flatMap if (it == 0) {

                        // If the number of items are 0, database is not synced before.
                        // Make a network call to load the list of cards from the server
                        refreshCards().flatMap {

                            // Once the network call completes start observing the database changes;
                            cardsDao.getAllCards().toObservable()
                        }
                    } else {

                        // There are previously synced card into the database.
                        // Read all the cards from the database & start observing database changes.
                        cardsDao.getAllCards().toObservable()
                    }
                }
                .map { it as ArrayList<HomeCards> } //Map list as array list. (Room doesn't support array list as output type)
    }

}