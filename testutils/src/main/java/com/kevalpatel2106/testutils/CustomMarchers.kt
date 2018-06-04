package com.kevalpatel2106.testutils

import android.view.View
import android.widget.ImageView
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher


/**
 * Created by Kevalpatel2106 on 04-Jun-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
object CustomMarchers{

    fun hasImage(): TypeSafeMatcher<View> {
        return object : TypeSafeMatcher<View>() {

            override fun describeTo(description: Description) {
                description.appendText("No image found.")
            }

            protected override fun matchesSafely(actualImageView: View): Boolean {
                return (actualImageView as ImageView).drawable != null
            }
        }
    }
}
