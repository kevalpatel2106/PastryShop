package com.kevalpatel2106.testutils

import android.support.test.espresso.IdlingResource.ResourceCallback
import android.support.test.espresso.IdlingResource



/**
 * Created by Kevalpatel2106 on 04-Jun-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class ElapsedTimeIdlingResource(private val waitingTime: Long) : IdlingResource {
    private val startTime: Long = System.currentTimeMillis()
    private var resourceCallback: ResourceCallback? = null

    override fun getName(): String {
        return ElapsedTimeIdlingResource::class.java.name + ":" + waitingTime
    }

    override fun isIdleNow(): Boolean {
        val elapsed = System.currentTimeMillis() - startTime
        val idle = elapsed >= waitingTime
        if (idle) {
            resourceCallback!!.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(resourceCallback: ResourceCallback) {
        this.resourceCallback = resourceCallback
    }
}
