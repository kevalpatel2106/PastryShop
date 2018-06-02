/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.home

import android.arch.lifecycle.MutableLiveData
import com.kevalpatel2106.pastryshop.base.BaseViewModel
import com.kevalpatel2106.pastryshop.bin.Pages
import com.kevalpatel2106.pastryshop.repository.Repository
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

    internal val isLoadingPages = MutableLiveData<Boolean>()

    internal val errorLoadingPages = SingleLiveEvent<String>()

    init {
        // Initialize
        pages.value = ArrayList()
        isLoadingPages.value = false

        //Start loading the pages.
        loadPages()
    }

    /**
     * Load the list of [Pages] to display in the view. View can observe [pages] to get notify about
     * changes.
     */
    private fun loadPages() {
        val d = repository.getPages()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { isLoadingPages.value = true }
                .doOnError { isLoadingPages.value = false }
                .doOnNext { isLoadingPages.value = false }
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

}