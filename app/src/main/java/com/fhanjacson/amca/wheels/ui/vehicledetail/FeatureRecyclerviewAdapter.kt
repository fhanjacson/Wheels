package com.fhanjacson.amca.wheels.ui.vehicledetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fhanjacson.amca.wheels.R

class FeatureRecyclerviewAdapter(private val features: Array<String>): RecyclerView.Adapter<FeatureViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        return FeatureViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_feature,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return features.size
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


class FeatureViewHolder(view: View) : RecyclerView.ViewHolder(view){

}