/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.repository.db

import android.app.Application
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.kevalpatel2106.pastryshop.bin.Pages

/**
 * Created by Keval on 01/06/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Database(entities = [Pages::class], version = PSDatabase.DB_VERSION, exportSchema = true)
@TypeConverters(ImageListConverter::class)
abstract class PSDatabase : RoomDatabase() {

    companion object {
        //Database configs
        /**
         * Database name.
         */
        private const val DB_NAME = "ps_db"

        /**
         * Database version.
         */
        const val DB_VERSION = 1

        /**
         * Sole instance of the database
         */
        private var sINSTANCE: PSDatabase? = null

        /**
         * Get the singleton instance of the [PSDatabase].
         */
        fun getAppDatabase(application: Application): PSDatabase {
            if (sINSTANCE == null) {
                sINSTANCE = Room.databaseBuilder(application, PSDatabase::class.java, DB_NAME)
                        .build()
            }
            return sINSTANCE!!
        }
    }

    abstract fun cardsDao(): PagesDao
}