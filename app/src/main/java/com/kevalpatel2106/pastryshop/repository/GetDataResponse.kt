package com.kevalpatel2106.pastryshop.repository

import com.google.gson.annotations.SerializedName

data class GetDataResponse(

        @field:SerializedName("cards")
        val cards: ArrayList<CardsItem>,

        @field:SerializedName("background")
        val background: String
)