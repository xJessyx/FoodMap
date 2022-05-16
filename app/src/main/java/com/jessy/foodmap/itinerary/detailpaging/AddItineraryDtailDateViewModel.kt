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

    val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>>
        get() = _places
    var position =position
    var journeyItemId = journeyArg.id


    fun getPlaces(){

        var newList = mutableListOf<Place>()

        db.collection("journeys").document(journeyItemId)
            .collection("places")
            .whereEqualTo("day",position+1)
            .orderBy("startTime", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    val data = document.toObject(Place::class.java)
                    newList.add(data)

                }
                _places.value = newList

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    var isUpdating = false

    fun updateMoveList(list: MutableList<Place>,fromPosition: Int, toPosition: Int){



//        db.collection("journeys").document(journeyItemId)
//            .collection("places").document(list[fromPosition].id)
//            .update("startTime",list[toPosition].startTime)
//            .addOnSuccessListener {
//                Log.d(ContentValues.TAG, "startTime success")
//
//            }
//            .addOnFailureListener {
//                Log.w(ContentValues.TAG, "Error adding document")
//            }
//        db.collection("journeys").document(journeyItemId)
//            .collection("places").document(list[fromPosition].id)
//            .update("name",list[toPosition].name)
//            .addOnSuccessListener {
//                Log.d(ContentValues.TAG, "name success")
//
//            }
//            .addOnFailureListener {
//                Log.w(ContentValues.TAG, "Error adding document")
//            }
//        db.collection("journeys").document(journeyItemId)
//            .collection("places").document(list[fromPosition].id)
//            .update("dwellTime",list[toPosition].dwellTime)
//            .addOnSuccessListener {
//                Log.d(ContentValues.TAG, "dwellTime success")
//
//            }
//            .addOnFailureListener {
//                Log.w(ContentValues.TAG, "Error adding document")
//            }


        if (!isUpdating) {
            isUpdating = true
            val updateMap = mapOf(
                "startTime" to (list[toPosition].startTime?.minus(1) ?: 0)
            )

            db.collection("journeys").document(journeyItemId)
                .collection("places").document(list[fromPosition].id)
                .update(updateMap)
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "startTime success")
                    isUpdating = false
                    getPlaces()
                }
                .addOnFailureListener {
                    isUpdating = false
                    Log.w(ContentValues.TAG, "Error adding document")
                }
        }


    }


    fun delectFireBaseItem(list: MutableList<Place>,position: Int){
        db.collection("journeys").document(journeyItemId)
            .collection("places").document(list[position].id)
            .delete()

            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "delect success")

            }
            .addOnFailureListener {
                Log.w(ContentValues.TAG, "Error adding document")
            }
    }

}