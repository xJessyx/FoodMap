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

class InviteViewModel(journeyArg: Journey) : ViewModel() {

   val db = Firebase.firestore
   var inviteImg=MutableLiveData<String>()
   var inviteId = db.collection("invitations").document().id


   val _addInvite = MutableLiveData<Invite>()
   val addInvite: LiveData<Invite>
      get() = _addInvite


   init {
      inviteImg.value = journeyArg.image
   }


   fun  addFireBaseInvitations(){

      db.collection("invitations").document(inviteId)
         .set()
         .addOnSuccessListener {
            Log.d(ContentValues.TAG, "DocumentSnapshot successfull")
         }
         .addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error adding document", e)
         }
   }


   fun addInvitationsItem(){

         val data = Invite(

            invite_status = 0,
            journey_id = ,
            journey_name = ,
            receive_id = ,
            receive_name = ,
            sender_id = ,
            sender_name =

         )
      _addInvite.value = data

   }

}