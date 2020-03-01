package com.fhanjacson.amca.wheels.ui.vehiclefilter


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.fhanjacson.amca.wheels.Constant

import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.ui.search.SearchViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_vehicle_filter.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class VehicleFilterFragment : Fragment() {

    private lateinit var viewmodel: SearchViewModel
    private lateinit var typeChipGroup: ChipGroup
    private lateinit var brandChipGroup: ChipGroup
    private lateinit var transmissionChipGroup: ChipGroup
    private lateinit var fview: View


    private var isTypeChipVisible = true
    private var isBrandChipVisible = true
    private var isTransmissionChipVisible = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewmodel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        return inflater.inflate(R.layout.fragment_vehicle_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fview = view
        typeChipGroup = view.type_chipGroup
        brandChipGroup = view.brand_chipGroup
        transmissionChipGroup = view.transmission_chipGroup

        setupFilterTitle()
        setupChips()

    }

    private fun setupFilterTitle() {
        fview.title_filter_type.setOnClickListener {
            if (isTypeChipVisible) {
                typeChipGroup.visibility = View.GONE
                isTypeChipVisible = !isTypeChipVisible
            } else {
                typeChipGroup.visibility = View.VISIBLE
                isTypeChipVisible = !isTypeChipVisible
            }
        }

        fview.title_filter_brand.setOnClickListener {
            if (isBrandChipVisible) {
                brandChipGroup.visibility = View.GONE
                isBrandChipVisible = !isBrandChipVisible
            } else {
                brandChipGroup.visibility = View.VISIBLE
                isBrandChipVisible = !isBrandChipVisible
            }
        }

        fview.title_filter_transmission.setOnClickListener {
            if (isTransmissionChipVisible) {
                transmissionChipGroup.visibility = View.GONE
                isTransmissionChipVisible = !isTransmissionChipVisible
            } else {
                transmissionChipGroup.visibility = View.VISIBLE
                isTransmissionChipVisible = !isTransmissionChipVisible
            }
        }

    }

    private fun setupChips() {
        setupTypeChip()
        setupBrandChip()
        setupTransmissionChip()
    }

    private fun setupTypeChip() {
        val vehicleType = resources.getStringArray(R.array.vehicle_types)
        for(type in vehicleType) {
            val chip = Chip(context)
            chip.text = type
            chip.isClickable = true
            chip.isCheckable = true
            chip.setOnCheckedChangeListener { _, b ->
                chip.isChecked = typeFilter(type.toLowerCase(Locale.getDefault()), b)
            }
            typeChipGroup.addView(chip)
        }
    }

    private fun setupBrandChip() {
        val vehicleBrands = resources.getStringArray(R.array.vehicle_brands)
        for(brand in vehicleBrands) {
            val chip = Chip(context)
            chip.text = brand
            chip.isClickable = true
            chip.isCheckable = true
            chip.setOnCheckedChangeListener { _, b ->
                chip.isChecked = brandFilter(brand, b)
            }
            brandChipGroup.addView(chip)
        }
    }

    private fun setupTransmissionChip() {
        val vehicleTransmission = resources.getStringArray(R.array.vehicle_transmissions)
        for(transmission in vehicleTransmission) {
            val chip = Chip(context)
            chip.text = transmission
            chip.isClickable = true
            chip.isCheckable = true
            chip.setOnCheckedChangeListener { _, b ->
                chip.isChecked = transmissionFilter(transmission.toLowerCase(Locale.getDefault()), b)
            }
            transmissionChipGroup.addView(chip)
        }
    }

    private fun typeFilter(type: String, boolean: Boolean): Boolean {
        if (boolean) {
            if (viewmodel.filterTypeList.size < 10) {
                viewmodel.filterTypeList.add(type)
                Log.d(Constant.LOG_TAG, viewmodel.filterTypeList.toString())
                return true
            } else {
                Toast.makeText(context, "Maximum 10 type filters", Toast.LENGTH_LONG).show()
                Log.d(Constant.LOG_TAG, viewmodel.filterTypeList.toString())
                return false
            }
        } else {
            if (viewmodel.filterTypeList.size > 0) {
                viewmodel.filterTypeList.remove(type)
                Log.d(Constant.LOG_TAG, viewmodel.filterTypeList.toString())
                return false
            }
        }
        return false
    }

    private fun brandFilter(brand: String, boolean: Boolean): Boolean {
        if (boolean) {
            if (viewmodel.filterBrandList.size < 10) {
                viewmodel.filterBrandList.add(brand)
                Log.d(Constant.LOG_TAG, viewmodel.filterBrandList.toString())
                return true
            } else {
                Toast.makeText(context, "Maximum 10 brand filters", Toast.LENGTH_LONG).show()
                Log.d(Constant.LOG_TAG, viewmodel.filterBrandList.toString())
                return false
            }
        } else {
            if (viewmodel.filterBrandList.size > 0) {
                viewmodel.filterBrandList.remove(brand)
                Log.d(Constant.LOG_TAG, viewmodel.filterBrandList.toString())
                return false
            }
        }
        return false
    }

    private fun transmissionFilter(transmission: String, boolean: Boolean): Boolean {
        if (boolean) {
            if (viewmodel.filterTranmissionList.size < 10) {
                viewmodel.filterTranmissionList.add(transmission)
                Log.d(Constant.LOG_TAG, viewmodel.filterTranmissionList.toString())
                return true
            } else {
                Toast.makeText(context, "Maximum 2 transmission filters", Toast.LENGTH_LONG).show()
                Log.d(Constant.LOG_TAG, viewmodel.filterTranmissionList.toString())
                return false
            }
        } else {
            if (viewmodel.filterTranmissionList.size > 0) {
                viewmodel.filterTranmissionList.remove(transmission)
                Log.d(Constant.LOG_TAG, viewmodel.filterTranmissionList.toString())
                return false
            }
        }
        return false
    }



}
