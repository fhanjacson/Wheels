package com.fhanjacson.amca.wheels.ui.search

import android.location.Geocoder
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.base.GlideApp
import com.fhanjacson.amca.wheels.model.Vehicle
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class VehicleListAdapter(private val vehicleList: List<Vehicle>) :
    RecyclerView.Adapter<VehicleListViewHolder>() {

    var storage = FirebaseStorage.getInstance()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleListViewHolder {
        return VehicleListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_vehicle,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }

    override fun onBindViewHolder(holder: VehicleListViewHolder, position: Int) {

        fun getLocationName(): String {
            val geoCoder = Geocoder(holder.itemView.context, Locale.getDefault())
            val listAddress = geoCoder.getFromLocation(vehicleList[position].location.latitude, vehicleList[position].location.longitude, 1)
            Log.d(Constant.LOG_TAG, listAddress[0].getAddressLine(0))
            return listAddress[0].getAddressLine(0)
        }

//        holder.starView.visibility = View.GONE

        holder.vehiclePrimaryName.text = holder.itemView.context.getString(
            R.string.text_vehicle_primary_name,
            vehicleList[position].brand,
            vehicleList[position].model
        )
        holder.vehiclePrice.text = holder.itemView.context.getString(
            R.string.text_currency_price_short,
            vehicleList[position].price.toString()
        )
        holder.vehicleLocation.text = getLocationName()
        GlideApp.with(holder.itemView.context)
            .load(storage.getReferenceFromUrl(vehicleList[position].images[0]))
            .into(holder.vehicleImageView)


        holder.itemView.setOnClickListener{ view ->
            val action = SearchFragmentDirections.actionSearchFragment2ToVehicleDetailFragment(vehicleList[position])
            view.findNavController().navigate(action)
        }


    }




}