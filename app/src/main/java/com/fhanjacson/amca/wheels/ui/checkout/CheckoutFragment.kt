package com.fhanjacson.amca.wheels.ui.checkout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fhanjacson.amca.wheels.Constant

import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.Vehicle
import com.fhanjacson.amca.wheels.repository.FirestoreRepository
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.checkout_fragment.*
import kotlinx.android.synthetic.main.checkout_fragment.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class CheckoutFragment : Fragment() {

    private lateinit var changeDateButton: Button
    private var materialDatePickerBuilder = MaterialDatePicker.Builder.dateRangePicker()
    lateinit var materialDatePicker: MaterialDatePicker<androidx.core.util.Pair<Long, Long>>
    lateinit var auth: FirebaseAuth
    lateinit var repo: FirestoreRepository
    lateinit var viewmodel: CheckoutViewModel
    lateinit var viewCheckout: View
    lateinit var selectedVehicle: Vehicle
    private val args by navArgs<CheckoutFragmentArgs>()
    private var totalPrice = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.checkout_fragment, container, false)
        repo = FirestoreRepository()
        auth = FirebaseAuth.getInstance()
        changeDateButton = root.changeDateButton
        viewmodel = ViewModelProvider(this).get(CheckoutViewModel::class.java)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCheckout = view
        selectedVehicle = args.vehicle
        viewmodel.booking.vehicleid = selectedVehicle.id


        if (viewmodel.booking.startdate.toDate().time == 0.toLong() || viewmodel.booking.enddate.toDate().time == 0.toLong()) {
            view.startdate_text.text = "Select date first"
            view.enddate_text.text = "Select date first"
            view.totalprice_text.text = view.context.getString(R.string.text_currency_price_short, viewmodel.booking.totalprice.toString())
        } else {
           updateDateUI()
        }

        setupMaterialDateRangePicker()
        setupMaterialDateRangePickerCallback()

        val user = auth.currentUser
        if (user != null) {
            viewmodel.booking.userid = user.uid
        }

        changeDateButton.setOnClickListener {
            materialDatePicker.show(parentFragmentManager, materialDatePicker.toString())
        }

        proceed_button.setOnClickListener {
            if (viewmodel.booking.totalDay > 0 && viewmodel.booking.totalprice > 0) {
//               pay()
                findNavController().navigate(R.id.action_checkoutFragment_to_paymentFragment)

            }
        }

    }


    private fun setupMaterialDateRangePicker() {
        val calendarConstraints = CalendarConstraints.Builder()
        val calendar = Calendar.getInstance()
        calendarConstraints.setStart(calendar.timeInMillis)
        calendar.roll(Calendar.YEAR, 1)
        calendarConstraints.setEnd(calendar.timeInMillis)
        val validator: CalendarConstraints.DateValidator =
            DateValidatorPointForward.from(MaterialDatePicker.todayInUtcMilliseconds())
        calendarConstraints.setValidator(validator)
        materialDatePickerBuilder.setCalendarConstraints(calendarConstraints.build())
        materialDatePicker = materialDatePickerBuilder.build()
    }


    private fun setupMaterialDateRangePickerCallback() {
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
            viewmodel.booking.startdate = Timestamp(Date(it.first!!))
            viewmodel.booking.enddate = Timestamp(Date(it.second!!))
            val dayDiff = viewmodel.booking.enddate.toDate().time - viewmodel.booking.startdate.toDate().time
            viewmodel.booking.totalDay = TimeUnit.DAYS.convert(dayDiff, TimeUnit.MILLISECONDS).toInt() + 1
            viewmodel.booking.totalprice = selectedVehicle.price * viewmodel.booking.totalDay
            Log.d(Constant.LOG_TAG, "diff:  ${TimeUnit.DAYS.convert(dayDiff, TimeUnit.MILLISECONDS).toInt() + 1} days")
            updateDateUI()
        }
    }

    private fun updateDateUI() {
        val sdf = SimpleDateFormat("E, d MMMM YYYY", Locale.getDefault())
        viewCheckout.startdate_text.text = sdf.format(viewmodel.booking.startdate.toDate())
        viewCheckout.enddate_text.text = sdf.format(viewmodel.booking.enddate.toDate())
        viewCheckout.totalprice_text.text = viewCheckout.context.getString(R.string.text_currency_price_short, viewmodel.booking.totalprice.toString())
    }

    private fun pay() {
        repo.addBooking(viewmodel.booking).addOnSuccessListener {
            Toast.makeText(context, "Add document success! ${it.id}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_checkoutFragment_to_paymentFragment)
        }
    }


}
