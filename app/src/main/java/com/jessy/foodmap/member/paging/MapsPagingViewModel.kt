package com.jessy.foodmap.member.paging

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Article
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.Place
import com.jessy.foodmap.data.source.PublisherRepository
import com.jessy.foodmap.login.UserManager
import com.jessy.foodmap.network.LoadApiStatus

class MapsPagingViewModel(private val repository: PublisherRepository) : ViewModel() {

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    var myAllJourney = MutableLiveData<List<Journey>>()
    var myAllPlace = MutableLiveData<List<Place>>()

//    init {
//        getMyAllJourneyResult()
//        getMyAllPlaceResult()
//    }

//    val db = Firebase.firestore
//    var myAllJourneyList = mutableListOf<Journey>()
//    var myAllPlaceList = mutableListOf<Place>()
//
//
//    private val _myAllJourney = MutableLiveData<List<Journey>>()
//    val myAllJourney: LiveData<List<Journey>>
//        get() = _myAllJourney
//
//    private val _myAllPlace = MutableLiveData<List<Place>>()
//    val myAllPlace: LiveData<List<Place>>
//        get() = _myAllPlace



//    fun getMyAllJourney() {
//
//        db.collection("journeys")
//            .whereEqualTo("userId", UserManager.user!!.id)
//            .whereEqualTo("status", 2)
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
//                    val data = document.toObject(Journey::class.java)
//                    myAllJourneyList.add(data)
//                }
//                _myAllJourney.value = myAllJourneyList
//            }
//            .addOnFailureListener { exception ->
//                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
//            }
//    }
//
//    fun getMyAllPlace() {
//        for (i in myAllJourneyList) {
//
//            db.collection("journeys").document(i.id)
//                .collection("places")
//                .get()
//                .addOnSuccessListener { result ->
//                    for (document in result) {
//                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
//                        val data = document.toObject(Place::class.java)
//                        myAllPlaceList.add(data)
//                    }
//                    _myAllPlace.value = myAllPlaceList
//                }
//                .addOnFailureListener { exception ->
//                    Log.d(ContentValues.TAG, "Error getting documents: ", exception)
//                }
//
//        }
//
//    }

    fun getMyAllJourneyResult() {
        myAllJourney = repository.getMyAllJourney()
        _status.value = LoadApiStatus.DONE
    }
    fun getMyAllPlaceResult() {
        myAllPlace = repository.getMyAllPlace()
        Log.v("myAllPlace ViewModel","${myAllPlace.value}")
        _status.value = LoadApiStatus.DONE
    }
}