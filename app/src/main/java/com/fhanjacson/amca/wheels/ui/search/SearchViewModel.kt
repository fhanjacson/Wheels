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

    lateinit var vehiclestest : MutableList<Vehicle>

    var firebaseRepository = FirestoreRepository()
    var vehicles: MutableLiveData<List<Vehicle>> = MutableLiveData()




    fun getVehicleListtest(): MutableList<Vehicle> {
        return vehiclestest
    }

    fun getVehicleListFromFirestore() {
        firebaseRepository.vehicleList().get().addOnSuccessListener { documents ->
            if (documents != null) {
                val vehicletemp = mutableListOf<Vehicle>()
                for (doc in documents) {
                    val vehicle = doc.toObject(Vehicle::class.java)
                    vehicle.id = doc.id
                    vehicletemp.add(vehicle)
                }
                vehiclestest = vehicletemp
            }
        }
    }








    fun getRealTimeVehicleList() : LiveData<List<Vehicle>> {
        Log.d(Constant.LOG_TAG, "SearchViewModel getRealTimeVehicleList")

        firebaseRepository.vehicleList().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.d(Constant.LOG_TAG, e.toString())
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
           vehiclestest = vehicleList
       }

       return vehicles
   }


}