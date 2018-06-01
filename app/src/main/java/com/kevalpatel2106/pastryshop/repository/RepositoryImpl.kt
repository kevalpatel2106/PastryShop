/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.repository

import com.kevalpatel2106.pastryshop.bin.HomeCards
import com.kevalpatel2106.pastryshop.repository.network.Endpoint
import com.kevalpatel2106.pastryshop.repository.network.Network
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class RepositoryImpl constructor(private val network: Network) : Repository {

    override fun getData(): Observable<ArrayList<HomeCards>> {
        return network.getRetrofitClient()
                .create(Endpoint::class.java)
                .getData()
                .map {
                    val  homeCards = ArrayList<HomeCards>()
                    for (cardItem in it.cards){
                        homeCards.add(cardItem.toHomeCards())
                    }
                    return@map homeCards
                }
    }

}