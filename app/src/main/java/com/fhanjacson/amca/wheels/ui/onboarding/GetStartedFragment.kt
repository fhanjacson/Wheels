package com.fhanjacson.amca.wheels.ui.onboarding


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.fhanjacson.amca.wheels.Constant

import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.GetStarted
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.fragment_get_started.*
import kotlinx.android.synthetic.main.fragment_get_started.view.*
import com.google.gson.annotations.JsonAdapter
import androidx.navigation.NavController
import androidx.navigation.get


/**
 * A simple [Fragment] subclass.
 */
class GetStartedFragment : Fragment() {

    private lateinit var getStartedViewPagerAdapter: GetStartedViewpagerAdapter
    private lateinit var getStartedViewpager: ViewPager2

    private lateinit var wormDotsIndicator: WormDotsIndicator
    private lateinit var getStartedItems: ArrayList<GetStarted>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        getStartedItems = arrayListOf()
        getStartedItems.add(object : GetStarted(R.drawable.getstart1, "Discover your dream car", "We have a huge collections of car"){})
        getStartedItems.add(object : GetStarted(R.drawable.getstart2, "Ride it anytime you want", "Just book and ride it"){})
        getStartedItems.add(object : GetStarted(R.drawable.getstart3, "Affordable prices", "Competitive pricing"){})


        getStartedViewPagerAdapter = GetStartedViewpagerAdapter(this, getStartedItems)
        return inflater.inflate(R.layout.fragment_get_started, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wormDotsIndicator = view.worm_dots_indicator

        getStartedViewpager = view.getStartedViewpager
        getStartedViewpager.adapter = getStartedViewPagerAdapter
        wormDotsIndicator.setViewPager2(getStartedViewpager)


//        getStarted_button.setOnClickListener{
//            Log.d(Constant.LOG_TAG, findNavController().graph.label.toString() + " == " + R.navigation.search_navigation.toString())
//            Log.d(Constant.LOG_TAG, findNavController().graph.navigatorName)
//            Log.d(Constant.LOG_TAG, findNavController().graph.id.toString())
//
////            if (findNavController().graph.label == R.navigation.search_navigation) {
////            findNavController().navigate(R.id.action_getStartedFragment_to_onboardingFragment3)
////            } else if (findNavController().graph.id == R.navigation.account_navigation) {
////                findNavController().navigate(R.id.action_getStartedFragment2_to_onboardingFragment2)
////            }
//        }
//
//        cancel_button.setOnClickListener{
//            findNavController().popBackStack()
//        }
    }



}
