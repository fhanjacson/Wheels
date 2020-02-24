package com.fhanjacson.amca.wheels.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

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

}