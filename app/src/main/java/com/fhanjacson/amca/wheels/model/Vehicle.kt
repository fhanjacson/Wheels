package com.fhanjacson.amca.wheels.model

import java.io.Serializable

class Vehicle : Serializable {
    lateinit var id:String
    lateinit var brand: String
    lateinit var model: String
    lateinit var images: List<String>
    var price: Double = 0.toDouble()
}