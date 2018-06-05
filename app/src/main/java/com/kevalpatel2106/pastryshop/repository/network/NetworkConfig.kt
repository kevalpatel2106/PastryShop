/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.repository.network

/**
 * Created by Keval on 01/06/18.
 * Network related configurations.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */

internal object NetworkConfig {

    /**
     * Request read timeout in minutes.
     */
    internal const val READ_TIMEOUT = 1L

    /**
     * Request write timeout in minutes.
     */
    internal const val WRITE_TIMEOUT = 1L

    /**
     * Network connection timeout in minutes.
     */
    internal const val CONNECTION_TIMEOUT = 1L
}
