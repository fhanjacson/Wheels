package com.fhanjacson.amca.wheels.ui.onboarding


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.GetStarted
import kotlinx.android.synthetic.main.fragment_onboarding_viewpager.view.*

/**
 * A simple [Fragment] subclass.
 */
class ImageViewFragment(getStarted: GetStarted) : Fragment() {

    private var getStarted = getStarted

    lateinit var onboardingImage: ImageView
    lateinit var primaryText: TextView
    lateinit var secondaryText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_onboarding_viewpager, container, false)
        onboardingImage = root.onboard_image
        primaryText = root.onboard_primary_text
        secondaryText = root.onboard_secondary_text
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onboardingImage.setImageResource(getStarted.resourceID)
        primaryText.text = getStarted.primaryText
        secondaryText.text = getStarted.secondaryText
    }
}
