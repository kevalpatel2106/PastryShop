/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.repository

import com.kevalpatel2106.pastryshop.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Keval on 02/06/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(JUnit4::class)
class RepoDiModuleTest {

    @Test
    fun checkEnableLogging() {
        assertEquals(BuildConfig.BUILD_TYPE == "debug", RepoDiModule().provideIsEnableLogging())
    }

    @Test
    fun checkProvideNetwork() {
        val testBaseUrl = "http://example.com/"
        val network = RepoDiModule().provideNetwork(testBaseUrl, true)

        // Validate the base url
        assertEquals(testBaseUrl, network.getRetrofitClient().baseUrl().toString())

        // Validate the logging flag
        val interceptors = network.okHttpClient.interceptors()
        Assert.assertEquals(1, interceptors.size)
        Assert.assertTrue(interceptors[0] is HttpLoggingInterceptor)
        Assert.assertEquals(HttpLoggingInterceptor.Level.BODY, (interceptors[0] as HttpLoggingInterceptor).level)
    }
}