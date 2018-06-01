/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.utils

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
fun Fragment.showSnack(message: String,
                       actionName: String? = null,
                       actionListener: View.OnClickListener? = null,
                       duration: Int = Snackbar.LENGTH_SHORT) {

    if (activity == null) return

    val snackbar = Snackbar.make((activity!!.findViewById<ViewGroup>(android.R.id.content)).getChildAt(0),
            message, duration)

    actionName?.let {
        snackbar.setAction(actionName, actionListener)
        snackbar.setActionTextColor(context!!.getColorCompat(android.R.color.holo_orange_dark))
    }

    snackbar.show()
}