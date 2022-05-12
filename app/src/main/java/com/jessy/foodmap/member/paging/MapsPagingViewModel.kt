package com.jessy.foodmap.member.paging

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.Place
import com.jessy.foodmap.login.UserManager

class MapsPagingViewModel :ViewModel() {

    val db = Firebase.firestore
    var myAllJourneyList = mutableListOf<Journey>()
    var myAllPlaceList = mutableListOf<Place>()


    val _myAllJourney = MutableLiveData<List<Journey>>()
    val myAllJourney: LiveData<List<Journey>>
        get() = _myAllJourney

    val _myAllPlace = MutableLiveData<List<Place>>()
    val myAllPlace: LiveData<List<Place>>
        get() = _myAllPlace

    fun getMyAllJourney(){

        db.collection("journeys")
            .whereEqualTo("userId", UserManager.user!!.id)
            .whereEqualTo("status",2)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    val data = document.toObject(Journey::class.java)
                    myAllJourneyList.add(data)
                }
                _myAllJourney.value = myAllJourneyList
                Log.v("myAllJourneyList","$myAllJourneyList")
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }


    fun getMyAllPlace(){
        for (i in myAllJourneyList){

            db.collection("journeys").document(i.id)
                .collection("places")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        val data = document.toObject(Place::class.java)
                        myAllPlaceList.add(data)
                    }
                    _myAllPlace.value = myAllPlaceList
                    Log.v("myAllPlaceList","$myAllPlaceList")
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                }

        }

    }
}