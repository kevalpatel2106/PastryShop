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
import android.support.transition.TransitionInflater
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.kevalpatel2106.pastryshop.PSApplication
import com.kevalpatel2106.pastryshop.R
import com.kevalpatel2106.pastryshop.bin.Pages
import com.kevalpatel2106.pastryshop.di.DaggerAppDiComponent
import com.kevalpatel2106.pastryshop.pageDetail.DetailFragment
import com.kevalpatel2106.pastryshop.utils.PSLinearLayoutManager
import com.kevalpatel2106.pastryshop.utils.showSnack
import kotlinx.android.synthetic.main.fragment_home_frament.*
import kotlinx.android.synthetic.main.item_dashboard_card.view.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass. This is the home screen of the application which contains the list
 * of cards for application pages and contact information.
 */
class HomeFragment : Fragment(), PageSelectionListener {

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
        model.errorLoadingPages.observe(this@HomeFragment, Observer {
            it?.let { showSnack(it) }
        })
        model.isBlockUi.observe(this@HomeFragment, Observer {
            it?.let {
                // Disable collapsing toolbar behaviour if pages are still loading...
                home_coordinate_layout.isAllowForScroll = !it

                // Hide call FAB
                if (it) phone_fab.hide() else phone_fab.show()
            }
        })

        setPagesList()

        manageScrollAnimations()

        // Set FAB
        phone_fab.setOnClickListener { context?.let { model.call(it) } }
    }

    private fun setPagesList() {
        //Prepare to horizontal linear layout manager.
        home_pages_rv.layoutManager = PSLinearLayoutManager(
                context = context!!,
                orientation = LinearLayoutManager.HORIZONTAL,
                reverseLayout = false
        )

        // Decorate each items!!!
        home_pages_rv.addItemDecoration(PagesListItemDecorator(resources.getDimensionPixelSize(R.dimen.home_card_margin)))

        // Snap the item to the center
        LinearSnapHelper().attachToRecyclerView(home_pages_rv)

        // Set the adapter and observe the pages list changes.
        home_pages_rv.adapter = PagesAdapter(
                cards = model.pages.value!!,
                pageSelectionListener = this@HomeFragment
        )
        model.pages.observe(this@HomeFragment, Observer {
            it?.let { home_pages_rv.adapter.notifyDataSetChanged() }
        })
    }

    private fun manageScrollAnimations() {
        val cardsRvInitialMargin = resources.getDimension(R.dimen.home_cards_list_initial_marin_top)
        val cardsImageMaxHeight = resources.getDimension(R.dimen.home_cards_image_max_height)

        var subTitleXCoordinate = 0F
        home_coordinate_layout.viewTreeObserver
                .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

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
            val lp: CoordinatorLayout.LayoutParams = home_pages_rv.layoutParams as CoordinatorLayout.LayoutParams
            lp.topMargin = newTopMargin.toInt()
            home_pages_rv.layoutParams = lp

            // Change the height of the image in each card.
            // As the appbar shrinks, the image height should increase.
            val newHeight = (Math.abs(verticalOffset) / appBarLayout.totalScrollRange.toDouble() * cardsImageMaxHeight).toInt()
            (home_pages_rv.adapter as PagesAdapter).imageHeight = newHeight

            // Control the scroll of the recycler view.
            // Recycler view should only scroll when the pages are fully visible (i.e. images in the card is fully visible).
            (home_pages_rv.layoutManager as PSLinearLayoutManager).isScrollEnabled = percentAppBarVisible < 0.20

            //Animate the subtitle text view
            if (subTitleXCoordinate > 0) {  // Make sure that OnGlobalLayoutListener is called once and we have the real x coordinate
                shop_subtitle_tv.x = shop_name_tv.x +
                        resources.getDimensionPixelSize(R.dimen.spacing_nano) +
                        ((subTitleXCoordinate - shop_name_tv.x) * percentAppBarVisible.toFloat())
            }
        })
    }

    override fun onPageSelected(page: Pages, itemView: View) {
        val detailFragment = DetailFragment.newInstance(
                context = context!!,
                id = page.id,
                name = page.title,
                description = page.description,
                images = page.image
        )

        //Exit transition
        sharedElementReturnTransition = TransitionInflater.from(context)
                .inflateTransition(R.transition.detail_transition)
        exitTransition = TransitionInflater.from(context)
                .inflateTransition(android.R.transition.fade)

        @Suppress("ReplaceSingleLineLet")
        activity?.let {
            it.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, detailFragment)
                    .addToBackStack(DETAIL_BACK_STACK_NAME)
                    .addSharedElement(itemView.card_title_tv, ViewCompat.getTransitionName(itemView.card_title_tv))
                    .addSharedElement(itemView.card_description_tv, ViewCompat.getTransitionName(itemView.card_description_tv))
                    .addSharedElement(itemView.card_iv, ViewCompat.getTransitionName(itemView.card_iv))
                    .addSharedElement(itemView.card, ViewCompat.getTransitionName(itemView.card))
                    .commit()
            it.supportFragmentManager.executePendingTransactions()

        }
    }

    companion object {
        private const val DETAIL_BACK_STACK_NAME = "detail"
    }
}
