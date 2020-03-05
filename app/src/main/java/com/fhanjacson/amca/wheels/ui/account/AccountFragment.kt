package com.fhanjacson.amca.wheels.ui.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.base.GlideApp
import com.fhanjacson.amca.wheels.model.AccountSetting
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_account.view.*
import kotlinx.android.synthetic.main.fragment_account_detail.view.*

class AccountFragment : Fragment(), AccountAdapter.AccountAdapterInterface {



    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var myDataset: ArrayList<AccountSetting>
    private lateinit var profileImageView: ImageView
    private lateinit var accountView: View
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_account, container, false)

        profileImageView = root.profile_imageView

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountView = view
        myDataset = arrayListOf()

        val user = auth.currentUser
        if (user != null) {
            view.noUserLayout_account.visibility = View.GONE
            view.accountName.text = user.displayName
            view.accountEmail.text = user.email
            Log.d(Constant.LOG_TAG, "${user.photoUrl}")
            if (user.photoUrl != null) {
                GlideApp.with(view.context).load(user.photoUrl).into(view.profile_imageView)
            } else {
                view.profile_imageView.setImageResource(R.drawable.placeholder_profile_image)
            }
            myDataset.add(object : AccountSetting(Constant.SETTING_PROFILE, view.context.getString(R.string.text_profile)) {})
            myDataset.add(object : AccountSetting(Constant.SETTING_CHANGE_PASSWORD, view.context.getString(R.string.text_change_password)) {})
            myDataset.add(object : AccountSetting(Constant.SETTING_DELETE_PROFILE, view.context.getString(R.string.text_delete_profile)) {})
            myDataset.add(object : AccountSetting(Constant.SETTING_LOGOUT, view.context.getString(R.string.text_logout)) {})
        } else {
            view.noUserLayout_account.visibility = View.VISIBLE
            myDataset.add(object : AccountSetting(Constant.SETTING_LOGIN, view.context.getString(R.string.text_login)) {})
        }

        view.loginOrSignupButton_account.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment2_to_onboardingFragment2)
        }

        viewManager = LinearLayoutManager(context)
        viewAdapter = AccountAdapter(myDataset, this)

        recyclerView = view.accountRecyclerview.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = viewManager
            adapter = viewAdapter
        }


    }

    override fun updateUIAfterUserSignOut() {
        accountView.noUserLayout_account.visibility = View.VISIBLE
        myDataset.clear()
        myDataset.add(object : AccountSetting(Constant.SETTING_LOGIN, accountView.context.getString(R.string.text_login)) {})
        viewAdapter.notifyDataSetChanged()
    }
}
