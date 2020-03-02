package com.fhanjacson.amca.wheels.ui.search

import androidx.lifecycle.ViewModel
import com.fhanjacson.amca.wheels.repository.FirestoreRepository
import com.google.firebase.firestore.Query

class SearchViewModel : ViewModel() {

    val repo = FirestoreRepository()

    val defaultVehicleFilterQuery = repo.vehicleList()

    var vehicleFilterQuery : Query = defaultVehicleFilterQuery

    var filterTypeString: String = ""
    var filterBrandString = ""
    var filterTransmissionString: String = ""


    var vehicleList = repo.getVehicleList(defaultVehicleFilterQuery)

    fun refreshVehicleList() {
        vehicleList = repo.getVehicleList(vehicleFilterQuery)
    }





}
