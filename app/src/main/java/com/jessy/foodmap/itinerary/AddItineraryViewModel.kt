package com.jessy.foodmap.itinerary

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Journey

class AddItineraryViewModel : ViewModel() {



    val _addItinerary = MutableLiveData<Journey>()
    val addItinerary: LiveData<Journey>
        get() = _addItinerary


    val db = Firebase.firestore
    val itineraryName = MutableLiveData<String>()
    val itineraryStartDate = MutableLiveData<String>()
    val itineraryEndDate = MutableLiveData<String>()

    fun addFireBaseJourney() {
        Log.v("addItinerary", "${addItinerary.value?.journeyName}")

        db.collection("journey")
            .add(addItinerary)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun addItineraryItem() {
        _addItinerary.value.apply {
            this?.journeyName = itineraryName.toString()
            this?.startDate = itineraryStartDate.toString()
            this?.endtDate = itineraryEndDate.toString()

        }
    }

}

