/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.home

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * Created by Keval on 01/06/18.
 *
 * @param margin desirable margin size in px between the views in the recyclerView
 * @param columns number of columns of the RecyclerView
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class CardListItemDecorator(private val margin: Int) : RecyclerView.ItemDecoration() {

    /**
     * Set different margins for the items inside the recyclerView: no top margin for the first row
     * and no left margin for the first column.
     */
    override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        outRect.top = margin
        outRect.bottom = margin

        //we only add top margin to the first row
        if (position <= 0) {
            outRect.left = margin
        }
        outRect.left = margin
    }
}