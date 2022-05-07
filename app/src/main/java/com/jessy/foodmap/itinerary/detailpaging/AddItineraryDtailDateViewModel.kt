package com.jessy.foodmap.itinerary.detailpaging

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.Place

class AddItineraryDtailDateViewModel (position : Int,journeyArg: Journey): ViewModel(){

    val db = Firebase.firestore

    val _placeLiveData = MutableLiveData<List<Place>>()
    val placeLiveData: LiveData<List<Place>>
        get() = _placeLiveData
    var places = mutableListOf<Place>()
    var position =position
    var journeyItemId = journeyArg.id


    fun getPlaces(){

        db.collection("journeys").document(journeyItemId)
            .collection("places")
            .whereEqualTo("day",position+1)
            .orderBy("startTime", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    val data = document.toObject(Place::class.java)
                    places.add(data)

                }
                _placeLiveData.value = places
                Log.v("places","${places}")

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

}