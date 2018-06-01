/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.base

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class PSCoordinatorLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : CoordinatorLayout(context, attrs) {

    var isAllowForScroll = false

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        return isAllowForScroll && super.onStartNestedScroll(child, target, nestedScrollAxes)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return isAllowForScroll && super.onTouchEvent(ev)
    }
}