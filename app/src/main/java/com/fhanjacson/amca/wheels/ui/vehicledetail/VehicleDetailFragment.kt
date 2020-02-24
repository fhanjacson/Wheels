package com.fhanjacson.amca.wheels.ui.vehicledetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

import com.fhanjacson.amca.wheels.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.vehicle_detail_fragment.view.*

class VehicleDetailFragment : Fragment() {

    private lateinit var checkoutButton: Button

    companion object {
        fun newInstance() = VehicleDetailFragment()
    }

    private lateinit var viewModel: VehicleDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var root = inflater.inflate(R.layout.vehicle_detail_fragment, container, false)

        checkoutButton = root.checkoutButton


        return root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VehicleDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkoutButton.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                findNavController().navigate(R.id.action_vehicleDetailFragment_to_checkoutFragment)
            } else {
                findNavController().navigate(R.id.action_vehicleDetailFragment_to_getStartedFragment)
            }
        }
    }
}
