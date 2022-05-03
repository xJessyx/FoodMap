package com.jessy.foodmap.addPlace

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.Place


class AddPlaceViewModel : ViewModel() {
    val db = Firebase.firestore

    val _addPlace = MutableLiveData<Place>()
    val addPlace: LiveData<Place>
        get() = _addPlace

    val _addAllJourney = MutableLiveData<List<Journey>>()
    val addAllJourney: LiveData<List<Journey>>
        get() = _addAllJourney

   var journeySinner : String =""
    var daySinner : Int = 0
    var transportationSinner : Int = 0
    var dwellTime : Long? = null
    var placeName :String = ""
    var startTime : Long? = null
    var trafficTime :Long? =3600000
    var getAllJourney = mutableListOf<Journey>()
    var placeId = db.collection("places").document().id
    var journeyId :String = ""
    var latitude:Double? =null
    var longitude:Double? =null

    fun getAllJourney() {
    db.collection("journeys")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
                val data = document.toObject(Journey::class.java)
                getAllJourney.add(data)
                Log.v("getAllJourney","$getAllJourney")

            }
            _addAllJourney.value = getAllJourney
            Log.v("getAllJourney2","$getAllJourney")
        }
        .addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
        }

}
    fun addFireBasePlace() {

        val placeObject = addPlace.value
        Log.v("placeObject.id","${placeObject!!.id}")

        if (placeObject != null ) {
            db.collection("journeys").document(journeyId)
                .collection("places").document(placeId)
                    .set(placeObject)
                    .addOnSuccessListener {
                        Log.d(ContentValues.TAG, "success")
                        Log.v("placeId","${placeId}")

                    }
                    .addOnFailureListener {
                        Log.w(ContentValues.TAG, "Error adding document")
                    }
        }
    }

    fun addPlaceItem(){
        val data = Place(
            id = placeId,
            name = placeName,
            day = daySinner,
            transportation = transportationSinner,
            dwellTime = dwellTime,
            startTime = startTime,
            trafficTime = trafficTime,
            latitude = latitude,
            longitude = longitude
        )
        _addPlace.value = data
    }

}

