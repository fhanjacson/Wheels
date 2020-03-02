package com.fhanjacson.amca.wheels.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.fhanjacson.amca.wheels.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_account.view.*
import kotlinx.android.synthetic.main.fragment_activity.view.*

class ActivityFragment : Fragment() {

    private lateinit var activityViewModel: ActivityViewModel
    private lateinit var activityViewPagerAdapter: ActivityViewPagerAdapter
    private lateinit var activityTabLayout: TabLayout
    private lateinit var activityViewPager: ViewPager2
    private var activityViewPagerTitle = arrayListOf<String>()
    var auth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activityViewModel =
            ViewModelProvider(this).get(ActivityViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_activity, container, false)

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = auth.currentUser
        if (user != null) {
            view.noUserLayout_activity.visibility = View.GONE
            activityTabLayout = view.activityTablayout
            activityViewPager = view.activityViewpager

            activityViewPagerTitle.add(getString(R.string.title_trip_history))
            activityViewPagerTitle.add(getString(R.string.title_account_history))

            activityViewPagerAdapter = ActivityViewPagerAdapter(this)
            activityViewPager.adapter = activityViewPagerAdapter
            TabLayoutMediator(activityTabLayout, activityViewPager) {tab, position ->
                tab.text = activityViewPagerTitle[position]
            }.attach()
        } else {
            view.noUserLayout_activity.visibility = View.VISIBLE
            view.loginOrSignupButton_activity.setOnClickListener {
                findNavController().navigate(R.id.action_activityFragment2_to_onboardingFragment)
            }
        }



    }
}