package com.jessy.foodmap.itinerary.paging

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.Place

class RecommendPagingViewModel : ViewModel() {

    val db = Firebase.firestore
    var getAllJourney = mutableListOf<Journey>()

    val _getAllJourneyLiveData = MutableLiveData<List<Journey>>()
    val getAllJourneyLiveData: LiveData<List<Journey>>
        get() = _getAllJourneyLiveData


    fun getFireBaseJourney() {

        db.collection("journeys")
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

    }