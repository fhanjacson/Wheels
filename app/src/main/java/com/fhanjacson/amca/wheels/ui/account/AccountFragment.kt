package com.fhanjacson.amca.wheels.ui.account

import android.accounts.Account
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fhanjacson.amca.wheels.R

class AccountFragment : Fragment() {


    private lateinit var accountViewModel: AccountViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var myDataset: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        accountViewModel =
            ViewModelProviders.of(this).get(AccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_account, container, false)
//        val textView: TextView = root.findViewById(R.id.text_account)
//        accountViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDataset = arrayOf("asd", "sad", "123", "Log Out")

        viewManager = LinearLayoutManager(context)
        viewAdapter = AccountAdapter(myDataset)

        recyclerView = view.findViewById<RecyclerView>(R.id.accountRecyclerview).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }



    }
}