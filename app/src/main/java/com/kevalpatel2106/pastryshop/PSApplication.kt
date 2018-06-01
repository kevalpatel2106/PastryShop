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
import com.kevalpatel2106.pastryshop.di.BaseComponent
import com.kevalpatel2106.pastryshop.di.BaseDiModule
import com.kevalpatel2106.pastryshop.di.DaggerBaseComponent

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class PSApplication : Application() {

    private lateinit var mBaseComponent: BaseComponent

    companion object {

        fun getBaseComponent(context: Context): BaseComponent {
            return (context.applicationContext as PSApplication).mBaseComponent
        }
    }

    override fun onCreate() {
        super.onCreate()

        //Create app component
        mBaseComponent = DaggerBaseComponent.builder()
                .baseDiModule(BaseDiModule(this@PSApplication))
                .build()

        //Inject dagger
        mBaseComponent.inject(this@PSApplication)
    }

}