package com.fhanjacson.amca.wheels.ui.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.AccountSetting
import com.google.firebase.auth.FirebaseAuth

class AccountAdapter(private val myDataset: ArrayList<AccountSetting>, callback: AccountAdapterInterface) :
    RecyclerView.Adapter<AccountSettingViewHolder>() {



    var auth = FirebaseAuth.getInstance()
    var callback: AccountAdapterInterface = callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountSettingViewHolder {
        return AccountSettingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_account_setting,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    override fun onBindViewHolder(holder: AccountSettingViewHolder, position: Int) {
        holder.accountSettingTextView.text = myDataset[position].name

        when (myDataset[position].id) {
            Constant.SETTING_LOGOUT -> {
                holder.itemLayout.setOnClickListener {
                    auth.signOut()
                    callback.updateUIAfterUserSignOut()
                    Toast.makeText(holder.itemView.context, "Logged Out", Toast.LENGTH_SHORT).show()
                }
            }

            Constant.SETTING_LOGIN -> {
                holder.itemLayout.setOnClickListener {
                    holder.itemLayout.findNavController().navigate(R.id.action_accountFragment2_to_onboardingFragment2)
                }
            }
        }
    }



    interface AccountAdapterInterface {
        fun updateUIAfterUserSignOut()
    }

}