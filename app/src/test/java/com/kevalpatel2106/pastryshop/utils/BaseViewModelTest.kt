/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.utils

import io.reactivex.Observable
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.TimeUnit

/**
 * Created by Keval on 02/06/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(JUnit4::class)
class BaseViewModelTest {

    class TestViewModel : BaseViewModel() {
        fun addNewDisposable() {
            addDisposable(Observable.timer(10, TimeUnit.MINUTES).subscribe())
        }

        fun forceClear() {
            onCleared()
        }
    }

    private lateinit var testViewModel: TestViewModel

    @Before
    fun setUp() {
        testViewModel = TestViewModel()
    }

    @After
    fun clear() {
        testViewModel.forceClear()
    }

    @Test
    fun checkAddDisposable() {
        testViewModel.addNewDisposable()
        assertEquals(1, testViewModel.compositeDisposable.size())
    }

    @Test
    fun checkClearDisposable() {
        testViewModel.addNewDisposable()
        assertEquals(1, testViewModel.compositeDisposable.size())

        testViewModel.forceClear()
        assertEquals(0, testViewModel.compositeDisposable.size())
    }

}