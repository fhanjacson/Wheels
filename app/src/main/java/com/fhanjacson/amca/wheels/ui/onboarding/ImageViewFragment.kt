package com.fhanjacson.amca.wheels.ui.onboarding


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.fhanjacson.amca.wheels.R
import kotlinx.android.synthetic.main.fragment_image_view.view.*

/**
 * A simple [Fragment] subclass.
 */
class ImageViewFragment(resID: Int) : Fragment() {

    private var resourceID = resID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_image_view, container, false)
        root.getStartImageview.setImageResource(resourceID)
        return root
    }


}
