package com.kevalpatel2106.pastryshop.repository.network

import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Keval on 05/06/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(JUnit4::class)
class GetDataResponseTest {

    companion object {

        internal val testTitle = "test_title"
        internal val testsDescription = "test_description"
        internal val testImages = ArrayList<String>()

        internal val testPhone = "3726583269"
        internal val testEmail = "test@example.com"
        internal val testTwitter = "@kevalpatel2106"

        private val testBackground = "http://example.com/background.png"

        private val pages = ArrayList<PagesItem>()
        private lateinit var contact: ContactItem

        init {
            testImages.add("http://eample.com/image1.jgp")
            testImages.add("http://eample.com/image2.jgp")
        }

        @BeforeClass
        @JvmStatic
        fun prepareData() {
            pages.add(PagesItem(
                    id = 1,
                    title = testTitle,
                    description = testsDescription,
                    image = testImages
            ))
            pages.add(PagesItem(
                    id = 2,
                    title = testTitle,
                    description = testsDescription,
                    image = testImages
            ))
            pages.add(PagesItem(
                    id = 3,
                    title = testTitle,
                    description = testsDescription,
                    image = testImages
            ))

            contact = ContactItem(
                    phone = testPhone,
                    twitter = testTwitter,
                    email = testEmail
            )
        }
    }

    @Test
    fun checkInit() {

        val getDataResponse = GetDataResponse(
                pagesItem = pages,
                contactInfo = contact,
                background = testBackground
        )

        getDataResponse.pagesItem.forEachIndexed { index, pagesItem ->
            assertEquals(pages[index].id, pagesItem.id)
            assertEquals(pages[index].title, pagesItem.title)
            assertEquals(pages[index].description, pagesItem.description)

            pagesItem.image.forEachIndexed { i, image -> assertEquals(pages[index].image[i], image) }
        }

        assertEquals(contact.phone, getDataResponse.contactInfo.phone)
        assertEquals(contact.email, getDataResponse.contactInfo.email)
        assertEquals(contact.twitter, getDataResponse.contactInfo.twitter)

        assertEquals(testBackground, getDataResponse.background)
    }
}