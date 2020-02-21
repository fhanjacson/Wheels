package com.fhanjacson.amca.wheels.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fhanjacson.amca.wheels.OnboardingActivity
import com.fhanjacson.amca.wheels.R
import kotlinx.android.synthetic.main.fragment_account.view.*

class AccountFragment : Fragment() {


    private lateinit var accountViewModel: AccountViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var myDataset: Array<String>
    private lateinit var profileImageView: ImageView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_account, container, false)

        profileImageView = root.profile_imageView

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDataset = arrayOf("asd", "sad", "123", "Log Out")

        viewManager = LinearLayoutManager(context)
        viewAdapter = AccountAdapter(myDataset)

        recyclerView = view.accountRecyclerview.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        profileImageView.setOnClickListener {
            val intent = Intent(context, OnboardingActivity::class.java)
            startActivity(intent)
        }

    }
}