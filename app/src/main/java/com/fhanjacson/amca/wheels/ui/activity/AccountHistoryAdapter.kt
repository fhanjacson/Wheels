package com.fhanjacson.amca.wheels.ui.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.ProfileActivityResponse
import kotlinx.android.synthetic.main.item_activity.view.*
import java.text.SimpleDateFormat
import java.util.*

class AccountHistoryAdapter(private val tripList: List<ProfileActivityResponse>) : RecyclerView.Adapter<AccountHistoryAdapter.AccountHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountHistoryViewHolder {
        return AccountHistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_activity,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return tripList.size
    }

    override fun onBindViewHolder(holder: AccountHistoryViewHolder, position: Int) {
        val sdf = SimpleDateFormat(Constant.DATE_FORMAT_PATTERN_WITH_TIME, Locale.getDefault())
        holder.activityID.text = tripList[position].id
        holder.activityDate.text = sdf.format(tripList[position].activityDate.toDate())
        holder.activityMessage.text = tripList[position].activityMessage
        holder.activityColor.setBackgroundResource(R.drawable.color_bar)

        when (tripList[position].activityType) {
            Constant.ACTIVITY_TYPE_ACCOUNT_CREATED -> {
                holder.activityType.text = holder.itemView.context.getText(R.string.text_account_created)
            }

            Constant.ACTIVITY_TYPE_UPDATE_PROFILE -> {
                holder.activityType.text = holder.itemView.context.getText(R.string.text_profile_updated)
            }

            Constant.ACTIVITY_TYPE_CHANGE_PASSWORD -> {
                holder.activityType.text = holder.itemView.context.getText(R.string.text_password_changed)
            }

            Constant.ACTIVITY_TYPE_ACCOUNT_DELETED -> {
                holder.activityType.text = holder.itemView.context.getText(R.string.text_account_deleted)
            }
        }
    }

    class AccountHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val activityID: TextView = itemView.activityid
        val activityDate = itemView.activityDate
        val activityType = itemView.activityType
        val activityMessage = itemView.activityMessage
        val activityColor = itemView.colorBar

    }
}