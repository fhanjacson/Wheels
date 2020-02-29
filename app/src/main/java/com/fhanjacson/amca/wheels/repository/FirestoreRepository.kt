package com.fhanjacson.amca.wheels.repository

import com.fhanjacson.amca.wheels.model.Booking
import com.fhanjacson.amca.wheels.model.Vehicle
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FirestoreRepository {
    val TAG = "FIREBASE_REPOSITORY"
    var fireStoreDB = FirebaseFirestore.getInstance()
//    var user = FirebaseAuth.getInstance().currentUser

    fun vehicleList(): CollectionReference {
        return fireStoreDB.collection("vehicleList")
    }

    fun tripList(): CollectionReference {
        return fireStoreDB.collection("tripList")
    }

    fun getVehicleList(): Task<QuerySnapshot> {
        return vehicleList().get()
    }



    fun addBooking(booking: Booking): Task<DocumentReference> {
        return tripList().add(booking)
    }

}