package com.fhanjacson.amca.wheels

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var currentNavController: LiveData<NavController>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

//        TODO: vehicle list recyclerview should only get the list one time only, except user refresh (refresh layout click tab again) or filter
//        TODO: Pagination
//        TODO: Filter
//        TODO: Activities
//        TODO: Profile

    override fun onResume() {
        super.onResume()
        Log.d(Constant.LOG_TAG, "TODO: vehicle list recyclerview should only get the list one time only, except user refresh (refresh layout click tab again) or filter")
        Log.d(Constant.LOG_TAG, "TODO: Pagination")
        Log.d(Constant.LOG_TAG, "TODO: Filter")
        Log.d(Constant.LOG_TAG, "TODO: Activities")
        Log.d(Constant.LOG_TAG, "TODO: Profile")

    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)

        val navGraphIds = listOf(R.navigation.search_navigation, R.navigation.activity_navigation, R.navigation.account_navigation)

        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController.value?.navigateUp() ?: false
    }
}
