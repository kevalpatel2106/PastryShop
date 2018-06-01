/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.home


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.kevalpatel2106.pastryshop.PSApplication
import com.kevalpatel2106.pastryshop.R
import com.kevalpatel2106.pastryshop.di.DaggerAppDiComponent
import com.kevalpatel2106.pastryshop.utils.AppLinearLayoutManager
import com.kevalpatel2106.pastryshop.utils.showSnack
import kotlinx.android.synthetic.main.fragment_home_frament.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    private lateinit var model: HomeViewModel

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        DaggerAppDiComponent.builder()
                .baseComponent(PSApplication.getBaseComponent(context!!))
                .build()
                .inject(this@HomeFragment)
        model = ViewModelProviders
                .of(this@HomeFragment, viewModelProvider)
                .get(HomeViewModel::class.java)

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

        // Handle errors
        model.errorLoadingCards.observe(this@HomeFragment, Observer {
            it?.let { showSnack(it) }
        })
        model.isLoadingCards.observe(this@HomeFragment, Observer {
            it?.let {
                // Disable collapsing toolbar behaviour if cards are still loading...
                home_coordinate_layout.isAllowForScroll = !it
            }
        })

        setCardsList()

        manageScrollAnimations()
    }

    private fun setCardsList() {
        home_cards_rv.layoutManager = AppLinearLayoutManager(
                context = context!!,
                orientation = LinearLayoutManager.HORIZONTAL,
                reverseLayout = false
        )
        home_cards_rv.addItemDecoration(CardListItemDecorator(resources.getDimensionPixelSize(R.dimen.home_card_margin)))
        LinearSnapHelper().attachToRecyclerView(home_cards_rv)
        home_cards_rv.adapter = HomeCardsAdapter(model.cards.value!!)
        model.cards.observe(this@HomeFragment, Observer {
            it?.let { home_cards_rv.adapter.notifyDataSetChanged() }
        })
    }

    private fun manageScrollAnimations() {
        val cardsRvInitialMargin = resources.getDimension(R.dimen.home_cards_list_initial_marin_top)
        val cardsImageMaxHeight = resources.getDimension(R.dimen.home_cards_image_max_height)

        var subTitleXCoordinate = 0F
        home_coordinate_layout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                //Remove the listener before proceeding
                home_coordinate_layout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                subTitleXCoordinate = shop_subtitle_tv.x
            }
        })

        home_app_bar_layout.addOnOffsetChangedListener({ appBarLayout, verticalOffset ->

            val percentAppBarVisible: Double = ((appBarLayout.totalScrollRange - Math.abs(verticalOffset))
                    / appBarLayout.totalScrollRange.toDouble())

            // Change the margin of the recycler view.
            // It will o from -100dp to 0dp as the appbar shrinks
            val newTopMargin = percentAppBarVisible * cardsRvInitialMargin
            val lp: CoordinatorLayout.LayoutParams = home_cards_rv.layoutParams as CoordinatorLayout.LayoutParams
            lp.topMargin = newTopMargin.toInt()
            home_cards_rv.layoutParams = lp

            // Change the height of the image in each card.
            // As the appbar shrinks, the image height should increase.
            val newHeight = (Math.abs(verticalOffset) / appBarLayout.totalScrollRange.toDouble() * cardsImageMaxHeight).toInt()
            (home_cards_rv.adapter as HomeCardsAdapter).imageHeight = newHeight

            // Control the scroll of the recycler view.
            // Recycler view should only scroll when the cards are fully visible (i.e. images in the card is fully visible).
            (home_cards_rv.layoutManager as AppLinearLayoutManager).isScrollEnabled = percentAppBarVisible < 0.20

            //Animate the subtitle text view
            if (subTitleXCoordinate > 0) {  // Make sure that OnGlobalLayoutListener is called once and we have the real x coordinate
                shop_subtitle_tv.x = shop_name_tv.x +
                        resources.getDimensionPixelSize(R.dimen.spacing_nano) +
                        ((subTitleXCoordinate - shop_name_tv.x) * percentAppBarVisible.toFloat())
            }
        })
    }

}
