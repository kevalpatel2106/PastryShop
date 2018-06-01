/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Kevalpatel2106 on 01-Jun-18.
 * Bin to handle the references for all the [Scheduler].
 *
 * @constructor Public constructor.
 * @param database [Scheduler] for the database queries. Make sure the database operations are single
 * threaded. Default value is [Schedulers.single].
 * @param disk [Scheduler] for the disk operations. Default value is [Schedulers.io].
 * @param main Android main thread [Scheduler]. Default value is [AndroidSchedulers.mainThread].
 * @param compute [Scheduler] to perform the heavy computation. If your work deal with any i/o consider
 * using [network],[database] or [disk] scheduler. Default value is [Schedulers.computation].
 * @param network [Scheduler] to perform network calls. Default value is [Schedulers.io].
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
data class RxSchedulers(
        val database: Scheduler = Schedulers.single(),
        val disk: Scheduler = Schedulers.io(),
        val network: Scheduler = Schedulers.io(),
        val compute: Scheduler = Schedulers.computation(),
        val main: Scheduler = AndroidSchedulers.mainThread()
)
