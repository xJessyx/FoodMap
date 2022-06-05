package com.jessy.foodmap.itinerary.invite.paging

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Invite
import com.jessy.foodmap.login.UserManager

class JoinViewModel : ViewModel() {

    val db = Firebase.firestore
    var getWaitJoinInviteList = mutableListOf<Invite>()
    private val _getWaitJoinInvite = MutableLiveData<List<Invite>>()
    val getWaitJoinInvite: LiveData<List<Invite>>
        get() = _getWaitJoinInvite

    private var getJoinInviteList = mutableListOf<Invite>()

    private val _getJoinInvite = MutableLiveData<List<Invite>>()
    val getJoinInvite: LiveData<List<Invite>>
        get() = _getJoinInvite

    fun getWaitJoinInviteItem(journeyId: String) {
        db.collection("invitations")
            .whereEqualTo("journeyId", journeyId)
            .whereEqualTo("receiveId", UserManager.user?.id)
            .whereEqualTo("inviteStatus", 0)
            .addSnapshotListener { snapshot, e ->

                if (e != null) {
                    Log.w("yaya", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot?.documents?.isNullOrEmpty() == false) {

                    for (document in snapshot.documents) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        val data = document.toObject(Invite::class.java)
                        data?.let {

                            getWaitJoinInviteList.add(data)
                        }
                    }
                    _getWaitJoinInvite.value = getWaitJoinInviteList

                } else {
                    Log.d("yaya", "Current data: null")
                }

            }
    }

    fun getJoinInviteItem(journeyIdArg: String) {
        db.collection("invitations")
            .whereEqualTo("journeyId", journeyIdArg)
            .whereEqualTo("receiveId", UserManager.user?.id)
            .whereEqualTo("inviteStatus", 1)

            .addSnapshotListener { snapshot, e ->

                Log.d("yaya", "addSnapshotListener")
                if (e != null) {
                    Log.w("yaya", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot?.documents?.isNullOrEmpty() == false) {

                    for (document in snapshot.documents) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        val data = document.toObject(Invite::class.java)
                        data?.let {

                            getJoinInviteList.add(data)
                        }
                    }
                    _getJoinInvite.value = getJoinInviteList

                } else {
                    Log.d("yaya", "Current data: null")
                }

            }
    }

}