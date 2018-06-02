/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.testutils

import android.support.test.rule.ActivityTestRule
import android.support.v4.app.Fragment
import org.junit.Assert.fail

/**
 * Created by Keval on 21-Jul-17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class FragmentTestRule<F : Fragment>(fragmentClass: Class<F>,
                                     fragmentInstance: F? = null)
    : ActivityTestRule<FragmentRuleActivity>(FragmentRuleActivity::class.java) {

    lateinit var fragment: F

    init {
        if (fragmentInstance == null) {
            try {
                fragment = fragmentClass.newInstance()
            } catch (e: InstantiationException) {
                fail(String.format("%s: Could not insert %s into FragmentRuleActivity: %s",
                        javaClass.simpleName,
                        fragmentClass.simpleName,
                        e.message))
            } catch (e: IllegalAccessException) {
                fail(String.format("%s: Could not insert %s into FragmentRuleActivity: %s", javaClass.simpleName, fragmentClass.simpleName, e.message))
            }
        } else {
            fragment = fragmentInstance
        }
    }

    override fun afterActivityLaunched() {
        super.afterActivityLaunched()

        //Instantiate and insert the fragment into the container layout
        val manager = activity.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()


    }
}
