package com.fhanjacson.amca.wheels.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fhanjacson.amca.wheels.model.Booking
import com.fhanjacson.amca.wheels.model.BookingResponse
import com.fhanjacson.amca.wheels.model.ProfileActivity
import com.fhanjacson.amca.wheels.model.ProfileActivityResponse
import com.fhanjacson.amca.wheels.repository.FirestoreRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class ActivityViewModel : ViewModel() {

    private val repo = FirestoreRepository()
    private val user = FirebaseAuth.getInstance()


    private val tripQuery = repo.tripList().orderBy("bookingDate", Query.Direction.DESCENDING)
        .whereEqualTo("userID", user.uid)

    private val activityQuery = repo.activityList().orderBy("activityDate", Query.Direction.DESCENDING)
        .whereEqualTo("userID", user.uid)

    val bookingList: LiveData<List<BookingResponse>> = getTripList(tripQuery)
    val activityList: LiveData<List<ProfileActivityResponse>> = getActivityList(activityQuery)


    private fun getTripList(query: Query): LiveData<List<BookingResponse>> {
        return repo.getTripList(query)
    }

    private fun getActivityList(query: Query): LiveData<List<ProfileActivityResponse>> {
        return repo.getActivityList(query)
    }
}




//    private val _text = MutableLiveData<String>().apply {
//        value = "This is Activity Fragment"
//    }
//    val text: LiveData<String> = _text
