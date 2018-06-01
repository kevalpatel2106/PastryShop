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
import com.kevalpatel2106.pastryshop.bin.HomeCards
import io.reactivex.Flowable

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Dao
interface CardsDao {

    @Insert
    fun insert(cards: HomeCards): Long

    @Update
    fun update(cards: HomeCards): Int

    @Query("SELECT COUNT(*) FROM ${HomeCards.CARDS_TABLE}")
    fun getCount(): Flowable<Int>

    @Query("SELECT * FROM ${HomeCards.CARDS_TABLE}")
    fun getAllCards(): Flowable<List<HomeCards>>

    @Query("SELECT * FROM ${HomeCards.CARDS_TABLE} WHERE ${HomeCards.CARDS_ID}=:id")
    fun getCardFromId(id: Long): HomeCards?

    @Query("DELETE FROM ${HomeCards.CARDS_TABLE} WHERE ${HomeCards.CARDS_UPDATE_TIME}<:updateTime")
    fun deleteNotUpdatedCards(updateTime: Long)

    @Query("DELETE FROM ${HomeCards.CARDS_TABLE}")
    fun nukeTable()
}