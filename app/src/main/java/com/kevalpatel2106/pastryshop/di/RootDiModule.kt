/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.preference.PreferenceManager
import com.kevalpatel2106.pastryshop.BuildConfig
import com.kevalpatel2106.pastryshop.repository.db.PSDatabase
import com.kevalpatel2106.pastryshop.utils.BaseApplication
import com.kevalpatel2106.pastryshop.utils.SharedPrefsProvider
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Keval on 01/06/18.
 * Dagger [Module] to provide application, database and network base url dependencies. In the test
 * environment you can mock this dependencies and inject mocks into the application.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Module
internal class RootDiModule(private val application: Application) {

    companion object {
        const val BASE_URL = "base_url"
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
    fun provideBaseApplication(): BaseApplication {
        return application as BaseApplication
    }

    @Singleton
    @Provides
    fun provideSharedPreferenceProvider(context: Context): SharedPrefsProvider {
        return SharedPrefsProvider(PreferenceManager.getDefaultSharedPreferences(context))
    }

    /**
     * Room database .
     *
     * @see PSDatabase
     */
    @Singleton
    @Provides
    fun provideDatabase(application: Application): PSDatabase {
        return Room.databaseBuilder(
                application,
                PSDatabase::class.java,
                PSDatabase.DB_NAME
        ).build()
    }

    /**
     * Base url of the API endpoints.
     */
    @Singleton
    @Provides
    @Named(BASE_URL)
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }
}
