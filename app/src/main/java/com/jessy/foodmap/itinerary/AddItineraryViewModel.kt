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

//    val _addItinerary = MutableLiveData<List<Journey>>()
//    val addItinerary: LiveData<List<Journey>>
//        get() = _addItinerary

    val _addItinerary = MutableLiveData<Journey>()
    val addItinerary: LiveData<Journey>
        get() = _addItinerary

    val db = Firebase.firestore
    val itineraryName = MutableLiveData<String>()
    val itineraryStartDate = MutableLiveData<String>()
    val itineraryEndDate = MutableLiveData<String>()

    fun addFireBaseJourney() {
        Log.v("addItinerary", "${addItinerary.value}")

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
//        val list = mutableListOf<Journey>()
//        val data = Journey(
//            journeyName = itineraryName.value!!,
//           startDate = itineraryStartDate.value!!,
//           endtDate = itineraryEndDate.value!!
//        )
//        list.add(data)
//        _addItinerary.value= list

//        _addItinerary.value?.journeyName = itineraryName.value.toString()
//        _addItinerary.value?.startDate = itineraryStartDate.value.toString()
//        _addItinerary.value?.endtTime = itineraryEndDate.value.toString()

 //             _addItinerary.value.apply {
//            this?.journeyName =itineraryName.value.toString()
//            this?.startDate = itineraryStartDate.value.toString()
//            this?.endtTime = itineraryEndDate.value.toString()
//        }




        val data = Journey(
            journeyName = itineraryName.value!!,
           startDate = itineraryStartDate.value!!,
           endtDate = itineraryEndDate.value!!
        )
        _addItinerary.value = data

        Log.v("journeyName","${itineraryName.value}")
        Log.v("startDate","${itineraryStartDate.value}")
        Log.v("startDate","${itineraryEndDate.value}")
        Log.v("_addItinerary","${_addItinerary.value}")
    }

}

