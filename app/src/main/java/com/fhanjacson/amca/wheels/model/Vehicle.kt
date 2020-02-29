package com.fhanjacson.amca.wheels.model

import java.io.Serializable

class Vehicle : Serializable {
    lateinit var id:String
    lateinit var brand: String
    lateinit var model: String
    lateinit var images: List<String>
    var price: Double = 0.toDouble()
    lateinit var type: String
    var seat: Int = 0
    lateinit var  transmission: String
    var mpg: Int = 0
    var trip: Int = 0
    var rating: Double = 0.toDouble()
    lateinit var licenseplate: String




}