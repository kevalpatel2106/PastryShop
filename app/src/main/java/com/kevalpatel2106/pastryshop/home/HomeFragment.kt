/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.home


import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevalpatel2106.pastryshop.R
import com.kevalpatel2106.pastryshop.bin.HomeCards
import com.kevalpatel2106.pastryshop.utils.AppLinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home_frament.*


/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    private val cards = ArrayList<HomeCards>()

    init {
        cards.add(HomeCards("title 1", "description 1", R.drawable.about_card_image))
        cards.add(HomeCards("title 2", "description 2", R.drawable.menu_card_image))
        cards.add(HomeCards("title 3", "description 3", R.drawable.credits_card_image))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_frament, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set the actionbar
        if (activity != null && activity is AppCompatActivity) {
            with(activity as AppCompatActivity) {
                this.setSupportActionBar(home_toolbar)
                this.supportActionBar?.setDisplayShowTitleEnabled(false)
                this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                this.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamburer_menu)
            }
        }

        //Set the recycler view
        home_cards_rv.layoutManager = AppLinearLayoutManager(
                context = context!!,
                orientation = LinearLayoutManager.HORIZONTAL,
                reverseLayout = false
        )
        home_cards_rv.adapter = HomeCardsAdapter(cards)


        // Manage the appbar scroll offsets.
        val cardsRvInitialMargin = resources.getDimension(R.dimen.home_cards_list_initial_marin_top)
        val cardsImageMaxHeight = resources.getDimension(R.dimen.home_cards_image_max_height)
        home_app_bar_layout.addOnOffsetChangedListener({ appBarLayout, verticalOffset ->

            // Change the margin of the recycler view.
            // It will o from -100dp to 0dp as the appbar shrinks
            val percentAppBarVisible: Double = ((appBarLayout.totalScrollRange - Math.abs(verticalOffset)) / appBarLayout.totalScrollRange.toDouble())
            val newTopMargin = percentAppBarVisible * cardsRvInitialMargin

            // Change the margin of the recycler view.
            val lp: CoordinatorLayout.LayoutParams = home_cards_rv.layoutParams as CoordinatorLayout.LayoutParams
            lp.topMargin = newTopMargin.toInt()
            home_cards_rv.layoutParams = lp

            // Change the height of the image in each card.
            // As the appbar shrinks, the image height should increase.
            val newHeight = (Math.abs(verticalOffset) / appBarLayout.totalScrollRange.toDouble() * cardsImageMaxHeight).toInt()
            (home_cards_rv.adapter as HomeCardsAdapter).imageHeight = newHeight

            // Control the scroll of the recycler view.
            // Recycler view should only scroll when the cards are fully visible (i.e. images in the card is fully visible).
            (home_cards_rv.layoutManager as AppLinearLayoutManager).isScrollEnabled = newHeight >= cardsImageMaxHeight
        })
    }

}
