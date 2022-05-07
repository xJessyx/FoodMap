package com.jessy.foodmap.itinerary.add

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Journey
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class AddItineraryViewModel : ViewModel() {

    val _addItinerary = MutableLiveData<Journey>()
    val addItinerary: LiveData<Journey>
        get() = _addItinerary
    val db = Firebase.firestore
    val itineraryName = MutableLiveData<String>()
    val itineraryStartDate = MutableLiveData<String>()
    val itineraryEndDate = MutableLiveData<String>()
    var itineraryTotalDay :Int =0
    var itineraryImage:String?= null
    var journeyId = db.collection("journeys").document().id


    fun addFireBaseJourney() {
        val itineraryObject = addItinerary.value

        if (itineraryObject != null) {
            db.collection("journeys").document(journeyId)
                .set(itineraryObject)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfull")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }
    }

     fun differenceDay(){
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val mStart = LocalDate.parse(itineraryStartDate.value, format)
        val mEnd = LocalDate.parse(itineraryEndDate.value, format)
        val difference = ChronoUnit.DAYS.between(mStart, mEnd)
         itineraryTotalDay = difference.toInt()+1
        Log.v("difference","$difference")
    }

    fun addItineraryItem() {

        val data = Journey(
            id = journeyId,
            name =itineraryName.value!!,
            startDate =  itineraryStartDate.value!!,
            endDate =  itineraryEndDate.value!!,
            image = itineraryImage ?: "",
            totalDay = itineraryTotalDay,
            share = false
        )
        _addItinerary.value = data

    }

}

