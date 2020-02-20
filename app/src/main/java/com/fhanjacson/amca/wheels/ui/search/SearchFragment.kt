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
import coil.api.load
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.Vehicle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.storage.FirebaseStorage

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var vehicleList: List<Vehicle>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(Constant.LOG_TAG, "SearchFragment onCreateView")
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        val testView: ImageView = root.findViewById(R.id.test_imageview)
        searchViewModel.getVehicleList().observe(viewLifecycleOwner, Observer {
            vehicleList = it

            val storage = FirebaseStorage.getInstance()
            val ref = storage.getReferenceFromUrl(vehicleList[0].images[0])

            ref.downloadUrl.addOnSuccessListener {url ->
                Log.d(Constant.LOG_TAG, " Image URL: $url.toString()")
                testView.load(url)
            }.addOnFailureListener { e ->
                Log.d(Constant.LOG_TAG, e.toString())
            }

        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = view.findViewById<FloatingActionButton>(R.id.filterFAB)
        fab.setOnClickListener {
            Toast.makeText(context, "filter", Toast.LENGTH_SHORT).show()
        }
    }
}