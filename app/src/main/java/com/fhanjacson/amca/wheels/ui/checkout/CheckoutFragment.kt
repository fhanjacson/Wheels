package com.fhanjacson.amca.wheels.ui.checkout

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Parcel
import android.util.Log
import android.util.Pair
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.AttrRes
import com.fhanjacson.amca.wheels.Constant

import com.fhanjacson.amca.wheels.R
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.checkout_fragment.view.*
import java.util.*

class CheckoutFragment : Fragment() {

    private lateinit var changeDateButton: Button
    private var materialDatePickerBuilder = MaterialDatePicker.Builder.dateRangePicker()
    lateinit var materialDatePicker: MaterialDatePicker<androidx.core.util.Pair<Long, Long>>

    companion object {
        fun newInstance() = CheckoutFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.checkout_fragment, container, false)
        changeDateButton = root.changeDateButton

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMaterialDateRangePicker(view)
        setupMaterialDateRangePickerCallback()

        changeDateButton.setOnClickListener {

            materialDatePicker.show(parentFragmentManager, materialDatePicker.toString())
        }

    }


    private fun resolveOrThrow(context: Context, @AttrRes attributeResId: Int): Int {
        val typedValue = TypedValue()
        if (context.theme.resolveAttribute(attributeResId, typedValue, true)) {
            return typedValue.data
        }
        throw IllegalArgumentException(context.resources.getResourceName(attributeResId))
    }

    fun setupMaterialDateRangePicker(view: View) {
        val calendarConstraints = CalendarConstraints.Builder()
        val calendar = Calendar.getInstance()
        calendarConstraints.setStart(calendar.timeInMillis)
        calendar.roll(Calendar.YEAR, 1)
        calendarConstraints.setEnd(calendar.timeInMillis)
        val validator: CalendarConstraints.DateValidator =
            DateValidatorPointForward.from(MaterialDatePicker.todayInUtcMilliseconds())
        calendarConstraints.setValidator(validator)

//        val fullscreenTheme = resolveOrThrow(view.context, R.attr.materialCalendarTheme)


        materialDatePickerBuilder.setCalendarConstraints(calendarConstraints.build())
//        materialDatePickerBuilder.setTheme(fullscreenTheme)
        materialDatePicker = materialDatePickerBuilder.build()
    }


    fun setupMaterialDateRangePickerCallback() {
        materialDatePicker.addOnCancelListener {
            Log.d(Constant.LOG_TAG, "Date Dialog Cancelled")
        }

        materialDatePicker.addOnNegativeButtonClickListener {
            Log.d(Constant.LOG_TAG, "Dialog Negative Button was clicked")
        }

        materialDatePicker.addOnPositiveButtonClickListener {
            Log.d(
                Constant.LOG_TAG,
                "Date String = ${materialDatePicker.headerText}::  Date epoch values::${it.first}:: to :: ${it.second}"
            )




        }
    }
}
