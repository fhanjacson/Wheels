package com.fhanjacson.amca.wheels.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.Vehicle
import com.google.firebase.storage.FirebaseStorage

class VehicleListAdapter(private val vehicleList: List<Vehicle>) :
    RecyclerView.Adapter<VehicleListViewHolder>() {
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

        holder.vehiclePrimaryName.text = holder.itemView.context.getString(
            R.string.text_vehicle_primary_name,
            vehicleList[position].brand,
            vehicleList[position].model
        )
        holder.vehiclePrice.text = holder.itemView.context.getString(
            R.string.text_currenct_short,
            vehicleList[position].price.toString()
        )

        FirebaseStorage.getInstance().getReferenceFromUrl(vehicleList[position].images[0])
            .downloadUrl.addOnSuccessListener { url ->
            holder.vehicleImageView.load(url) {
                crossfade(true)
            }
            Log.d(Constant.LOG_TAG, " Image URL: $url.toString()")
        }.addOnFailureListener { e ->
            Log.d(Constant.LOG_TAG, e.toString())
        }
    }
}