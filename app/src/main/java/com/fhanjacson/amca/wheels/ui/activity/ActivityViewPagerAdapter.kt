package com.fhanjacson.amca.wheels.ui.activity

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ActivityViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        lateinit var fragment : Fragment
        when (position) {
            0 -> {
                val tripHistoryFragment = TripHistoryFragment()
                fragment = tripHistoryFragment
            }
            1 -> {
                val accountHistoryFragment = AccountHistoryFragment()
                fragment = accountHistoryFragment
            }
        }

        return fragment
    }


}