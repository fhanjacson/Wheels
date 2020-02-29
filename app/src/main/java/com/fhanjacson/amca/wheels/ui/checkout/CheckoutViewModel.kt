package com.fhanjacson.amca.wheels.ui.checkout

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.model.Booking

class CheckoutViewModel : ViewModel() {

    var booking = Booking()

//    var selectedVehicleID = ""
//    var

//    init {
//        booking = MutableLiveData(Booking())
//    }

//    fun getBooking(): Booking {
//        return booking
//    }
//
//    fun updateBooking(mbooking: Booking) {
//        Log.d(Constant.LOG_TAG, "updateBooking in viewmodel")
//        booking.value = mbooking
//        Log.d(Constant.LOG_TAG, booking.value!!.vehicleid)
//    }
//
//    fun updateUserID(uid: String) {
//        booking.value!!.userid = uid
//    }

}
