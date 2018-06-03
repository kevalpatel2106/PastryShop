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
import com.kevalpatel2106.pastryshop.utils.BaseApplication
import com.kevalpatel2106.pastryshop.utils.SharedPrefsProvider
import com.kevalpatel2106.testutils.MockSharedPreference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Module
internal class MockBaseDiModule(private val application: Application) {

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
    fun provideBaseApplication(): BaseApplication {
        return application as BaseApplication
    }

    @Singleton
    @Provides
    fun provideSharedPreferenceProvider(context: Context): SharedPrefsProvider {

        // Create in memory shared preference
        return SharedPrefsProvider(MockSharedPreference())
    }
}
