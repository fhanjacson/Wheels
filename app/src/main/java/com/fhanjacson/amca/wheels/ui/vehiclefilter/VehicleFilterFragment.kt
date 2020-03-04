package com.fhanjacson.amca.wheels.ui.vehiclefilter


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

    private lateinit var filterTypeArray: Array<String>
    private lateinit var filterBrandArray: Array<String>
    private lateinit var filterTransmissionArray: Array<String>

    private var isTypeChipVisible = true
    private var isBrandChipVisible = true
    private var isTransmissionChipVisible = true

    private var isFilterChanged = false

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
        filterTypeArray = resources.getStringArray(R.array.vehicle_types)
        filterBrandArray = resources.getStringArray(R.array.vehicle_brands)
        filterTransmissionArray = resources.getStringArray(R.array.vehicle_transmissions)

        fview = view
        typeChipGroup = view.type_chipGroup
        brandChipGroup = view.brand_chipGroup
        transmissionChipGroup = view.transmission_chipGroup


        Log.d(Constant.LOG_TAG, "initial transmission: ${viewmodel.filterTransmissionString}")


        setupFilterTitle()
        resetChips()
        setupChips()

        view.viewResultsButton.setOnClickListener {
            setFilter()
        }

        view.resetFilterButton.setOnClickListener {
            resetFilter()
        }

    }


    private fun setFilter() {
        Log.d(Constant.LOG_TAG, "${viewmodel.filterTypeString}, ${viewmodel.filterBrandString}, ${viewmodel.filterTransmissionString}")
        if (isFilterChanged) {
            if (viewmodel.filterTypeString.isNotBlank()) {
                viewmodel.vehicleFilterQuery =
                    viewmodel.defaultVehicleFilterQuery.whereEqualTo(
                        "type",
                        viewmodel.filterTypeString
                    )
            } else {
                viewmodel.vehicleFilterQuery = viewmodel.defaultVehicleFilterQuery
            }

            if (viewmodel.filterBrandString.isNotBlank()) {
                Log.d(Constant.LOG_TAG, "setFilter")
                viewmodel.vehicleFilterQuery =
                    viewmodel.vehicleFilterQuery.whereEqualTo("brand", viewmodel.filterBrandString)
            }

            if (viewmodel.filterTransmissionString.isNotBlank()) {
                Log.d(Constant.LOG_TAG, "setFilter")
                viewmodel.vehicleFilterQuery =
                    viewmodel.vehicleFilterQuery.whereEqualTo(
                        "transmission",
                        viewmodel.filterTransmissionString
                    )
            }

            viewmodel.refreshVehicleList()
        } else {
            Log.d(Constant.LOG_TAG, "Filter didn't change")
        }
        findNavController().popBackStack()
    }

    private fun resetFilter() {
        checkFirstChip(typeChipGroup)
        checkFirstChip(brandChipGroup)
        checkFirstChip(transmissionChipGroup)
        viewmodel.vehicleFilterQuery = viewmodel.defaultVehicleFilterQuery
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

    private fun resetChips() {
        typeChipGroup.removeAllViews()
        brandChipGroup.removeAllViews()
        transmissionChipGroup.removeAllViews()
    }

    private fun setupChips() {
        setupTypeChip()
        setupBrandChip()
        setupTransmissionChip()
        setupInitCheck()
    }

    fun checkFirstChip(chipGroup: ChipGroup) {
        val chip = chipGroup.getChildAt(0) as Chip
        chip.isChecked = true
    }

    fun setupInitCheck() {
        if (viewmodel.filterTypeString.isBlank()) {
            checkFirstChip(typeChipGroup)
        }
        if (viewmodel.filterBrandString.isBlank()) {
            checkFirstChip(brandChipGroup)
        }
        if (viewmodel.filterTransmissionString.isBlank()) {
            checkFirstChip(transmissionChipGroup)
        }
    }

    private fun setupTypeChip() {
        val vehicleType = resources.getStringArray(R.array.vehicle_types)
        for (type in vehicleType) {
            val chip = Chip(context)
            chip.text = type
            chip.isClickable = true
            chip.isCheckable = true
            chip.setOnCheckedChangeListener { _, b ->
                if (b) {
                    typeFilter(type.toLowerCase(Locale.getDefault()), b)
                    Log.d(
                        Constant.LOG_TAG,
                        "viewmodel type: ${viewmodel.filterTypeString}"
                    )
                }
                isFilterChanged = true
            }
            typeChipGroup.addView(chip)
            chip.isChecked = viewmodel.filterTypeString == type.toLowerCase(Locale.getDefault())

        }
    }

    private fun setupBrandChip() {
        val vehicleBrands = resources.getStringArray(R.array.vehicle_brands)
        for (brand in vehicleBrands) {
            val chip = Chip(context)
            chip.text = brand
            chip.isClickable = true
            chip.isCheckable = true
            chip.setOnCheckedChangeListener { _, b ->
                if (b) {
                    brandFilter(brand, b)
                    Log.d(
                        Constant.LOG_TAG,
                        "viewmodel brand: ${viewmodel.filterBrandString}"
                    )
                }
                isFilterChanged = true
            }
            brandChipGroup.addView(chip)
            chip.isChecked = viewmodel.filterBrandString == brand
        }
    }

    private fun setupTransmissionChip() {
        val vehicleTransmission = resources.getStringArray(R.array.vehicle_transmissions)
        for (transmission in vehicleTransmission) {
            val chip = Chip(context)
            chip.text = transmission
            chip.isClickable = true
            chip.isCheckable = true
            chip.setOnCheckedChangeListener { _, b ->
                if (b) {
                    transmissionFilter(transmission.toLowerCase(Locale.getDefault()))
                    Log.d(
                        Constant.LOG_TAG,
                        "viewmodel Transmission: ${viewmodel.filterTransmissionString}"
                    )
                }
                isFilterChanged = true

            }
            transmissionChipGroup.addView(chip)
            chip.isChecked =
                viewmodel.filterTransmissionString == transmission.toLowerCase(Locale.getDefault())
        }
    }

    private fun typeFilter(type: String, boolean: Boolean) {
        if (boolean) {
            if (type != getString(R.string.text_filter_all_types).toLowerCase(Locale.getDefault())) {
                viewmodel.filterTypeString = type
            } else {
                viewmodel.filterTypeString = ""
            }
        }
    }

    private fun brandFilter(brand: String, boolean: Boolean) {
        if (boolean) {
            if (brand != getString(R.string.text_filter_all_brands)) {
                viewmodel.filterBrandString = brand
            } else {
                viewmodel.filterBrandString = ""
            }
        }
    }

    private fun transmissionFilter(transmission: String) {
        if (transmission != getString(R.string.text_filter_all_transmissions).toLowerCase(Locale.getDefault())) {
            viewmodel.filterTransmissionString = transmission
        } else {
            viewmodel.filterTransmissionString = ""
        }
    }


}
