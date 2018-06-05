/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.repository

import com.kevalpatel2106.pastryshop.bin.Contact
import com.kevalpatel2106.pastryshop.bin.Pages
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Keval on 01/06/18.
 * Protocol that defines
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
interface Repository {

    /**
     * Get the list of all [Pages] to display in the view.
     *
     * @return [Observable] of list of [Pages]. Once the subscriber subscribes to this [Observable],
     * it will keep emitting whenever there are any changes into the data until the stream gets dispose.
     */
    fun getPages(): Observable<ArrayList<Pages>>

    /**
     * Get the details of the [Pages] with [pageId].
     *
     * @return [Observable] of [Pages]. Once the subscriber subscribes to this [Observable],
     * it will keep emitting whenever there are any changes into the data until the stream gets dispose.
     */
    fun getPage(pageId: Long): Observable<Pages>

    /**
     * Get the [Contact] information.
     *
     * @return [Single] of [Contact].
     */
    fun getContactInfo(): Single<Contact>

    /**
     * Call the server and refresh all the data into the cache.
     *
     * @return [Single] of list of [Pages].
     */
    fun refreshData(): Single<ArrayList<Pages>>
}