/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.utils

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Keval on 02/06/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(JUnit4::class)
class LiveDataExtKtTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var testLiveData: MutableLiveData<String>
    private val testString = "test"

    @Before
    fun setUp() {
        testLiveData = MutableLiveData()
        testLiveData.value = testString
    }

    @Test
    fun checkRecall() {
        var counter = 0
        val observer = Observer<String> {
            counter++
            assertEquals(testString, it)
        }
        testLiveData.observeForever(observer)

        testLiveData.recall()

        assertEquals(2, counter)
    }
}