package com.fhanjacson.amca.wheels.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.OnboardingActivity
import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.AccountSetting
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_account.view.*

class AccountFragment : Fragment(), AccountAdapter.AccountAdapterInterface {

    private lateinit var accountViewModel: AccountViewModel


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var myDataset: ArrayList<AccountSetting>
    private lateinit var profileImageView: ImageView
    private lateinit var accountView : View
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        auth = FirebaseAuth.getInstance()
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_account, container, false)

        profileImageView = root.profile_imageView

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountView = view
        myDataset = arrayListOf()

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            Log.d(Constant.LOG_TAG, "user is not null")
            view.accountName.text = user.displayName
            view.accountEmail.text = user.email
            myDataset.add(object : AccountSetting(Constant.SETTING_PROFILE, view.context.getString(R.string.text_profile)){})
            myDataset.add(object : AccountSetting(Constant.SETTING_LOGOUT, view.context.getString(R.string.text_logout)){})
        } else {
            view.accountCard.visibility = View.GONE
            myDataset.add(object : AccountSetting(Constant.SETTING_LOGIN, view.context.getString(R.string.text_login)){})
        }

        viewManager = LinearLayoutManager(context)
        viewAdapter = AccountAdapter(myDataset, this)

        recyclerView = view.accountRecyclerview.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }



    }

    override fun updateUIAfterUserSignOut() {
            accountView.accountCard.visibility = View.GONE
            myDataset.clear()
            myDataset.add(object : AccountSetting(Constant.SETTING_LOGIN, accountView.context.getString(R.string.text_login)){})
            viewAdapter.notifyDataSetChanged()
    }
}
