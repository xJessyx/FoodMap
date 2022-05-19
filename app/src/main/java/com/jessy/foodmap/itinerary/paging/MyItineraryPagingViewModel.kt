package com.jessy.foodmap.itinerary.paging

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Invite
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.User
import com.jessy.foodmap.login.UserManager
import com.jessy.foodmap.login.UserManager.Companion.user

class MyItineraryPagingViewModel : ViewModel() {

    //    val ownerImage=MutableLiveData<String>()
    //  val coeditImage:String? =null
    val db = Firebase.firestore
    var getAllJourney = mutableListOf<Journey>()

    val _getAllJourneyLiveData = MutableLiveData<List<Journey>>()
    val getAllJourneyLiveData: LiveData<List<Journey>>
        get() = _getAllJourneyLiveData

    val _coEditUserInfos = MutableLiveData<List<User>>()
    val coEditUserInfos: LiveData<List<User>>
        get() = _coEditUserInfos

    private val _navigateToDetailDate = MutableLiveData<Journey>()
    val navigateToDetailDate: LiveData<Journey>
        get() = _navigateToDetailDate

    var checkInviteList = mutableListOf<Invite>()

    val _checkInvite = MutableLiveData<List<Invite>>()
    val checkInvite: LiveData<List<Invite>>
        get() = _checkInvite


    val _loadStatus = MutableLiveData<Boolean>()
    val loadStatus: LiveData<Boolean>
        get() = _loadStatus

    fun getFireBaseCoEditJourney() {
        db.collection("journeys")
            .whereArrayContains("coEditUser", user!!.id)
            .whereEqualTo("share", false)
            .orderBy("startDate", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->

                if (e != null) {
                    Log.w("yaya", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot?.documents?.isNullOrEmpty() == false) {

                    for (document in snapshot.documents) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        val data = document.toObject(Journey::class.java)
                        data?.let {
                            getAllJourney.add(data)
                        }
                    }
                    _getAllJourneyLiveData.value = getAllJourney
                    Log.v("getAllJourney","${getAllJourney}")
                } else {
                    Log.d("yaya", "Current data: null")
                }

            }

    }

    fun navigateToDetailDate(journey: Journey) {
        _navigateToDetailDate.value = journey
    }

    fun onDetailNavigated() {
        _navigateToDetailDate.value = null
    }

    fun checkInviteItem() {
        db.collection("invitations")
            .whereEqualTo("senderEmail", user?.email)
            .whereEqualTo("inviteStatus", 0)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    val data = document.toObject(Invite::class.java)
                    checkInviteList.add(data)
                }
                _checkInvite.value = checkInviteList

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    fun updateInviteStatusTrue(id: String) {
        db.collection("invitations").document(id)
            .update("inviteStatus", 1)
    }

    fun updateInviteStatusFalse(id: String) {
        db.collection("invitations").document(id)
            .update("inviteStatus", 2)
    }

    fun updateCoEdit(
        journeyId: String,
    ) {

        db.collection("journeys").document(journeyId)
            .update("coEditUser", FieldValue.arrayUnion(user!!.id))
    }


    fun queryAllUsers(ids: List<String>) {
        Log.d("yaya", "queryAllUsers ids=$ids")
        if (ids.isNullOrEmpty()) {
            return
        }
        db.collection("users")
            .whereIn("id", ids)
            .get()
            .addOnSuccessListener { result ->

                Log.d("yaya", "queryAllUsers success")
                val users = mutableListOf<User>()
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    val user = document.toObject(User::class.java)
                    Log.d("yaya", "user=$user")
                    users.add(user)
                }

                _coEditUserInfos.value = users

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }
}