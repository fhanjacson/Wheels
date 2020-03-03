package com.fhanjacson.amca.wheels.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.model.*
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import java.util.ArrayList

class FirestoreRepository {
    val TAG = "FIREBASE_REPOSITORY"
    var fireStoreDB = FirebaseFirestore.getInstance()

    fun vehicleList(): CollectionReference {
        return fireStoreDB.collection("vehicleList")
    }

    fun tripList(): CollectionReference {
        return fireStoreDB.collection("tripList")
    }

    fun activityList(): CollectionReference {
        return fireStoreDB.collection("activityList")
    }

    fun getVehicleList(): MutableLiveData<List<Vehicle>> {
        val data = MutableLiveData<List<Vehicle>>()
        vehicleList().get().addOnSuccessListener { docs ->
            val mVehicleList = mutableListOf<Vehicle>()
            for (doc in docs) {
                val vehicle = doc.toObject(Vehicle::class.java)
                vehicle.id = doc.id
                mVehicleList.add(vehicle)
            }
            data.value = mVehicleList
        }
        return data
    }


    fun getVehicleList(query: Query): MutableLiveData<List<Vehicle>> {
        val data = MutableLiveData<List<Vehicle>>()
        query.get().addOnSuccessListener { docs ->
            val mVehicleList = mutableListOf<Vehicle>()
            for (doc in docs) {
                val vehicle = doc.toObject(Vehicle::class.java)
                vehicle.id = doc.id
                mVehicleList.add(vehicle)
            }
            data.value = mVehicleList
        }
        return data
    }

    fun addBooking(booking: Booking): Task<DocumentReference> {
        return tripList().add(booking)
    }

    fun getTripList(query: Query): MutableLiveData<List<BookingResponse>> {
        Log.d(Constant.LOG_TAG, "FirestoreRepository:getTripList")

        val data = MutableLiveData<List<BookingResponse>>()
        query.addSnapshotListener { docs, e ->
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

    fun addActivity(activity: ProfileActivity): Task<DocumentReference> {
        return activityList().add(activity).addOnSuccessListener {

        }
    }

    fun getActivityList(query: Query): MutableLiveData<List<ProfileActivityResponse>> {
        Log.d(Constant.LOG_TAG, "FirestoreRepository:getActivityList")

        val data = MutableLiveData<List<ProfileActivityResponse>>()
        query.addSnapshotListener { docs, e ->
            val mActivityList = mutableListOf<ProfileActivityResponse>()
            if (e != null) {
                Log.d(Constant.LOG_TAG, e.toString())
                data.value = mActivityList
                return@addSnapshotListener
            }
            if (docs != null) {
                for (doc in docs) {
                    val activity = doc.toObject(ProfileActivityResponse::class.java)
                    activity.id = doc.id
                    mActivityList.add(activity)
                }
                data.value = mActivityList
            } else {
                data.value = mActivityList
            }
        }
        return data

    }


}