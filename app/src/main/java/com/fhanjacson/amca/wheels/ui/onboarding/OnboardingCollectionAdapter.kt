package com.fhanjacson.amca.wheels.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        lateinit var fragment : Fragment

        when (position) {
            0 -> {
                val loginFragment = LoginFragment()
//                loginFragment.arguments = Bundle().apply {}
                fragment = loginFragment
            }
            1 -> {
                val signupFragment = SignupFragment()
//                signupFragment.arguments = Bundle().apply {}
                fragment = signupFragment
            }
        }

        return fragment
    }

}