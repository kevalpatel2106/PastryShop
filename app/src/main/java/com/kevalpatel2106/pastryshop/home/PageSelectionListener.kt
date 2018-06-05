/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.home

import android.view.View
import com.kevalpatel2106.pastryshop.bin.Pages

/**
 * Created by Keval on 02/06/18.
 * Listener to get callbacks whenever any page is selected from the list.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal interface PageSelectionListener {

    /**
     * This callback will be called whenever any page from the pages list gets clicked. It provides
     * selected [page] info and [itemView] of the row.
     */
    fun onPageSelected(page: Pages, itemView: View)
}