/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.testutils

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * Created by Keval on 21-Jul-17.
 * This is the test activity to load fragments inside it while running the instrumentation tests.
 */

class FragmentRuleActivity : AppCompatActivity() {

    companion object {
        const val CONTAINER_ID = 384
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val frameLayout = FrameLayout(this@FragmentRuleActivity)
        setContentView(frameLayout)

        frameLayout.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        frameLayout.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        frameLayout.id = CONTAINER_ID
    }
}
