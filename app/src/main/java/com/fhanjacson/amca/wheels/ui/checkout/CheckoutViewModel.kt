package com.fhanjacson.amca.wheels.ui.checkout

import androidx.lifecycle.ViewModel
import com.fhanjacson.amca.wheels.model.Booking
import com.fhanjacson.amca.wheels.model.Vehicle

class CheckoutViewModel : ViewModel() {

    var booking = Booking()
    var bookingid = ""
    var selectedVehicle = Vehicle()

}
