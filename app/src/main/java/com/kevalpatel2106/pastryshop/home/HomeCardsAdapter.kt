/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.home

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.kevalpatel2106.pastryshop.bin.HomeCards

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class HomeCardsAdapter(private val cards: ArrayList<HomeCards>)
    : RecyclerView.Adapter<HomeCardsViewHolder>() {

    internal var imageHeight = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCardsViewHolder {
        return HomeCardsViewHolder.create(parent)
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: HomeCardsViewHolder, position: Int) {
        holder.bind(cards[position], imageHeight)
    }
}