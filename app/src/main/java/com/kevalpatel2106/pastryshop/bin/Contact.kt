/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.bin

/**
 * Created by Keval on 02/06/18.
 * Bin that holds the contact information.
 *
 * @param phone Mobile number.
 * @param email Email address.
 * @param twitter Url of the twitter handler.
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
data class Contact(

        val phone: String,

        val email: String,

        val twitter: String
) {

    companion object {

        // Preference keys name.
        const val PREF_KEY_PHONE = "phone"
        const val PREF_KEY_EMAIL = "email"
        const val PREF_KEY_TWITTER = "twitter"
    }
}