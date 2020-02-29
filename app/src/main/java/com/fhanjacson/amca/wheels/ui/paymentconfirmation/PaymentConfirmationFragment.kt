package com.fhanjacson.amca.wheels.ui.paymentconfirmation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.Booking
import com.fhanjacson.amca.wheels.repository.FirestoreRepository
import com.fhanjacson.amca.wheels.ui.checkout.CheckoutViewModel
import kotlinx.android.synthetic.main.paymentconfirmation_fragment.view.*

class PaymentConfirmationFragment : Fragment() {

    lateinit var repo: FirestoreRepository

    private lateinit var viewModel: CheckoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        repo = FirestoreRepository()
        viewModel = ViewModelProvider(this).get(CheckoutViewModel::class.java)

        return inflater.inflate(R.layout.paymentconfirmation_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.textView3.text = viewModel.booking.totalprice.toString()

    }


}
