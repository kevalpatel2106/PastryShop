/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.main

import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.NoActivityResumedException
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.DrawerActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.UiDevice
import android.view.Gravity
import com.kevalpatel2106.pastryshop.R
import com.kevalpatel2106.pastryshop.home.HomeFragment
import com.kevalpatel2106.testutils.DrawableMatcher
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Keval on 03/06/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule<MainActivity>(MainActivity::class.java)


    @Test
    fun checkHomeFragmentLoaded() {
        val currentFragment = rule.activity.supportFragmentManager
                .findFragmentById(R.id.main_container)

        assertNotNull(currentFragment)
        assertTrue(currentFragment is HomeFragment)
    }

    @Test
    fun checkDrawerStateOnRotation() {
        val device = UiDevice.getInstance(getInstrumentation())

        onView(withId(R.id.main_drawer_layout)).perform(DrawerActions.open())
        assertTrue(rule.activity.main_drawer_layout.isDrawerOpen(Gravity.START))

        // Switch to landscape
        device.setOrientationLeft()
        assertTrue(rule.activity.main_drawer_layout.isDrawerOpen(Gravity.START))

        // Switch to portrait
        device.setOrientationRight()
        assertTrue(rule.activity.main_drawer_layout.isDrawerOpen(Gravity.START))
    }

    @Test
    fun checkDrawerHeader() {
        onView(withId(R.id.main_drawer_layout)).perform(DrawerActions.open())

        assertEquals(1, rule.activity.main_navigation_view.headerCount)

        onView(withId(R.id.drawer_header_name_tv))
                .check(matches(withText(rule.activity.getString(R.string.sample_user_display_name))))
        onView(withId(R.id.drawer_header_profile_iv))
                .check(matches(DrawableMatcher(R.drawable.ic_user)))
    }

    @Test
    fun checkOnBackPressed_WhenDrawerIsOpen() {
        onView(withId(R.id.main_drawer_layout)).perform(DrawerActions.open())

        Espresso.pressBack()

        // Drawer must be close
        assertFalse(rule.activity.main_drawer_layout.isDrawerOpen(Gravity.START))
        assertFalse(rule.activity.isFinishing)
    }

    @Test
    fun checkOnBackPressed_WhenDrawerIsClose() {
        onView(withId(R.id.main_drawer_layout)).perform(DrawerActions.close())

        try {
            Espresso.pressBack()
            assertTrue(rule.activity.isFinishing)
            fail("Activity didn't closed.")
        } catch (e: NoActivityResumedException) {
            // Test passed
        }
    }

    @Test
    fun checkDrawerToggleButton() {
        onView(withId(R.id.main_drawer_layout)).perform(DrawerActions.close())

        // Open the drawer
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
        assertTrue(rule.activity.main_drawer_layout.isDrawerOpen(Gravity.START))
    }
}