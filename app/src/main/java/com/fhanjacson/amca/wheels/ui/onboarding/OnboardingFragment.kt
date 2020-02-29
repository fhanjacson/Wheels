package com.fhanjacson.amca.wheels.ui.onboarding


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.R

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_onboarding.view.*


/**
 * A simple [Fragment] subclass.
 */
class OnboardingFragment : Fragment() {

    private lateinit var onboardingCollectionAdapter: OnboardingCollectionAdapter
    private lateinit var onboardingViewpager: ViewPager2
    private lateinit var onboardingTablayout: TabLayout
    private var onboardingViewPagerTitle = arrayListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.getStarted_button.setOnClickListener {
            view.getStartedFragmentContainer.visibility = View.GONE
            view.getstarted_bottomlayout.visibility = View.GONE
        }

        view.cancel_button.setOnClickListener {
            findNavController().popBackStack()
        }


        onboardingViewpager = view.onboardingViewpager
        onboardingTablayout = view.onboardingTablayout

        onboardingViewPagerTitle.add(getString(R.string.text_login_capital))
        onboardingViewPagerTitle.add(getString(R.string.text_signup_capital))


        onboardingCollectionAdapter = OnboardingCollectionAdapter(this)
        onboardingViewpager.adapter = onboardingCollectionAdapter
        TabLayoutMediator(onboardingTablayout, onboardingViewpager) { tab, position ->
            tab.text = onboardingViewPagerTitle[position]
        }.attach()


    }
}