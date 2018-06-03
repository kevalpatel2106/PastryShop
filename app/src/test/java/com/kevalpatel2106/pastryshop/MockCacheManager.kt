/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */

package com.kevalpatel2106.pastryshop

import com.kevalpatel2106.pastryshop.bin.Contact
import com.kevalpatel2106.pastryshop.bin.Pages
import com.kevalpatel2106.pastryshop.repository.db.PagesDao
import com.kevalpatel2106.pastryshop.utils.SharedPrefsProvider

/**
 * Created by Keval on 03/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class MockCacheManager(private val pagesDao: PagesDao,
                       private val sharedPrefsProvider: SharedPrefsProvider) {


    internal val testTitle = "test_title"
    internal val testsDescription = "test_description"
    internal val testImages = ArrayList<String>()

    internal val testPhone = "3726583269"
    internal val testEmail = "test@example.com"
    internal val testTwitter = "@kevalpatel2106"

    init {
        testImages.add("http://eample.com/image1.jgp")
        testImages.add("http://eample.com/image2.jgp")
    }


    /**
     * Fill the cache/database using the fake items.
     */
    internal fun fillCache() {
        clearCache()

        pagesDao.insert(Pages(
                id = 1,
                title = testTitle,
                description = testsDescription,
                image = testImages
        ))
        pagesDao.insert(Pages(
                id = 2,
                title = testTitle,
                description = testsDescription,
                image = testImages
        ))
        pagesDao.insert(Pages(
                id = 3,
                title = testTitle,
                description = testsDescription,
                image = testImages
        ))

        // Save the contact to the database
        sharedPrefsProvider.savePreferences(Contact.PREF_KEY_PHONE, testPhone)
        sharedPrefsProvider.savePreferences(Contact.PREF_KEY_EMAIL, testEmail)
        sharedPrefsProvider.savePreferences(Contact.PREF_KEY_TWITTER, testTwitter)
    }

    /**
     * Clear the cache with database and shared preference.
     */
    internal fun clearCache() {
        pagesDao.nukeTable()
        sharedPrefsProvider.nukePrefrance()
    }
}