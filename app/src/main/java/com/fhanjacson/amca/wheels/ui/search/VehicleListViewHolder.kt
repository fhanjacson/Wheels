package com.fhanjacson.amca.wheels.ui.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.item_vehicle.view.*

class VehicleListViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val vehicleImageView = view.vehicleImage
    val vehiclePrimaryName = view.vehicleName
    val vehiclePrice = view.vehiclePrice
    val vehicleLocation = view.vehicleLocation
    val shimmerLayout = view.shimmer_view_container
}