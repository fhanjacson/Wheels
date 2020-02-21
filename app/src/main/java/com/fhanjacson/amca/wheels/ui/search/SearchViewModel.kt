package com.fhanjacson.amca.wheels.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.model.Vehicle
import com.fhanjacson.amca.wheels.repository.FirestoreRepository
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot

class SearchViewModel : ViewModel() {

    val TAG = "SEARCH_VIEWMODEL"
    var firebaseRepository = FirestoreRepository()
    var vehicles: MutableLiveData<List<Vehicle>> = MutableLiveData()

    fun getRealTimeVehicleList() : LiveData<List<Vehicle>> {
        Log.d(Constant.LOG_TAG, "SearchViewModel getRealTimeVehicleList")

        firebaseRepository.vehicleList().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.d(Constant.LOG_TAG, "Listen failed.", e)
                vehicles.value = null
                return@EventListener
            }

            val vehicleList : MutableList<Vehicle> = mutableListOf()
            for (doc in value!!) {

                val vehicleItem = doc.toObject(Vehicle::class.java)
                vehicleItem.id = doc.id
                vehicleList.add(vehicleItem)
            }
            vehicles.value = vehicleList
        })
        return vehicles
    }

   fun getVehicleList() : LiveData<List<Vehicle>> {
       Log.d(Constant.LOG_TAG, "getVehicleList")

       firebaseRepository.vehicleList().get().addOnSuccessListener { docs ->
           val vehicleList: MutableList<Vehicle> = mutableListOf()
           for (doc in docs) {
               val vehicle = doc.toObject(Vehicle::class.java)
               vehicle.id = doc.id
               vehicleList.add(vehicle)
           }
           vehicles.value = vehicleList
       }

       return vehicles
   }


}