/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.repository.network

import com.google.gson.annotations.SerializedName
import com.kevalpatel2106.pastryshop.bin.Contact
import com.kevalpatel2106.pastryshop.bin.Pages

internal data class GetDataResponse(

        @field:SerializedName("pages")
        val pagesItem: ArrayList<PagesItem>,

        @field:SerializedName("contact")
        val contactInfo: ContactItem,

        @field:SerializedName("background")
        val background: String
)

internal data class PagesItem(

        @field:SerializedName("id")
        val id: Long,

        @field:SerializedName("image")
        val image: ArrayList<String>,

        @field:SerializedName("description")
        val description: String,

        @field:SerializedName("title")
        val title: String
) {

    fun toPage(): Pages {
        return Pages(id, title, description, image)
    }
}

internal data class ContactItem(

        @field:SerializedName("phone")
        val phone: String,

        @field:SerializedName("email")
        val email: String,

        @field:SerializedName("twitter")
        val twitter: String
) {

    fun toContact(): Contact {
        return Contact(phone, email, twitter)
    }
}