/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.pageDetail


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.transition.TransitionInflater
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevalpatel2106.pastryshop.PSApplication
import com.kevalpatel2106.pastryshop.R
import com.kevalpatel2106.pastryshop.di.DaggerAppDiComponent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailFragment : Fragment() {

    private lateinit var model: DetailViewModel

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    private var pageId: Long = 0

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        DaggerAppDiComponent.builder()
                .baseComponent(PSApplication.getBaseComponent(context!!))
                .build()
                .inject(this@DetailFragment)
        model = ViewModelProviders
                .of(this@DetailFragment, viewModelProvider)
                .get(DetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_detail, container, false)

        parseArguments()

        //Set the transition names.
        ViewCompat.setTransitionName(rootView.detail_title_tv,
                "${getString(R.string.transition_name_home_card_title)}_$pageId")
        ViewCompat.setTransitionName(rootView.detail_description_tv,
                "${getString(R.string.transition_name_home_card_description)}_$pageId")
        ViewCompat.setTransitionName(rootView.detail_images_flipper,
                "${getString(R.string.transition_name_home_card_image)}_$pageId")
        ViewCompat.setTransitionName(rootView.details_card,
                "${getString(R.string.transition_name_home_card_view)}_$pageId")

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.name.observe(this@DetailFragment, Observer {
            it?.let {
                detail_title_tv.text = it
            }
        })
        model.description.observe(this@DetailFragment, Observer {
            it?.let {
                detail_description_tv.text = it
            }
        })
        model.image.observe(this@DetailFragment, Observer {
            it?.let {
                if (it.isEmpty()) return@let

                Picasso.get()
                        .load(it[0])
                        .noFade()
                        .fit()
                        .centerCrop()
                        .into(detail_images_flipper)
                Picasso.get()
                        .load(it[0])
                        .noFade()
                        .into(detail_second_image_iv)
            }
        })

        // Close button
        detail_close_btn.setOnClickListener { activity?.supportFragmentManager?.popBackStack() }

        // Start observing for the changes,
        model.observePage(pageId)
    }

    private fun parseArguments() {
        when {
            arguments == null -> throw IllegalArgumentException("No argument passed.")
            !arguments!!.containsKey(ARG_ID) -> throw IllegalArgumentException("No images passed.")
            !arguments!!.containsKey(ARG_IMAGES) -> throw IllegalArgumentException("No images passed.")
            !arguments!!.containsKey(ARG_DESCRIPTION) -> throw IllegalArgumentException("No page title passed.")
            !arguments!!.containsKey(ARG_NAME) -> throw IllegalArgumentException("No page description passed.")
            else -> {
                pageId = arguments!!.getLong(ARG_ID)
                model.name.value = arguments!!.getString(ARG_NAME)
                model.description.value = arguments!!.getString(ARG_DESCRIPTION)
                model.image.value = arguments!!.getStringArrayList(ARG_IMAGES)
            }
        }
    }

    companion object {
        private const val ARG_ID = "id"
        private const val ARG_NAME = "name"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_IMAGES = "images"

        internal fun newInstance(
                context: Context,
                id: Long,
                name: String,
                description: String,
                images: ArrayList<String>
        ): DetailFragment {

            return DetailFragment().apply {
                retainInstance = true

                arguments = Bundle().apply {
                    putLong(ARG_ID, id)
                    putString(ARG_NAME, name)
                    putString(ARG_DESCRIPTION, description)
                    putStringArrayList(ARG_IMAGES, images)
                }

                //Enter animation
                sharedElementEnterTransition = TransitionInflater.from(context)
                        .inflateTransition(R.transition.detail_transition)
                enterTransition = TransitionInflater.from(context)
                        .inflateTransition(android.R.transition.fade)
            }
        }
    }
}
