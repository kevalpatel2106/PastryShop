/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.di

import com.kevalpatel2106.pastryshop.home.HomeFragment
import com.kevalpatel2106.pastryshop.pageDetail.DetailFragment
import com.kevalpatel2106.pastryshop.repository.RepoDiModule
import com.kevalpatel2106.pastryshop.utils.ApplicationScope
import dagger.Component

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@ApplicationScope
@Component(dependencies = [BaseComponent::class], modules = [ViewModelFactoryModule::class, RepoDiModule::class])
interface AppDiComponent {

    fun inject(homeFragment: HomeFragment)

    fun inject(detailFragment: DetailFragment)
}