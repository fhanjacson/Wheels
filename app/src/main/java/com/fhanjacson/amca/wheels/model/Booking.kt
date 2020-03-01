package com.fhanjacson.amca.wheels.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import java.io.Serializable
import java.util.*

open class Booking : Serializable {
//    lateinit var id: String
    lateinit var userID: String
    lateinit var vehicleID: String
    var startDate: Timestamp = Timestamp(Date(0))
    var endDate: Timestamp = Timestamp(Date(0))
    var totalPrice: Double = 0.toDouble()
    var totalDay = 0
    var bookingDate = Timestamp(Date(0))
    lateinit var vehicleName: String
}