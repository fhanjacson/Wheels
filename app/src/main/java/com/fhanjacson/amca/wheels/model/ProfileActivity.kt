package com.fhanjacson.amca.wheels.model

import com.google.firebase.Timestamp
import java.util.*

open class ProfileActivity() {
    lateinit var userID: String
    var activityDate = Timestamp(Date(0))
    lateinit var activityType: String
    lateinit var activityMessage: String

    constructor(userID: String, activityDate: Timestamp, activityType: String, activityMessage: String) : this() {
        this.userID = userID
        this.activityDate = activityDate
        this.activityType = activityType
        this.activityMessage = activityMessage
    }


}