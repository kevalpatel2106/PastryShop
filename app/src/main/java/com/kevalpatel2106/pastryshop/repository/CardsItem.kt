package com.kevalpatel2106.pastryshop.repository

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import com.kevalpatel2106.pastryshop.bin.HomeCards

@Generated("com.robohorse.robopojogenerator")
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