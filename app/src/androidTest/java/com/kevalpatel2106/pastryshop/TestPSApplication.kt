/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop

import android.arch.persistence.room.Room
import com.kevalpatel2106.pastryshop.di.MockBaseDiModule
import com.kevalpatel2106.pastryshop.di.MockRootComponent
import com.kevalpatel2106.pastryshop.di.RootComponent
import com.kevalpatel2106.pastryshop.repository.db.PSDatabase
import com.kevalpatel2106.pastryshop.utils.BaseApplication
import com.kevalpatel2106.testutils.MockServerManager

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class TestPSApplication : BaseApplication() {

    var mockServerManager = MockServerManager()

    /**
     * Prepare the base url for the network server. This method will be called only once in application
     * lifetime. We are going to use [MockServerManager] to mock the network source and use the mock
     * server base url here.
     */
    override fun prepareBaseUrl(): String {
        mockServerManager.startMockWebServer()

        // Pass the mock web server url
        return mockServerManager.getBaseUrl()
    }

    /**
     * Prepare the [PSDatabase] for the application. This method will be called only once in application
     * lifetime. For the testing purpose we are going to create in memory database.
     */
    override fun prepareDatabase(): PSDatabase {
        // Create in memory database.
        // No disk writes

        return Room.inMemoryDatabaseBuilder(
                this@TestPSApplication,
                PSDatabase::class.java
        ).build()
    }

    /**
     * Prepare the [RootComponent] that will contain the root of the dependency graph. You can easily
     * mock these objects for tests by providing mock version of [RootComponent]. See android test
     * source for detail.
     */
    override fun prepareRootComponent(): RootComponent {
        return DaggerMockRootComponent.builder()
                .mockBaseDiModule(MockBaseDiModule(this@TestPSApplication))
                .build()
    }

    override fun injectRootComponent() {
        (rootComponent as MockRootComponent).inject(this@TestPSApplication)
    }
}