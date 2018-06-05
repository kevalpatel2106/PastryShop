/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.utils

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.MotionEvent


/**
 * Created by Keval on 01/06/18.
 * Custom [CoordinatorLayout].
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class PSCoordinatorLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : CoordinatorLayout(context, attrs) {

    /**
     * True to allow [PSCoordinatorLayout] to respond user touch events else false.
     */
    var allowTouchEvents = true

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return allowTouchEvents && super.onTouchEvent(ev)
    }
}