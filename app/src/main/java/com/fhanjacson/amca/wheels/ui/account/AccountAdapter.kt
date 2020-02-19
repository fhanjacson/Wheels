package com.fhanjacson.amca.wheels.ui.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fhanjacson.amca.wheels.R

class AccountAdapter(private val myDataset: Array<String>) : RecyclerView.Adapter<AccountSettingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountSettingViewHolder {
        return AccountSettingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_account_setting, parent, false))

    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    override fun onBindViewHolder(holder: AccountSettingViewHolder, position: Int) {
        holder.accountSettingTextView.text = myDataset[position]
    }

}