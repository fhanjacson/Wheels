package com.fhanjacson.amca.wheels.ui.vehicledetail

import android.app.ActionBar
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.MainActivity

import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.base.GlideApp
import com.fhanjacson.amca.wheels.model.Vehicle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.vehicle_detail_fragment.view.*
import java.util.*

class VehicleDetailFragment : Fragment(), OnMapReadyCallback {


    private lateinit var checkoutButton: Button
    private val args by navArgs<VehicleDetailFragmentArgs>()
    private lateinit var vehicleDetail: Vehicle
    private var storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var vehicleLocation: LatLng

    private lateinit var gMap: GoogleMap

    companion object {
        fun newInstance() = VehicleDetailFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var root = inflater.inflate(R.layout.vehicle_detail_fragment, container, false)
        checkoutButton = root.checkoutButton

        return root

    }


    fun getActionbar(): androidx.appcompat.app.ActionBar? {
        return (activity as MainActivity).supportActionBar
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        vehicleDetail = args.vehicleDetail

        getActionbar()?.title = getString(
            R.string.text_vehicle_primary_name,
            vehicleDetail.brand,
            vehicleDetail.model
        )

        GlideApp.with(view.context)
            .load(storage.getReferenceFromUrl(vehicleDetail.images[0]))
            .into(view.vehicledetail_image)
        view.vehicledetail_name.text = getString(
            R.string.text_vehicle_primary_name,
            vehicleDetail.brand,
            vehicleDetail.model
        )
        view.vehicledetail_price.text = getString(
            R.string.text_currency_price_short,
            vehicleDetail.price.toString()
        )

        view.vehicledetail_rating.text = getString(R.string.text_vehicle_rating, vehicleDetail.rating.toString(), vehicleDetail.trip.toString())

        when (vehicleDetail.type) {
            Constant.VEHICLE_TYPE_SEDAN -> {
                view.feature1_text.text = getString(R.string.text_vehicle_type_sedan)
                view.feature1_image.setImageResource(R.drawable.sedan)
            }

            Constant.VEHICLE_TYPE_HATCHBACK -> {
                view.feature1_text.text = getString(R.string.text_vehicle_type_hatchback)
                view.feature1_image.setImageResource(R.drawable.hatchback)
            }

            Constant.VEHICLE_TYPE_SUV -> {
                view.feature1_text.text = getString(R.string.text_vehicle_type_suv)
                view.feature1_image.setImageResource(R.drawable.suv)
            }

            Constant.VEHICLE_TYPE_VAN -> {
                view.feature1_text.text = getString(R.string.text_vehicle_type_van)
                view.feature1_image.setImageResource(R.drawable.van)
            }

            Constant.VEHICLE_TYPE_PICKUP -> {
                view.feature1_text.text = getString(R.string.text_vehicle_type_pickup)
                view.feature1_image.setImageResource(R.drawable.pickup)
            }

            Constant.VEHICLE_TYPE_JEEP -> {
                view.feature1_text.text = getString(R.string.text_vehicle_type_jeep)
                view.feature1_image.setImageResource(R.drawable.jeep)
            }
        }

        view.feature2_text.text = getString(R.string.text_vehicle_number_seat, vehicleDetail.seat)
        view.feature2_image.setImageResource(R.drawable.seat)


        when (vehicleDetail.transmission) {
            Constant.VEHICLE_TRANSMISSION_AT -> {
                view.feature3_text.text = getString(R.string.text_vehicle_transmission_at)
                view.feature3_image.setImageResource(R.drawable.at)
            }
            Constant.VEHICLE_TRANSMISSION_MT -> {
                view.feature3_text.text = getString(R.string.text_vehicle_transmission_mt)
                view.feature3_image.setImageResource(R.drawable.mt)
            }
        }

        view.feature4_text.text = getString(R.string.text_vehicle_mpg, vehicleDetail.mpg)
        view.feature4_image.setImageResource(R.drawable.mpg)

        val user = auth.currentUser

        checkoutButton.setOnClickListener {
            if (user != null) {
                val action = VehicleDetailFragmentDirections.actionVehicleDetailFragmentToCheckoutFragment(vehicleDetail)
                findNavController().navigate(action)
            } else {
                findNavController().navigate(R.id.action_vehicleDetailFragment_to_onboardingFragment3)
            }
        }

        if (user != null) {
            view.mapLayout.visibility = View.VISIBLE
            view.vehicledetail_location.text = getLocationName()
            val mapFragment = this.childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)

            view.reCenterMap.setOnClickListener {
                reCenterMap()
            }
        } else {
            view.vehicledetail_location.text = "You have to be logged in to see the vehicle location"
            view.mapLayout.visibility = View.GONE
        }



    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        vehicleLocation = LatLng(vehicleDetail.location.latitude, vehicleDetail.location.longitude)
        gMap.addMarker(MarkerOptions().position(vehicleLocation).title("Vehicle Location"))
        reCenterMap()
    }

    fun reCenterMap() {
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(vehicleLocation, 15f))
    }

    fun getLocationName(): String {
        val geoCoder = Geocoder(context, Locale.getDefault())
        val listAddress = geoCoder.getFromLocation(vehicleDetail.location.latitude, vehicleDetail.location.longitude, 1)
        Log.d(Constant.LOG_TAG, listAddress[0].getAddressLine(0))
        return listAddress[0].getAddressLine(0)
    }
}
