package com.kevalpatel2106.pastryshop.di

import com.kevalpatel2106.pastryshop.home.HomeFragmentTest
import com.kevalpatel2106.pastryshop.pageDetail.DetailFragmentTest
import com.kevalpatel2106.pastryshop.repository.RepoDiModule
import com.kevalpatel2106.pastryshop.utils.ApplicationScope
import dagger.Component

/**
 * Created by Kevalpatel2106 on 04-Jun-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@ApplicationScope
@Component(
        dependencies = [MockRootComponent::class],
        modules = [RepoDiModule::class]
)
interface TestAppComponent{

    fun inject(detailFragmentTest: DetailFragmentTest)
    fun inject(homeFragmentTest: HomeFragmentTest)
}
