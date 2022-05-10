package com.jessy.foodmap.itinerary.detailpaging

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.Place
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

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

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }


    fun updateMoveList(list: MutableList<Place>,fromPosition: Int, toPosition: Int){

//        val lists:List<Place> =list

        db.collection("journeys").document(journeyItemId)
            .collection("places").document(list[fromPosition].id)
            .update("startTime",list[toPosition].startTime)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "success")

            }
            .addOnFailureListener {
                Log.w(ContentValues.TAG, "Error adding document")
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