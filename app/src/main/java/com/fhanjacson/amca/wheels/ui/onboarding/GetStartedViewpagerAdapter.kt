package com.fhanjacson.amca.wheels.ui.onboarding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.GetStarted
import kotlinx.android.synthetic.main.fragment_onboarding_viewpager.*

class GetStartedViewpagerAdapter(fragment: Fragment, getStartedItems: ArrayList<GetStarted>) :
    FragmentStateAdapter(fragment) {

    var getStarted = getStartedItems

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        lateinit var fragment: Fragment

        fragment = ImageViewFragment(getStarted[position])

        return fragment
    }

}