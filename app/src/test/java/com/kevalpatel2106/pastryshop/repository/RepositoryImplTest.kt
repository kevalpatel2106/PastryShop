/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.repository

import com.kevalpatel2106.pastryshop.MockCacheManager
import com.kevalpatel2106.pastryshop.bin.Contact
import com.kevalpatel2106.pastryshop.bin.Pages
import com.kevalpatel2106.pastryshop.repository.db.MockPagesDao
import com.kevalpatel2106.pastryshop.repository.network.Network
import com.kevalpatel2106.pastryshop.utils.SharedPrefsProvider
import com.kevalpatel2106.testutils.MockServerManager
import com.kevalpatel2106.testutils.MockSharedPreference
import com.kevalpatel2106.testutils.RxSchedulersOverrideRule
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.File

/**
 * Created by Keval on 03/06/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(JUnit4::class)
class RepositoryImplTest {

    @Rule
    @JvmField
    val rxJavaRule = RxSchedulersOverrideRule()

    private lateinit var mockCacheManager: MockCacheManager
    private lateinit var mockServerManager: MockServerManager
    private lateinit var mockPagesDao: MockPagesDao
    private lateinit var mockSharedPrefsProvider: SharedPrefsProvider

    private lateinit var repository: Repository

    @Before
    fun setUp() {
        mockServerManager = MockServerManager()
        mockPagesDao = MockPagesDao(ArrayList())
        mockSharedPrefsProvider = SharedPrefsProvider(MockSharedPreference())
        mockCacheManager = MockCacheManager(mockPagesDao, mockSharedPrefsProvider)

        mockServerManager.startMockWebServer()

        repository = RepositoryImpl(
                network = Network(mockServerManager.getBaseUrl(), true),
                pagesDao = mockPagesDao,
                sharedPrefsProvider = mockSharedPrefsProvider
        )

    }

    @After
    fun tearUp() {
        mockServerManager.close()
        mockPagesDao.nukeTable()
    }

    @Test
    fun `get pages no items in cache`() {
        mockCacheManager.clearCache()

        mockServerManager.enqueueResponse(File(mockServerManager.getResponsesPath() + "/app_data.json"))

        // Make call
        val testSubscriber = TestObserver<ArrayList<Pages>>()
        repository.getPages().subscribe(testSubscriber)

        // Check the stream
        testSubscriber.assertValueCount(1)
                .assertNoErrors()
                .assertNotComplete()
                .assertValueAt(0, { it.size == 3 })
                .assertValueAt(0, { it[0].id == 1L })
                .assertValueAt(0, { it[0].title == "About" })
                .assertValueAt(0, { it[0].image.size == 3 })
                .assertValueAt(0, { it[1].id == 2L })
                .assertValueAt(0, { it[1].title == "Menu" })
                .assertValueAt(0, { it[1].image.size == 3 })
                .assertValueAt(0, { it[2].id == 3L })
                .assertValueAt(0, { it[2].title == "Credits" })
                .assertValueAt(0, { it[2].image.size == 3 })

        // Check the database
        assertEquals(3, mockPagesDao.tableItems.size)
        assertEquals("About", mockPagesDao.tableItems[0].title)
        assertEquals("Menu", mockPagesDao.tableItems[1].title)
        assertEquals("Credits", mockPagesDao.tableItems[2].title)

        // Check for the update times
        assertTrue(System.currentTimeMillis() - mockPagesDao.tableItems[0].updateMills < 10_000 /* 10 Seconds */)
        assertTrue(System.currentTimeMillis() - mockPagesDao.tableItems[1].updateMills < 10_000 /* 10 Seconds */)
        assertTrue(System.currentTimeMillis() - mockPagesDao.tableItems[2].updateMills < 10_000 /* 10 Seconds */)
    }

    @Test
    fun `get pages no items in cache network error`() {
        mockCacheManager.clearCache()

        mockServerManager.enqueueResponse("")

        // Make call
        val testSubscriber = TestObserver<ArrayList<Pages>>()
        repository.getPages().subscribe(testSubscriber)

        // Check the stream
        testSubscriber.assertValueCount(0)
                .assertErrorMessage("End of input at line 1 column 1 path \$" /* Invalid json */)
                .assertTerminated()

        // Check the database
        assertTrue(mockPagesDao.tableItems.isEmpty())
    }

    @Test
    fun `get pages items from filled cache`() {
        // Prepare database
        mockCacheManager.fillCache()

        // Set the empty response. (Network request failure)
        // There shouldn't be an API call as the cached is already filled.
        // So it shouldn't affect the response.
        mockServerManager.enqueueResponse("")

        // Make call
        val testSubscriber = TestObserver<ArrayList<Pages>>()
        repository.getPages().subscribe(testSubscriber)

        // Check the stream
        testSubscriber.assertValueCount(1)
                .assertNotComplete()
                .assertValueAt(0, { it.size == 3 })
                .assertValueAt(0, { it[0].id == 1L })
                .assertValueAt(0, { it[0].title == mockCacheManager.testTitle })
                .assertValueAt(0, { it[0].image.size == mockCacheManager.testImages.size })
                .assertValueAt(0, { it[1].id == 2L })
                .assertValueAt(0, { it[1].title == mockCacheManager.testTitle })
                .assertValueAt(0, { it[1].image.size == mockCacheManager.testImages.size })
                .assertValueAt(0, { it[2].id == 3L })
                .assertValueAt(0, { it[2].title == mockCacheManager.testTitle })
                .assertValueAt(0, { it[2].image.size == mockCacheManager.testImages.size })
    }


    @Test
    fun `get page by id when no items in cache`() {
        mockCacheManager.clearCache()

        mockServerManager.enqueueResponse(File(mockServerManager.getResponsesPath() + "/app_data.json"))

        // Make call
        val testSubscriber = TestObserver<Pages>()
        repository.getPage(1).subscribe(testSubscriber)

        // Check the stream
        testSubscriber.assertValueCount(1)
                .assertNoErrors()
                .assertNotComplete()
                .assertValueAt(0, { it.id == 1L })
                .assertValueAt(0, { it.title == "About" })
                .assertValueAt(0, { it.image.size == 3 })

        // Check the database
        assertEquals(3, mockPagesDao.tableItems.size)
        assertEquals("About", mockPagesDao.tableItems[0].title)
        assertEquals("Menu", mockPagesDao.tableItems[1].title)
        assertEquals("Credits", mockPagesDao.tableItems[2].title)

        // Check for the update times
        assertTrue(System.currentTimeMillis() - mockPagesDao.tableItems[0].updateMills < 10_000 /* 10 Seconds */)
        assertTrue(System.currentTimeMillis() - mockPagesDao.tableItems[1].updateMills < 10_000 /* 10 Seconds */)
        assertTrue(System.currentTimeMillis() - mockPagesDao.tableItems[2].updateMills < 10_000 /* 10 Seconds */)
    }

    @Test
    fun `get page by id when no items in cache network error`() {
        mockCacheManager.clearCache()

        mockServerManager.enqueueResponse("")

        // Make call
        val testSubscriber = TestObserver<Pages>()
        repository.getPage(1).subscribe(testSubscriber)

        // Check the stream
        testSubscriber.assertValueCount(0)
                .assertErrorMessage("End of input at line 1 column 1 path \$" /* Invalid json */)
                .assertTerminated()

        // Check the database
        assertTrue(mockPagesDao.tableItems.isEmpty())
    }

    @Test
    fun `get page by id from filled cache`() {
        // Prepare database
        mockCacheManager.fillCache()

        // Set the empty response. (Network request failure)
        // There shouldn't be an API call as the cached is already filled.
        // So it shouldn't affect the response.
        mockServerManager.enqueueResponse("")

        // Make call
        val testSubscriber = TestObserver<Pages>()
        repository.getPage(1).subscribe(testSubscriber)

        // Check the stream
        testSubscriber.assertValueCount(1)
                .assertNotComplete()
                .assertValueAt(0, { it.id == 1L })
                .assertValueAt(0, { it.title == mockCacheManager.testTitle })
                .assertValueAt(0, { it.description == mockCacheManager.testsDescription })
                .assertValueAt(0, { it.image.size == mockCacheManager.testImages.size })
    }

    @Test
    fun `get contact info when cache is filled`() {
        mockCacheManager.fillCache()

        // Make call
        val testSubscriber = TestObserver<Contact>()
        repository.getContactInfo().subscribe(testSubscriber)

        // Check the stream
        testSubscriber.assertValueCount(1)
                .assertComplete()
                .assertValueAt(0, { it.phone == mockCacheManager.testPhone })
                .assertValueAt(0, { it.email == mockCacheManager.testEmail })
                .assertValueAt(0, { it.twitter == mockCacheManager.testTwitter })
    }

    @Test
    fun `get contact info when cache is empty`() {
        mockCacheManager.clearCache()

        // Make call
        val testSubscriber = TestObserver<Contact>()
        repository.getContactInfo().subscribe(testSubscriber)

        // Check the stream
        testSubscriber.assertValueCount(0)
                .assertTerminated()
                .assertError { it.message?.isNotEmpty() ?: false }
    }

    @Test
    fun `refresh data with empty cache`() {
        mockCacheManager.clearCache()
        mockServerManager.enqueueResponse(File(mockServerManager.getResponsesPath() + "/app_data.json"))

        // Make call
        val testSubscriber = TestObserver<ArrayList<Pages>>()
        repository.refreshData().subscribe(testSubscriber)

        // Check the stream
        testSubscriber.assertValueCount(1)
                .assertNoErrors()
                .assertComplete()
                .assertValueAt(0, { it.size == 3 })
                .assertValueAt(0, { it[0].id == 1L })
                .assertValueAt(0, { it[0].title == "About" })
                .assertValueAt(0, { it[0].image.size == 3 })
                .assertValueAt(0, { it[1].id == 2L })
                .assertValueAt(0, { it[1].title == "Menu" })
                .assertValueAt(0, { it[1].image.size == 3 })
                .assertValueAt(0, { it[2].id == 3L })
                .assertValueAt(0, { it[2].title == "Credits" })
                .assertValueAt(0, { it[2].image.size == 3 })

        // Check the database
        assertEquals(3, mockPagesDao.tableItems.size)
        assertEquals("About", mockPagesDao.tableItems[0].title)
        assertEquals("Menu", mockPagesDao.tableItems[1].title)
        assertEquals("Credits", mockPagesDao.tableItems[2].title)

        // Check for the update times
        assertTrue(System.currentTimeMillis() - mockPagesDao.tableItems[0].updateMills < 10_000 /* 10 Seconds */)
        assertTrue(System.currentTimeMillis() - mockPagesDao.tableItems[1].updateMills < 10_000 /* 10 Seconds */)
        assertTrue(System.currentTimeMillis() - mockPagesDao.tableItems[2].updateMills < 10_000 /* 10 Seconds */)

        // Check the shared preferences.
        assertEquals("+91 8866 756632" /* See the test response */,
                mockSharedPrefsProvider.getStringFromPreferences(Contact.PREF_KEY_PHONE))
        assertEquals("kevalpatel2106@gmail.com" /* See the test response */,
                mockSharedPrefsProvider.getStringFromPreferences(Contact.PREF_KEY_EMAIL))
        assertEquals("https://twitter.com/kevalpatel2106" /* See the test response */,
                mockSharedPrefsProvider.getStringFromPreferences(Contact.PREF_KEY_TWITTER))
    }

    @Test
    fun `refresh data with filled cache`() {
        mockCacheManager.fillCache()
        mockPagesDao.insert(Pages(  //An extra item that won't be in the API response.
                id = 4,
                title = mockCacheManager.testTitle,
                description = mockCacheManager.testsDescription,
                image = mockCacheManager.testImages
        ))

        mockServerManager.enqueueResponse(File(mockServerManager.getResponsesPath() + "/app_data.json"))

        // Make call
        val testSubscriber = TestObserver<ArrayList<Pages>>()
        repository.refreshData().subscribe(testSubscriber)

        // Check the stream
        testSubscriber.assertValueCount(1)
                .assertNoErrors()
                .assertComplete()
                .assertValueAt(0, { it.size == 3 })
                .assertValueAt(0, { it[0].id == 1L })
                .assertValueAt(0, { it[0].title == "About" })
                .assertValueAt(0, { it[0].image.size == 3 })
                .assertValueAt(0, { it[1].id == 2L })
                .assertValueAt(0, { it[1].title == "Menu" })
                .assertValueAt(0, { it[1].image.size == 3 })
                .assertValueAt(0, { it[2].id == 3L })
                .assertValueAt(0, { it[2].title == "Credits" })
                .assertValueAt(0, { it[2].image.size == 3 })

        // Check the database
        // Check the entries are updated.
        assertEquals(3, mockPagesDao.tableItems.size)   // Check if the extra item gets deleted.
        assertEquals("About", mockPagesDao.tableItems[0].title)
        assertEquals("Menu", mockPagesDao.tableItems[1].title)
        assertEquals("Credits", mockPagesDao.tableItems[2].title)

        // Check for the update times
        assertTrue(System.currentTimeMillis() - mockPagesDao.tableItems[0].updateMills < 10_000 /* 10 Seconds */)
        assertTrue(System.currentTimeMillis() - mockPagesDao.tableItems[1].updateMills < 10_000 /* 10 Seconds */)
        assertTrue(System.currentTimeMillis() - mockPagesDao.tableItems[2].updateMills < 10_000 /* 10 Seconds */)

        // Check the shared preferences.
        assertEquals("+91 8866 756632" /* See the test response */,
                mockSharedPrefsProvider.getStringFromPreferences(Contact.PREF_KEY_PHONE))
        assertEquals("kevalpatel2106@gmail.com" /* See the test response */,
                mockSharedPrefsProvider.getStringFromPreferences(Contact.PREF_KEY_EMAIL))
        assertEquals("https://twitter.com/kevalpatel2106" /* See the test response */,
                mockSharedPrefsProvider.getStringFromPreferences(Contact.PREF_KEY_TWITTER))
    }

    @Test
    fun `refresh data with network error`() {
        mockCacheManager.clearCache()

        mockServerManager.enqueueResponse("")

        // Make call
        val testSubscriber = TestObserver<ArrayList<Pages>>()
        repository.refreshData().subscribe(testSubscriber)

        // Check the stream
        testSubscriber.assertValueCount(0)
                .assertErrorMessage("End of input at line 1 column 1 path \$" /* Invalid json */)
                .assertTerminated()

        // Check the database
        assertTrue(mockPagesDao.tableItems.isEmpty())
    }
}