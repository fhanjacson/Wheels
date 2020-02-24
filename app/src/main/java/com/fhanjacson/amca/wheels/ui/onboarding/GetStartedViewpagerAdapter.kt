package com.fhanjacson.amca.wheels.ui.onboarding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fhanjacson.amca.wheels.R

class GetStartedViewpagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        lateinit var fragment : Fragment

        when (position) {
            0 -> {
                val firstFragment = ImageViewFragment(R.drawable.getstart1)
                fragment = firstFragment
            }
            1 -> {
                val secondFragment = ImageViewFragment(R.drawable.getstart2)
                fragment = secondFragment
            }
            2 -> {
                val thirdFragment = ImageViewFragment(R.drawable.getstart3)
                fragment = thirdFragment
            }
        }

        return fragment
    }

}