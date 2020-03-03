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
import com.fhanjacson.amca.wheels.base.GlideApp
import com.fhanjacson.amca.wheels.model.Vehicle
import com.fhanjacson.amca.wheels.repository.FirestoreRepository
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.checkout_fragment.*
import kotlinx.android.synthetic.main.checkout_fragment.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class CheckoutFragment : Fragment() {

    private lateinit var changeDateButton: Button
    private var materialDatePickerBuilder = MaterialDatePicker.Builder.dateRangePicker()
    lateinit var materialDatePicker: MaterialDatePicker<androidx.core.util.Pair<Long, Long>>
    private val auth = FirebaseAuth.getInstance()
    private val repo = FirestoreRepository()
    lateinit var viewmodel: CheckoutViewModel
    lateinit var viewCheckout: View
    lateinit var selectedVehicle: Vehicle
    private val args by navArgs<CheckoutFragmentArgs>()
    private var totalPrice = 0
    var storage = FirebaseStorage.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.checkout_fragment, container, false)
        changeDateButton = root.changeDateButton
        viewmodel = ViewModelProvider(requireActivity()).get(CheckoutViewModel::class.java)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCheckout = view
        selectedVehicle = args.vehicle
        viewmodel.booking.vehicleID = selectedVehicle.id
        viewmodel.booking.vehicleName = getString(
            R.string.text_vehicle_primary_name,
            selectedVehicle.brand,
            selectedVehicle.model
        )
        viewmodel.selectedVehicle = selectedVehicle


        GlideApp.with(view.context)
            .load(storage.getReferenceFromUrl(selectedVehicle.images[0]))
            .into(view.vehiclepreview_image)
        view.vehiclename_text.text = getString(
            R.string.text_vehicle_primary_name,
            selectedVehicle.brand,
            selectedVehicle.model
        )
        view.vehicleprice_text.text = view.context.getString(
            R.string.text_currency_price_short,
            selectedVehicle.price.toString()
        )




        if (viewmodel.booking.startDate.toDate().time == 0.toLong() || viewmodel.booking.endDate.toDate().time == 0.toLong()) {
            view.startdate_text.text = getString(R.string.text_selecte_date_first)
            view.enddate_text.text = getString(R.string.text_selecte_date_first)
            view.totalprice_text.text = getString(
                R.string.text_currency_price_short,
                viewmodel.booking.totalPrice.toString()
            )
        } else {
            updateDateUI()
        }

        setupMaterialDateRangePicker()
        setupMaterialDateRangePickerCallback()

        val user = auth.currentUser
        if (user != null) {
            viewmodel.booking.userID = user.uid
        }

        changeDateButton.setOnClickListener {
            materialDatePicker.show(parentFragmentManager, materialDatePicker.toString())
        }

        proceed_button.setOnClickListener {
            if (viewmodel.booking.totalDay > 0 && viewmodel.booking.totalPrice > 0) {
                pay()
            } else {
                Toast.makeText(context, "Please set the trip dates", Toast.LENGTH_LONG).show()
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
            viewmodel.booking.startDate = Timestamp(Date(it.first!!))
            viewmodel.booking.endDate = Timestamp(Date(it.second!!))
            val dayDiff =
                viewmodel.booking.endDate.toDate().time - viewmodel.booking.startDate.toDate().time
            viewmodel.booking.totalDay =
                TimeUnit.DAYS.convert(dayDiff, TimeUnit.MILLISECONDS).toInt() + 1
            viewmodel.booking.totalPrice =
                viewmodel.selectedVehicle.price * viewmodel.booking.totalDay
            Log.d(
                Constant.LOG_TAG,
                "diff:  ${TimeUnit.DAYS.convert(dayDiff, TimeUnit.MILLISECONDS).toInt() + 1} days"
            )
            updateDateUI()
        }
    }

    private fun updateDateUI() {
        val sdf = SimpleDateFormat(Constant.DATE_FORMAT_PATTERN_WITH_DAY, Locale.getDefault())
        viewCheckout.startdate_text.text = sdf.format(viewmodel.booking.startDate.toDate())
        viewCheckout.enddate_text.text = sdf.format(viewmodel.booking.endDate.toDate())
        if (viewmodel.booking.totalDay > 1) {
            viewCheckout.totalprice_text.text = viewCheckout.context.getString(
                R.string.text_checkout_total_plural,
                viewmodel.booking.totalPrice.toString(),
                viewmodel.booking.totalDay.toString()
            )
        } else {
            viewCheckout.totalprice_text.text = viewCheckout.context.getString(
                R.string.text_checkout_total_singular,
                viewmodel.booking.totalPrice.toString(),
                viewmodel.booking.totalDay.toString()
            )
        }
    }

    private fun pay() {
        viewmodel.booking.bookingDate = Timestamp(Date())
        repo.addBooking(viewmodel.booking).addOnSuccessListener {
            viewmodel.bookingid = it.id
            findNavController().navigate(R.id.action_checkoutFragment_to_paymentFragment)

//            it.get().addOnSuccessListener { doc ->
//                val mBooking = doc.toObject(Booking::class.java)
//                if (mBooking != null) {
//                    viewmodel.booking = mBooking
//                    Toast.makeText(context, "Add document success! ${it.id}", Toast.LENGTH_SHORT).show()
//                }
//            }
        }
    }


}
