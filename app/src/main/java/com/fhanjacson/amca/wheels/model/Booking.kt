package com.fhanjacson.amca.wheels.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import java.io.Serializable
import java.util.*

class Booking : Serializable {
    lateinit var userid: String
    lateinit var vehicleid: String
    var startdate: Timestamp = Timestamp(Date(0))
    var enddate: Timestamp = Timestamp(Date(0))
    var totalprice: Double = 0.toDouble()
    var totalDay = 0
    var timestamp = FieldValue.serverTimestamp()
}