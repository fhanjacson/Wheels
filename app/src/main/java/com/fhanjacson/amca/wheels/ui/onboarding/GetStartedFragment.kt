package com.fhanjacson.amca.wheels.ui.onboarding


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2

import com.fhanjacson.amca.wheels.R
import kotlinx.android.synthetic.main.fragment_get_started.*
import kotlinx.android.synthetic.main.fragment_get_started.view.*

/**
 * A simple [Fragment] subclass.
 */
class GetStartedFragment : Fragment() {

    private lateinit var getStartedViewPagerAdapter: GetStartedViewpagerAdapter
    private lateinit var getStartedViewpager: ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        getStartedViewPagerAdapter = GetStartedViewpagerAdapter(this)
        return inflater.inflate(R.layout.fragment_get_started, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getStartedViewpager = view.getStartedViewpager
        getStartedViewpager.adapter = getStartedViewPagerAdapter


        getStarted_button.setOnClickListener{
            findNavController().navigate(R.id.action_getStartedFragment_to_onboardingFragment3)
        }

        cancel_button.setOnClickListener{
            findNavController().popBackStack()
        }
    }
}
