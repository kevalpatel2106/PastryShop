/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.di

import android.app.Application
import android.content.Context
import com.kevalpatel2106.pastryshop.MyApplication
import com.kevalpatel2106.pastryshop.utils.RxSchedulers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Module
class BaseDiModule(private val application: Application) {

    companion object {
        const val BASE_URL = "base_url"
        const val ENABLE_LOG = "enable_log"

        fun get(application: Application) = BaseDiModule(application)
    }

    @Singleton
    @Provides
    fun provideContext(): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Singleton
    @Provides
    fun provideBaseApplication(): MyApplication {
        return application as MyApplication
    }

    @Singleton
    @Provides
    fun provideRxSchedulers(): RxSchedulers = RxSchedulers()
}
