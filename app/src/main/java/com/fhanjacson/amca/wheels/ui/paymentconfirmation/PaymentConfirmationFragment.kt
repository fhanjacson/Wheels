package com.fhanjacson.amca.wheels.ui.paymentconfirmation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.repository.FirestoreRepository
import com.fhanjacson.amca.wheels.ui.checkout.CheckoutViewModel
import kotlinx.android.synthetic.main.paymentconfirmation_fragment.view.*
import java.text.SimpleDateFormat
import java.util.*
import android.content.ClipData
import android.content.Context.CLIPBOARD_SERVICE
import android.content.ClipboardManager
import android.widget.Toast
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.MainActivity





class PaymentConfirmationFragment : Fragment() {

    private val repo = FirestoreRepository()
    private lateinit var viewModel: CheckoutViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.paymentconfirmation_fragment, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(CheckoutViewModel::class.java)

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sdf = SimpleDateFormat(Constant.DATE_FORMAT_PATTERN_WITH_TIME, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()

        view.tripid_text.text = viewModel.bookingid
        view.trip_timestamp.text = sdf.format(viewModel.booking.bookingDate.toDate())
        view.licenseplate_text.text = viewModel.selectedVehicle.licenseplate
        view.trip_price.text = getString(R.string.text_currency_price_short, viewModel.booking.totalPrice.toString())

        fun copyToClipboard(label: String, text: String) {
            val clipboard = view.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(label, text)
            clipboard.setPrimaryClip(clip)
        }

        view.tripid_text.setOnClickListener {
            copyToClipboard("Trip ID", viewModel.bookingid)
            Toast.makeText(context, "Trip ID copied", Toast.LENGTH_SHORT).show()
        }

        view.licenseplate_card.setOnClickListener {
            copyToClipboard("License Plate", viewModel.selectedVehicle.licenseplate)
            Toast.makeText(context, "License Plate copied", Toast.LENGTH_SHORT).show()

        }


        view.mytrip_button.setOnClickListener {
            (activity as MainActivity).setBottomNavBarIndex(R.id.activity_navigation)
        }




    }






}
