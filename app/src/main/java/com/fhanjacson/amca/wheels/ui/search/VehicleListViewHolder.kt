package com.fhanjacson.amca.wheels.ui.search

import android.view.View
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.item_vehicle.view.*

class VehicleListViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val vehicleImageView = view.vehicleImage
    val vehiclePrimaryName = view.vehicleName
    val vehiclePrice = view.vehiclePrice
    val vehicleLocation = view.vehicleLocation
    val vehicleCard: CardView = view.vehicleCard
    val shimmerLayout = view.shimmer_view_container
    val starView = view.starView
}