/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.repository.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.kevalpatel2106.pastryshop.bin.Pages
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Dao
interface PagesDao {

    @Insert
    fun insert(cards: Pages): Long

    @Update
    fun update(cards: Pages): Int

    @Query("SELECT COUNT(*) FROM ${Pages.PAGES_TABLE}")
    fun getCount(): Single<Int>

    @Query("SELECT * FROM ${Pages.PAGES_TABLE}")
    fun getAllCards(): Flowable<List<Pages>>

    @Query("SELECT * FROM ${Pages.PAGES_TABLE} WHERE ${Pages.PAGE_ID}=:id")
    fun getCardFromId(id: Long): Pages?

    @Query("SELECT * FROM ${Pages.PAGES_TABLE} WHERE ${Pages.PAGE_ID}=:id")
    fun observePage(id: Long): Flowable<Pages>

    @Query("SELECT COUNT(*) FROM ${Pages.PAGES_TABLE} WHERE ${Pages.PAGE_ID}=:id")
    fun hasPage(id: Long): Single<Int>

    @Query("DELETE FROM ${Pages.PAGES_TABLE} WHERE ${Pages.PAGE_UPDATE_TIME}<:updateTime")
    fun deleteNotUpdatedCards(updateTime: Long)

    @Query("DELETE FROM ${Pages.PAGES_TABLE}")
    fun nukeTable()
}