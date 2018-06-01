/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.repository.di

import com.kevalpatel2106.pastryshop.BuildConfig
import com.kevalpatel2106.pastryshop.di.AppDiModule
import com.kevalpatel2106.pastryshop.repository.network.Network
import com.kevalpatel2106.pastryshop.utils.ApplicationScope
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Module
class RepoDiModule {

    companion object {
        const val BASE_URL = "base_url"
        const val ENABLE_LOG = "enable_log"
    }

    @Provides
    @ApplicationScope
    @Named(AppDiModule.BASE_URL)
    internal fun provideBaseUrl(): String {
        return "https://photoloc-qa.appspot.com" //TODO Switch for production
    }

    @Provides
    @ApplicationScope
    @Named(AppDiModule.ENABLE_LOG)
    internal fun provideIsEnableLogging(): Boolean {
        return BuildConfig.BUILD_TYPE.contains("debug", true)
    }

    @Provides
    @ApplicationScope
    internal fun provideNetwork(@Named(BASE_URL) baseUrl: String,
                                @Named(ENABLE_LOG) enableLogging: Boolean): Network {
        return Network(baseUrl, enableLogging)
    }


}