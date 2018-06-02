/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kevalpatel2106.pastryshop.di.DaggerAppDiComponent
import com.kevalpatel2106.pastryshop.main.MainActivity
import com.kevalpatel2106.pastryshop.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    private val tag = "SplashActivity"

    @Inject
    internal lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppDiComponent.builder()
                .baseComponent(PSApplication.getBaseComponent(this@SplashActivity))
                .build()
                .inject(this@SplashActivity)

        // Refresh the data
        // Fire the network call and forget.
        repository.refreshData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    // Do nothing
                }, {
                    // Do nothing
                    Log.e(tag, it.message)
                })

        // Navigate to the main screen
        MainActivity.launch(this@SplashActivity, false)
    }
}
