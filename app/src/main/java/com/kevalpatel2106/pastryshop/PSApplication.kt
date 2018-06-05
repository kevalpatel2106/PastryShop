/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop

import com.kevalpatel2106.pastryshop.di.DaggerRootComponent
import com.kevalpatel2106.pastryshop.di.RootComponent
import com.kevalpatel2106.pastryshop.di.RootDiModule
import com.kevalpatel2106.pastryshop.utils.BaseApplication

/**
 * Created by Keval on 01/06/18.
 * [BaseApplication] for the app.
 *
 * @see BaseApplication
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class PSApplication : BaseApplication() {

    override fun prepareRootComponent(): RootComponent {
        return DaggerRootComponent.builder()
                .rootDiModule(RootDiModule(this@PSApplication))
                .build()
    }

    override fun injectRootComponent() {
        rootComponent.inject(this@PSApplication)
    }
}
