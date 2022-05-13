package com.jessy.foodmap.itinerary.invite

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
import com.jessy.foodmap.login.UserManager.Companion.user

class InviteViewModel(journeyArg: Journey) : ViewModel() {

   val db = Firebase.firestore
   var inviteImg=MutableLiveData<String>()
   var inviteId = db.collection("invitations").document().id
   val shareJourney = journeyArg



   val _addInvite = MutableLiveData<Invite>()
   val addInvite: LiveData<Invite>
      get() = _addInvite


   init {
      inviteImg.value = journeyArg.image
   }


   fun  addFireBaseInvitations(){

      val dataObject = addInvite.value
      if (dataObject != null) {
         db.collection("invitations").document(inviteId)
            .set(dataObject)
            .addOnSuccessListener {
               Log.d(ContentValues.TAG, "DocumentSnapshot successfull")
            }
            .addOnFailureListener { e ->
               Log.w(ContentValues.TAG, "Error adding document", e)
            }
      }
   }


   fun addInvitationsItem(email: String) {
         val data = Invite(
            id = inviteId,
            inviteStatus = 0,
            journeyId = shareJourney.id,
            journeyName = shareJourney.name,
            receiveEmail=user!!.email,
            receiveId = user!!.id,
            receiveName = user!!.name,
            senderEmail = email,
            senderId = "",
            senderName ="",
            senderImage = ""
         )
      _addInvite.value = data

   }







}