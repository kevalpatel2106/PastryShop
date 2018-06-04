/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.di

import com.kevalpatel2106.pastryshop.TestPSApplication
import com.kevalpatel2106.pastryshop.repository.RepoDiModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Singleton
@Component(modules = [MockRootDiModule::class])
internal interface MockRootComponent : RootComponent {

    fun inject(testPSApplication: TestPSApplication)

    fun getTestPSApplication(): TestPSApplication
}
