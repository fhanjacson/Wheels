package com.fhanjacson.amca.wheels.ui.activity

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.BookingResponse
import kotlinx.android.synthetic.main.item_trip.view.*
import java.text.SimpleDateFormat
import java.util.*


class TripAdapter(private val tripList: List<BookingResponse>) : RecyclerView.Adapter<TripAdapter.TripViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        return TripViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_trip,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return tripList.size
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        var sdf = SimpleDateFormat(Constant.DATE_FORMAT_PATTERN_WITH_DAY, Locale.getDefault())

        holder.tripid.text = holder.itemView.context.getString(R.string.text_trip_id, tripList[position].id)
        holder.tripstart.text = sdf.format(tripList[position].startDate.toDate())
        holder.tripend.text = sdf.format(tripList[position].endDate.toDate())
        holder.vehiclename.text = tripList[position].vehicleName
        sdf = SimpleDateFormat(Constant.DATE_FORMAT_PATTERN_WITH_TIME, Locale.getDefault())
        holder.tripdate.text  = holder.itemView.context.getString(R.string.text_trip_booked_on, sdf.format(tripList[position].bookingDate.toDate()))
        holder.tripprice.text = holder.itemView.context.getString(R.string.text_checkout_total_plural, tripList[position].totalPrice.toString(), tripList[position].totalDay.toString())



    }


    class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tripid = itemView.tripid_text
        val tripdate = itemView.tripdate_text
        val tripprice = itemView.triptotalprice
        val tripstart = itemView.startdate_text
        val tripend = itemView.enddate_text
        val vehiclename = itemView.vehiclename_text
    }
}
