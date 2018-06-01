/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevalpatel2106.pastryshop.R
import com.kevalpatel2106.pastryshop.bin.HomeCards
import kotlinx.android.synthetic.main.item_dashboard_card.view.*

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class HomeCardsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun create(parent: ViewGroup): HomeCardsViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_dashboard_card, parent, false)

            return HomeCardsViewHolder(view)
        }
    }

    fun bind(card: HomeCards, imageHeight : Int){
        itemView.title.text = card.title
        itemView.descr.text = card.description

        itemView.image.layoutParams.height = imageHeight
    }
}