/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import com.kevalpatel2106.pastryshop.R
import com.kevalpatel2106.pastryshop.home.HomeFragment
import com.kevalpatel2106.pastryshop.utils.prepareLaunchIntent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_drawer.*

/**
 * Dashboard or the landing screen of the application.
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set the navigation drawer
        setNavigationDrawer()
    }

    /**
     * Set the navigation drawer and the header.
     */
    private fun setNavigationDrawer() {
        val drawerToggle = ActionBarDrawerToggle(this@MainActivity,
                main_drawer_layout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawerToggle.syncState()
        main_drawer_layout.addDrawerListener(drawerToggle)
        main_navigation_view.setNavigationItemSelectedListener(this@MainActivity)

        onNavigationItemSelected(main_navigation_view.menu.findItem(R.id.menu_drawer_home))
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_drawer_home -> {  //User clicked on HomeViewModel

                if (supportFragmentManager.findFragmentById(R.id.container) !is HomeFragment) {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, HomeFragment())
                            .commit()
                    supportFragmentManager.executePendingTransactions()
                }
            }
            R.id.nav_about -> { //User clicked on Info
                //TODO Implement about screen
            }
            else -> throw IllegalArgumentException("Invalid id for the navigation drawer.")
        }

        main_navigation_view.setCheckedItem(item.itemId)    // Mark as selected
        main_drawer_layout.closeDrawer(Gravity.START)       // Close the drawer
        return false
    }

    override fun onBackPressed() {
        if (main_drawer_layout.isDrawerOpen(Gravity.START)) {
            //If the drawer is open close it.
            main_drawer_layout.closeDrawer(Gravity.START)
        } else {
            //Exit the application
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (!main_drawer_layout.isDrawerOpen(Gravity.START)) {
                    //If the drawer is closed, open it.
                    main_drawer_layout.openDrawer(Gravity.START)
                }
                false
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    companion object {

        /**
         * Static factory method to launch [MainActivity]. Set [isNewTask] to true if you want to
         * start [MainActivity] in a new task.
         */
        fun launch(context: Context, isNewTask: Boolean = false) {
            context.startActivity(context.prepareLaunchIntent(MainActivity::class.java, isNewTask))
        }
    }
}
