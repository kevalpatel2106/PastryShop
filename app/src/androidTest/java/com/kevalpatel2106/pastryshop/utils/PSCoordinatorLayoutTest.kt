/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.utils

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.view.getMotionEvent
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Keval on 03/06/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(AndroidJUnit4::class)
class PSCoordinatorLayoutTest {

    @Test
    fun checkAllowTouchEvents() {
        val psCoordinatorLayout = PSCoordinatorLayout(InstrumentationRegistry.getTargetContext())

        // Check the initial state
        assertTrue(psCoordinatorLayout.allowTouchEvents)

        // Allow touch events
        psCoordinatorLayout.allowTouchEvents = false

        assertFalse(psCoordinatorLayout.onTouchEvent(getMotionEvent()))
    }
}