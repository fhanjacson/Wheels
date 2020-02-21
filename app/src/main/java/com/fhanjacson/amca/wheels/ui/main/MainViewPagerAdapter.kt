package com.fhanjacson.amca.wheels.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fhanjacson.amca.wheels.ui.account.AccountFragment
import com.fhanjacson.amca.wheels.ui.activity.ActivityFragment
import com.fhanjacson.amca.wheels.ui.search.SearchFragment

class MainViewPagerAdapter(fragmnet: FragmentActivity) : FragmentStateAdapter(fragmnet) {



    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        lateinit var fragment : Fragment

        when (position) {
            0 -> {
                val searchFragment = SearchFragment()
//                loginFragment.arguments = Bundle().apply {}
                fragment = searchFragment
            }
            1 -> {
                val activityFragment = ActivityFragment()
//                signupFragment.arguments = Bundle().apply {}
                fragment = activityFragment
            }
            2 -> {
                val accountFragment = AccountFragment()
                fragment = accountFragment
            }
        }

        return fragment
    }

}