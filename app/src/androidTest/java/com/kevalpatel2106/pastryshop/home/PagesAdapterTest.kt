/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.home

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.LinearLayout
import com.kevalpatel2106.pastryshop.R
import com.kevalpatel2106.pastryshop.bin.Pages
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Keval on 03/06/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(AndroidJUnit4::class)
class PagesAdapterTest {

    private val testTitle = "test_title"
    private val testsDescription = "test_description"
    private val testImages = ArrayList<String>()
    private val pages: ArrayList<Pages> = ArrayList()

    init {
        testImages.add("http://eample.com/image1.jgp")
        testImages.add("http://eample.com/image2.jgp")

        pages.add(Pages(
                id = 1,
                title = testTitle,
                description = testsDescription,
                image = testImages
        ))
        pages.add(Pages(
                id = 2,
                title = testTitle,
                description = testsDescription,
                image = testImages
        ))
        pages.add(Pages(
                id = 3,
                title = testTitle,
                description = testsDescription,
                image = testImages
        ))
    }

    @Test
    fun checkSize() {
        val adapter = PagesAdapter(pages, object : PageSelectionListener {
            override fun onPageSelected(page: Pages, itemView: View) {
                // Do nothing
            }
        })

        assertEquals(pages.size, adapter.itemCount)
    }

    @Test
    @UiThreadTest
    fun checkBind() {
        val adapter = PagesAdapter(pages, object : PageSelectionListener {
            override fun onPageSelected(page: Pages, itemView: View) {
                // Do nothing
            }
        })

        val holder = adapter
                .onCreateViewHolder(LinearLayout(InstrumentationRegistry.getTargetContext()), 0)
        adapter.imageHeight = 0
        adapter.bindViewHolder(holder, 0)

        assertEquals(pages[0].title,
                holder.itemView.findViewById<AppCompatTextView>(R.id.card_title_tv).text)
        assertEquals(pages[0].description,
                holder.itemView.findViewById<AppCompatTextView>(R.id.card_description_tv).text)
        assertEquals(0,
                holder.itemView.findViewById<AppCompatImageView>(R.id.card_iv).layoutParams.height)
    }

    @Test
    @UiThreadTest
    fun checkDisableItemClick() {
        var clickCount = 0
        val adapter = PagesAdapter(pages, object : PageSelectionListener {
            override fun onPageSelected(page: Pages, itemView: View) {
                clickCount++
            }
        })

        val holder = adapter
                .onCreateViewHolder(LinearLayout(InstrumentationRegistry.getTargetContext()), 0)
        adapter.imageHeight = 0
        adapter.bindViewHolder(holder, 0)

        // Enable click
        adapter.isClickEnabled = true
        holder.itemView.performClick()
        assertEquals(1, clickCount)     // Click count should increase

        // Reset click count
        clickCount = 0

        // Disable click
        adapter.isClickEnabled = false
        holder.itemView.performClick()
        assertEquals(0, clickCount)     // Click count should not change

    }
}