package com.fhanjacson.amca.wheels.ui.account

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.item_account_setting.view.*


class AccountSettingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val itemLayout: ConstraintLayout = view.accountSettingItemLayout
    val accountSettingTextView: TextView = view.accountsetting_textview
    val left_icon = view.icon_left
}

