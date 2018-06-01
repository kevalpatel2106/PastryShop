/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.base

import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import android.support.annotation.VisibleForTesting
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
abstract class BaseViewModel : ViewModel() {

    /**
     * [CompositeDisposable] to hold all the disposables from Rx and repository.
     */
    @VisibleForTesting
    internal val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) = mCompositeDisposable.add(disposable)

    @CallSuper
    override fun onCleared() {
        super.onCleared()

        //Delete all the API connections.
        mCompositeDisposable.dispose()
    }
}
