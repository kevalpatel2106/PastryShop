/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.pageDetail

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.kevalpatel2106.pastryshop.MockCacheManager
import com.kevalpatel2106.pastryshop.repository.Repository
import com.kevalpatel2106.pastryshop.repository.RepositoryImpl
import com.kevalpatel2106.pastryshop.repository.db.MockPagesDao
import com.kevalpatel2106.pastryshop.repository.network.Network
import com.kevalpatel2106.pastryshop.utils.SharedPrefsProvider
import com.kevalpatel2106.testutils.MockServerManager
import com.kevalpatel2106.testutils.MockSharedPreference
import com.kevalpatel2106.testutils.RxSchedulersOverrideRule
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.File

/**
 * Created by Keval on 03/06/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(JUnit4::class)
class DetailViewModelTest {

    @Rule
    @JvmField
    val rxJavaRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var cacheManager: MockCacheManager
    private lateinit var mockServerManager: MockServerManager
    private lateinit var mockPagesDao: MockPagesDao
    private lateinit var mockSharedPrefsProvider: SharedPrefsProvider
    private lateinit var repository: Repository
    private lateinit var model: DetailViewModel

    @Mock
    private lateinit var nameObserver: Observer<String>

    @Mock
    private lateinit var descriptionObserver: Observer<String>

    @Mock
    private lateinit var imageObserver: Observer<ArrayList<String>>

    @Mock
    private lateinit var errorLoadingObserver: Observer<String>


    @Before
    fun setUp() {
        // Set repository
        mockServerManager = MockServerManager()
        mockPagesDao = MockPagesDao(ArrayList())
        mockSharedPrefsProvider = SharedPrefsProvider(MockSharedPreference())
        cacheManager = MockCacheManager(mockPagesDao, mockSharedPrefsProvider)

        mockServerManager.startMockWebServer()

        repository = RepositoryImpl(
                network = Network(mockServerManager.getBaseUrl(), true),
                pagesDao = mockPagesDao,
                sharedPrefsProvider = mockSharedPrefsProvider
        )

        MockitoAnnotations.initMocks(this@DetailViewModelTest)

        model = DetailViewModel(repository)

        model.name.observeForever(nameObserver)
        model.description.observeForever(descriptionObserver)
        model.image.observeForever(imageObserver)
        model.errorLoadingPage.observeForever(errorLoadingObserver)
    }

    @After
    fun tearUp() {
        mockServerManager.close()
        mockPagesDao.nukeTable()
    }

    @Test
    fun `check loading page with filled cache`() {
        cacheManager.fillCache()

        model.observePage(1)

        Mockito.verify(nameObserver, Mockito.times(1)).onChanged(cacheManager.testTitle)
        Mockito.verify(descriptionObserver, Mockito.times(1)).onChanged(cacheManager.testsDescription)
        Mockito.verify(imageObserver, Mockito.times(1)).onChanged(cacheManager.testImages)
        Mockito.verify(errorLoadingObserver, Mockito.never()).onChanged(anyString())

        // Validate items
        assertEquals(cacheManager.testTitle, model.name.value)
        assertEquals(cacheManager.testsDescription, model.description.value)
        assertNull(model.errorLoadingPage.value)
        assertEquals(cacheManager.testImages[0], model.image.value!![0])
        assertEquals(cacheManager.testImages[1], model.image.value!![1])

    }

    @Test
    fun `check loading page with empty cache`() {
        mockServerManager.enqueueResponse(File(mockServerManager.getResponsesPath() + "/app_data.json"))
        cacheManager.clearCache()

        model.observePage(1)

        Mockito.verify(nameObserver, Mockito.times(1)).onChanged("About")
        Mockito.verify(descriptionObserver, Mockito.times(1)).onChanged(anyString())
        Mockito.verify(imageObserver, Mockito.times(1)).onChanged(model.image.value)
        Mockito.verify(errorLoadingObserver, Mockito.never()).onChanged(anyString())

        // Validate items
        assertEquals("About", model.name.value)
        assertNull(model.errorLoadingPage.value)
        assertEquals(3, model.image.value!!.size)
    }

    @Test
    fun `check loading page with network error`() {
        mockServerManager.enqueueResponse("")
        cacheManager.clearCache()

        model.observePage(1)

        Mockito.verify(nameObserver, Mockito.never()).onChanged(anyString())
        Mockito.verify(descriptionObserver, Mockito.never()).onChanged(anyString())
        Mockito.verify(imageObserver, Mockito.times(1 /* While initializing setting empty list */))
                .onChanged(model.image.value)
        Mockito.verify(errorLoadingObserver, Mockito.times(1)).onChanged(anyString())

        // Validate items
        assertNotNull(model.errorLoadingPage.value)
        assertNull(model.name.value)
        assertNull(model.description.value)
        assertTrue(model.image.value!!.isEmpty())
    }
}