package com.kevalpatel2106.pastryshop.repository.network

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Keval on 05/06/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(JUnit4::class)
class PagesItemTest {
    internal val testTitle = "test_title"
    internal val testsDescription = "test_description"
    internal val testImages = ArrayList<String>()

    init {
        testImages.add("http://eample.com/image1.jgp")
        testImages.add("http://eample.com/image2.jgp")
    }

    @Test
    fun checkInit() {

        val pagesItem = PagesItem(
                id = 1L,
                title = testTitle,
                description = testsDescription,
                image = testImages
        )

        assertEquals(1L, pagesItem.id)
        assertEquals(testTitle, pagesItem.title)
        assertEquals(testsDescription, pagesItem.description)
        pagesItem.image.forEachIndexed { index, image -> assertEquals(testImages[index], image) }
    }

    @Test
    fun checkToPage() {

        val pagesItem = PagesItem(
                id = 1L,
                title = testTitle,
                description = testsDescription,
                image = testImages
        )

        val pages = pagesItem.toPage()

        assertEquals(1L, pages.id)
        assertEquals(testTitle, pages.title)
        assertEquals(testsDescription, pages.description)
        pages.image.forEachIndexed { index, image -> assertEquals(testImages[index], image) }
    }
}