/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.bin

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Entity(tableName = HomeCards.CARDS_TABLE)
data class HomeCards(

        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = CARDS_ID)
        var id: Long,

        @ColumnInfo(name = CARDS_NAME)
        val title: String,

        @ColumnInfo(name = CARDS_DESCRIPTION)
        val description: String,

        val image: ArrayList<String>
) {

    @ColumnInfo(name = CARDS_UPDATE_TIME)
    var updateMills: Long = 0

    companion object {
        const val CARDS_TABLE = "card_table"
        const val CARDS_ID = "card_id"
        const val CARDS_NAME = "card_name"
        const val CARDS_DESCRIPTION = "card_description"
        const val CARDS_UPDATE_TIME = "update_time"
    }
}