package com.fhanjacson.amca.wheels.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.model.Booking
import com.fhanjacson.amca.wheels.model.BookingResponse
import com.fhanjacson.amca.wheels.model.Vehicle
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import java.util.ArrayList

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

    fun getTripList(): Task<QuerySnapshot> {
        return tripList().get()
    }

    fun getTripList(query: Query): MutableLiveData<List<BookingResponse>> {
        Log.d(Constant.LOG_TAG, "FirestoreRepository:getTripList")

        val data = MutableLiveData<List<BookingResponse>>()
        query.addSnapshotListener{ docs, e ->
            val mTripList = mutableListOf<BookingResponse>()
            if (e != null) {
                Log.d(Constant.LOG_TAG, e.toString())
                data.value = mTripList
                return@addSnapshotListener
            }
            if (docs != null) {
                for (doc in docs) {
                    val trip = doc.toObject(BookingResponse::class.java)
                    trip.id = doc.id
                    mTripList.add(trip)
                }
                data.value = mTripList
            } else {
                data.value = mTripList
            }
        }
        return data

    }

}