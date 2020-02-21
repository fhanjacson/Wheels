package com.fhanjacson.amca.wheels

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.fhanjacson.amca.wheels.ui.main.MainViewPagerAdapter
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewPagerAdapter: MainViewPagerAdapter
    private lateinit var mainViewPager: ViewPager2
    private lateinit var mainBottomNavigationView: BottomNavigationView
    private var mainNavigationTitles = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewPager = mainViewpager
        mainBottomNavigationView = nav_view

        mainNavigationTitles.add(getString(R.string.title_search))
        mainNavigationTitles.add(getString(R.string.title_activity))
        mainNavigationTitles.add(getString(R.string.title_account))

        mainViewPagerAdapter = MainViewPagerAdapter(this)
        mainViewPager.adapter = mainViewPagerAdapter





        mainViewPager.registerOnPageChangeCallback( object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mainBottomNavigationView.menu.getItem(position).isChecked = true
            }
        })

        mainBottomNavigationView.setOnNavigationItemSelectedListener(object: BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                Log.d(Constant.LOG_TAG, "bnv.menu.itemid: ${item.itemId}")

                when (item.itemId) {
                    R.id.searchFragmentMenu -> {
                        mainViewPager.currentItem = 0
                    }
                    R.id.activityFragmentMenu -> {
                        mainViewPager.currentItem = 1
                    }
                    R.id.accountFragmentMenu -> {
                        mainViewPager.currentItem = 2
                    }
                }
                return false
            }
        })

}



}

