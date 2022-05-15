package com.jessy.foodmap.itinerary.invite.paging

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Invite
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.login.UserManager

class JoinViewModel : ViewModel() {

    val db = Firebase.firestore

    var getWaitJoinInviteList = mutableListOf<Invite>()

    val _getWaitJoinInvite = MutableLiveData<List<Invite>>()
    val getWaitJoinInvite: LiveData<List<Invite>>
        get() = _getWaitJoinInvite

    var getJoinInviteList = mutableListOf<Invite>()

    val _getJoinInvite = MutableLiveData<List<Invite>>()
    val getJoinInvite: LiveData<List<Invite>>
        get() = _getJoinInvite

    fun getWaitJoinInviteItem(journeyIdArg:String) {
        db.collection("invitations")
            .whereEqualTo("journeyId",journeyIdArg)
            .whereEqualTo("receiveId", UserManager.user?.id)
            .whereEqualTo("inviteStatus",0)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    val data = document.toObject(Invite::class.java)
                    getWaitJoinInviteList.add(data)
                }
                Log.v("FieldValue.arrayUnion","$getWaitJoinInviteList")
                _getWaitJoinInvite.value = getWaitJoinInviteList

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    fun getJoinInviteItem(journeyIdArg:String) {
        db.collection("invitations")
            .whereEqualTo("journeyId",journeyIdArg)
            .whereEqualTo("receiveId", UserManager.user?.id)
            .whereEqualTo("inviteStatus",1)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    val data = document.toObject(Invite::class.java)
                    getJoinInviteList.add(data)
                }
                Log.v("getJoinInviteList","$getJoinInviteList")

                _getJoinInvite.value = getJoinInviteList

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }



}