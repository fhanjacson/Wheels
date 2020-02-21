package com.fhanjacson.amca.wheels.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.Vehicle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var vehicleList: List<Vehicle>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(Constant.LOG_TAG, "SearchFragment onCreateView")
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val vehicleListUpdateObserver = Observer<List<Vehicle>> {
            vehicleList = it
            viewManager = LinearLayoutManager(context)
            viewAdapter = VehicleListAdapter(vehicleList)
            recyclerView = view.vehicleRecyclerview.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }

        searchViewModel.getVehicleList().observe(viewLifecycleOwner, vehicleListUpdateObserver)

        val fab = view.filterFAB
        fab.setOnClickListener {
            Toast.makeText(context, "filter", Toast.LENGTH_SHORT).show()
            view.findNavController().navigate(R.id.action_searchFragment_to_vehicleFilterFragment)
        }
    }


}