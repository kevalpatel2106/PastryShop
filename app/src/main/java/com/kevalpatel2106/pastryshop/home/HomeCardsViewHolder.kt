/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.home

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevalpatel2106.pastryshop.R
import com.kevalpatel2106.pastryshop.bin.Pages
import com.squareup.picasso.Picasso
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

    fun bind(page: Pages, imageHeight: Int, onClick: (page: Pages) -> Unit) {
        itemView.card_title_tv.text = page.title
        itemView.card_description_tv.text = page.description

        //Set image
        itemView.card_iv.layoutParams.height = imageHeight
        if (imageHeight > 0) {
            Picasso.get().load(page.image[0])
                    .resizeDimen(R.dimen.home_cards_image_max_height, R.dimen.home_cards_image_max_height)
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
                    .into(itemView.card_iv)
        }

        itemView.setOnClickListener { onClick.invoke(page) }

        //Set transition names
        with(itemView.context) {
            ViewCompat.setTransitionName(itemView.card_title_tv,
                    "${getString(R.string.transition_name_home_card_title)}_${page.id}")
            ViewCompat.setTransitionName(itemView.card_description_tv,
                    "${getString(R.string.transition_name_home_card_description)}_${page.id}")
            ViewCompat.setTransitionName(itemView.card_iv,
                    "${getString(R.string.transition_name_home_card_image)}_${page.id}")
            ViewCompat.setTransitionName(itemView.card,
                    "${getString(R.string.transition_name_home_card_view)}_${page.id}")
        }
    }
}