/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop

import android.app.Application
import android.content.Context
import com.kevalpatel2106.pastryshop.di.AppComponent
import com.kevalpatel2106.pastryshop.di.AppDiModule
import com.kevalpatel2106.pastryshop.di.DaggerAppComponent

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class MyApplication: Application(){

    private lateinit var appComponent: AppComponent

    companion object {

        fun getSharedComponent(context: Context): AppComponent {
            return (context.applicationContext as MyApplication).appComponent
        }
    }

    override fun onCreate() {
        super.onCreate()

        //Create app component
        appComponent = DaggerAppComponent.builder()
                .appDiModule(AppDiModule(this@MyApplication))
                .build()

        //Inject dagger
        appComponent.inject(this@MyApplication)
    }

}