package com.fhanjacson.amca.wheels.ui.onboarding

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fhanjacson.amca.wheels.ui.login.LoginFragment
import com.fhanjacson.amca.wheels.ui.signup.SignupFragment

class OnboardingCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("JACSON", "fragment pos: $position")
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