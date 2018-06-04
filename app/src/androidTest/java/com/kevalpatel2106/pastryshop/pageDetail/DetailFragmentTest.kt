package com.kevalpatel2106.pastryshop.pageDetail

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.runner.AndroidJUnit4
import com.kevalpatel2106.pastryshop.CacheDataManager
import com.kevalpatel2106.pastryshop.R
import com.kevalpatel2106.pastryshop.TestPSApplication
import com.kevalpatel2106.pastryshop.di.DaggerTestAppComponent
import com.kevalpatel2106.pastryshop.di.MockRootComponent
import com.kevalpatel2106.pastryshop.utils.BaseApplication
import com.kevalpatel2106.testutils.CustomMarchers
import com.kevalpatel2106.testutils.FragmentTestRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Created by Kevalpatel2106 on 04-Jun-18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(AndroidJUnit4::class)
class DetailFragmentTest {

    @Rule
    @JvmField
    val rule = FragmentTestRule(
            fragmentClass = DetailFragment::class.java,
            fragmentInstance = DetailFragment.newInstance(
                    context = InstrumentationRegistry.getTargetContext(),
                    id = 1,
                    name = CacheDataManager.testTitle,
                    description = CacheDataManager.testsDescription,
                    images = CacheDataManager.testImages,
                    uesTransition = false /* Turn off animations while testing */
            )
    )

    @Inject
    internal lateinit var cacheDataManager: CacheDataManager

    @Before
    fun setUp() {
        DaggerTestAppComponent.builder()
                .mockRootComponent((BaseApplication.getRootComponent(rule.activity)) as MockRootComponent)
                .build()
                .inject(this@DetailFragmentTest)

        cacheDataManager.fillCache()

        (rule.activity.application as TestPSApplication)
                .mockServerManager
                .enqueueResponse(
                        context = InstrumentationRegistry.getContext(),
                        rawFile = com.kevalpatel2106.testutils.R.raw.app_data
                )
    }

    @After
    fun tearUp() {
        cacheDataManager.clearCache()
        (rule.activity.application as TestPSApplication)
                .mockServerManager
                .close()
    }

    @Test
    @Throws(Exception::class)
    fun checkData_WhenCacheFilled() {
        // Application is reading data from cache, not from network
        onView(ViewMatchers.withId(R.id.detail_title_tv))
                .check(ViewAssertions.matches(ViewMatchers.withText(CacheDataManager.testTitle)))
        onView(ViewMatchers.withId(R.id.detail_description_tv))
                .check(ViewAssertions.matches(ViewMatchers.withText(CacheDataManager.testsDescription)))

        onView(ViewMatchers.withId(R.id.details_card)).perform(ViewActions.swipeUp())
        onView(ViewMatchers.withId(R.id.detail_second_image_iv))
                .check(ViewAssertions.matches(CustomMarchers.hasImage()))
    }

//    @Test
//    @Throws(Exception::class)
//    fun checkData_WhenCacheEmpty() {
//        cacheDataManager.clearCache()
//
//        // Wait for the network to get updated response
//        val idlingResource = ElapsedTimeIdlingResource(2000)
//        IdlingRegistry.getInstance().register(idlingResource)
//
//        // Application is reading data from network as there is nothing in cache
//        onView(ViewMatchers.withId(R.id.detail_title_tv))
//                .check(ViewAssertions.matches(ViewMatchers.withText("About" /* See mock network response */)))
//        onView(ViewMatchers.withId(R.id.detail_description_tv))
//                .check(ViewAssertions.matches(ViewMatchers.withText(
//                        InstrumentationRegistry.getContext().getString(com.kevalpatel2106.pastryshop.test.R.string.detail_test_response_description)  /* See mock network response */
//                )))
//
//        onView(ViewMatchers.withId(R.id.details_card)).perform(ViewActions.swipeUp())
//        onView(ViewMatchers.withId(R.id.detail_second_image_iv))
//                .check(ViewAssertions.matches(CustomMarchers.hasImage()))
//        IdlingRegistry.getInstance().unregister(idlingResource)
//    }
}
