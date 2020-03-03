package com.fhanjacson.amca.wheels.ui.activity


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fhanjacson.amca.wheels.Constant

import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.ProfileActivityResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_account_history.view.*

/**
 * A simple [Fragment] subclass.
 */
class AccountHistoryFragment : Fragment() {

    private lateinit var viewmodel: ActivityViewModel
    private lateinit var fview: View
    private val auth = FirebaseAuth.getInstance()
    private lateinit var accountHistoryAdapter: AccountHistoryAdapter
    private lateinit var activityRecyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewmodel = ViewModelProvider(requireActivity()).get(ActivityViewModel::class.java)
        return inflater.inflate(R.layout.fragment_account_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fview = view

        if (auth.currentUser != null) {
            viewmodel.activityList.observe(viewLifecycleOwner, Observer {
                if (it.isEmpty()){
                    view.noActivityLayout.visibility = View.VISIBLE
                } else {
                    view.noActivityLayout.visibility = View.INVISIBLE
                }
                setupRecyclerView(it)
            })
        } else {
            Toast.makeText(context, "You're not logged in", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerView(accountHistoryList: List<ProfileActivityResponse>) {
        val user = auth.currentUser
        if (user != null) {
            accountHistoryAdapter = AccountHistoryAdapter(accountHistoryList)
            activityRecyclerView = fview.accountHistoryRecyclerview.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = accountHistoryAdapter
            }
        }
    }
}
