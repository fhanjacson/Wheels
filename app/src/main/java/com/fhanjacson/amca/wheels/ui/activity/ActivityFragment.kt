package com.fhanjacson.amca.wheels.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fhanjacson.amca.wheels.R
import kotlinx.android.synthetic.main.fragment_activity.*

class ActivityFragment : Fragment() {

    private lateinit var activityViewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activityViewModel =
            ViewModelProvider(this).get(ActivityViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_activity, container, false)
        val textView: TextView = text_activity
        activityViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}