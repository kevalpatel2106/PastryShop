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
import com.kevalpatel2106.pastryshop.bin.HomeCards
import com.kevalpatel2106.pastryshop.repository.Repository
import com.kevalpatel2106.pastryshop.utils.RxSchedulers
import com.kevalpatel2106.pastryshop.utils.SingleLiveEvent
import com.kevalpatel2106.pastryshop.utils.recall
import javax.inject.Inject

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class HomeViewModel @Inject constructor(
        private val repository: Repository,
        private val rxSchedulers: RxSchedulers
) : BaseViewModel() {

    internal val cards = MutableLiveData<ArrayList<HomeCards>>()

    internal val isLoadingCards = MutableLiveData<Boolean>()

    internal val errorLoadingCards = SingleLiveEvent<String>()

    init {
        cards.value = ArrayList()
        isLoadingCards.value = false

        loadCards()
    }

    private fun loadCards() {
        repository.getData()
                .observeOn(rxSchedulers.main)
                .subscribeOn(rxSchedulers.network)
                .doOnSubscribe { isLoadingCards.value = true }
                .doAfterTerminate { isLoadingCards.value = false }
                .subscribe({
                    cards.value!!.clear()
                    cards.value!!.addAll(it)
                    cards.recall()
                }, {
                    errorLoadingCards.value = it.message
                })
    }

}