package com.jessy.foodmap.itinerary.paging

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Journey

class MyItineraryPagingViewModel : ViewModel() {


    val db = Firebase.firestore
    var getAllJourney = mutableListOf<Journey>()

    val _getAllJourneyLiveData = MutableLiveData<List<Journey>>()
    val getAllJourneyLiveData: LiveData<List<Journey>>
        get() = _getAllJourneyLiveData

    private val _navigateToDetailDate = MutableLiveData<Journey>()
    val navigateToDetailDate: LiveData<Journey>
        get() = _navigateToDetailDate


    fun getFireBaseJourney() {

        db.collection("journeys")
            .whereEqualTo("userId","32fRAA8nlkV2gAojqHB1")
            .orderBy("startDate", Query.Direction.DESCENDING)
            .get()
                .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    val data = document.toObject(Journey::class.java)
                    getAllJourney.add(data)
                }
                _getAllJourneyLiveData.value = getAllJourney

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
        }

    fun navigateToDetailDate(journey: Journey) {
        _navigateToDetailDate.value = journey
    }

    fun onDetailNavigated() {
        _navigateToDetailDate.value = null
    }
}