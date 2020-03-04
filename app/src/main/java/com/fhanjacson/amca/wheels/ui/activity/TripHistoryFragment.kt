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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fhanjacson.amca.wheels.Constant

import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.BookingResponse
import com.fhanjacson.amca.wheels.repository.FirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_trip_history.view.*

/**
 * A simple [Fragment] subclass.
 */
class TripHistoryFragment : Fragment() {

    private lateinit var tripHistoryAdapter: TripHistoryAdapter
    private val repo = FirestoreRepository()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var tripRecyclerview: RecyclerView
    private lateinit var fview: View
    private lateinit var viewmodel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewmodel = ViewModelProvider(requireActivity()).get(ActivityViewModel::class.java)
        return inflater.inflate(R.layout.fragment_trip_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fview = view

        if (auth.currentUser != null) {
            viewmodel.bookingList.observe(viewLifecycleOwner, Observer {
                if (it.isEmpty()) {
                    view.noTripLayout.visibility = View.VISIBLE
                } else {
                    view.noTripLayout.visibility = View.INVISIBLE
                }
                setupRecyclerView(it)
            })
        } else {
            Toast.makeText(context, "You're not logged in", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerView(tripList: List<BookingResponse>) {
        Log.d(Constant.LOG_TAG, "TripHistoryFragment:setupRecyclerView")
        val user = auth.currentUser
        if (user != null) {
            tripHistoryAdapter = TripHistoryAdapter(tripList)
            val gridColumnCount = resources.getInteger(R.integer.grid_column_count)
            tripRecyclerview = fview.triphistoryRecyclerview.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(context, gridColumnCount)
                adapter = tripHistoryAdapter
            }
        }

    }

}
