/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.repository.network

import com.google.gson.annotations.SerializedName
import com.kevalpatel2106.pastryshop.bin.HomeCards

data class GetDataResponse(

        @field:SerializedName("cards")
        val cards: ArrayList<CardsItem>,

        @field:SerializedName("background")
        val background: String
)

data class CardsItem(

        @field:SerializedName("image")
        val image: String,

        @field:SerializedName("description")
        val description: String,

        @field:SerializedName("title")
        val title: String
) {

        fun toHomeCards(): HomeCards {
                return HomeCards(title, description, image)
        }
}