/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.home

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.kevalpatel2106.pastryshop.R
import com.kevalpatel2106.pastryshop.bin.Pages
import com.kevalpatel2106.pastryshop.repository.Repository
import com.kevalpatel2106.pastryshop.utils.BaseViewModel
import com.kevalpatel2106.pastryshop.utils.SingleLiveEvent
import com.kevalpatel2106.pastryshop.utils.recall
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created by Keval on 01/06/18.
 * View model for [HomeFragment]. This view model is responsible for loading the pages information
 * from the database or network.
 *
 * @param repository [Repository] to access the data from check or network.
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class HomeViewModel @Inject constructor(
        private val repository: Repository
) : BaseViewModel() {

    /**
     * [MutableLiveData] of [Pages] to display on the view. View can observe this to get notify
     * whenever the list changes.
     */
    internal val pages = MutableLiveData<ArrayList<Pages>>()

    /**
     * [MutableLiveData] of [Boolean]. View should block interaction with [pages] if the value is
     * set to true.
     */
    internal val isBlockUi = MutableLiveData<Boolean>()

    /**
     * [SingleLiveEvent] to notify about the error occurred while loading [pages] from the cache or
     * network.
     *
     * @see loadPages
     */
    internal val errorLoadingPages = SingleLiveEvent<String>()

    /**
     * [SingleLiveEvent] to notify about the error occurred while making call to the restaurant using
     * contact info.
     *
     * @see callRestaurant
     */
    internal val errorLoadingContact = SingleLiveEvent<String>()

    init {
        // Initialize
        pages.value = ArrayList()
        isBlockUi.value = false

        //Start loading the pages.
        loadPages()
    }

    /**
     * Load the list of [Pages] to display in the view. View can observe [pages] to get notify about
     * changes.
     * View can react on any errors occurred during reading the contact information by observing
     * [errorLoadingPages].
     * [isBlockUi] will remain true while method is reading pages from the cache or network. Once
     * pages are loaded [isBlockUi] will set to false.
     *
     * @see [Repository.getPages]
     */
    private fun loadPages() {
        val d = repository.getPages()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { isBlockUi.value = true }
                .doOnError { isBlockUi.value = true }
                .doOnNext { isBlockUi.value = false }
                .subscribe({

                    // Some changes occurred into the database
                    // Rebuild the pages list.
                    pages.value!!.clear()
                    pages.value!!.addAll(it)
                    pages.recall()
                }, {

                    // Something went wrong.
                    errorLoadingPages.value = it.message
                })

        addDisposable(d)
    }

    /**
     * Call the restaurant on the given number by opening the Dialer application available. View
     * can react on any errors occurred during reading the contact information by observing
     * [errorLoadingContact].
     *
     * @see [Repository.getContactInfo]
     */
    internal fun callRestaurant(context: Context) {
        repository.getContactInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${it.phone}")
                    }

                    // Check if the dialer app available?
                    if (intent.resolveActivity(context.packageManager) != null) {

                        // Open dialer
                        context.startActivity(intent)
                    } else {

                        // There is no application to make calls.
                        errorLoadingContact.value = context.getString(R.string.error_dialar_application)
                    }
                }, {

                    // Error occurred while reading the contact info.
                    errorLoadingContact.value = it.message
                })
    }
}