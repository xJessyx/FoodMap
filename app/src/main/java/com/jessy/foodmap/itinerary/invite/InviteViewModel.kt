package com.jessy.foodmap.itinerary.invite

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Invite
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.User
import com.jessy.foodmap.login.UserManager
import com.jessy.foodmap.login.UserManager.Companion.user

class InviteViewModel(journeyArg: Journey) : ViewModel() {

   val db = Firebase.firestore
   var inviteImg=MutableLiveData<String>()
   var inviteId = db.collection("invitations").document().id
   val shareJourney = journeyArg


   val _getSenderUser = MutableLiveData<List<User>>()
   val getSenderUser: LiveData<List<User>>
      get() = _getSenderUser


   val _addInvite = MutableLiveData<Invite>()
   val addInvite: LiveData<Invite>
      get() = _addInvite


   val _errorUser = MutableLiveData<Boolean>()
   val errorUser: LiveData<Boolean>
      get() = _errorUser


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

   fun checkUser(email: String) {

      db.collection ("users")
         .whereEqualTo("email", email)
         .get()
         .addOnSuccessListener { result ->

            val users = mutableListOf<User>()
            for (document in result) {
               Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
               val user = document.toObject(User::class.java)
               users.add(user)
            }

            if(result.isEmpty){
               _errorUser.value= true
               Log.v("error","${_errorUser.value}")
            }else{
               _getSenderUser.value = users

            }

         }


         .addOnFailureListener { exception ->
            Log.d(ContentValues.TAG, "Error getting documents: ", exception)
         }

   }


   fun addInvitationsItem(email: String, list: List<User>) {
         val data = Invite(
            id = inviteId,
            inviteStatus = 0,
            journeyId = shareJourney.id,
            journeyName = shareJourney.name,
            receiveEmail=user!!.email,
            receiveId = user!!.id,
            receiveName = user!!.name,
            senderEmail = email,
            senderId = list[0].id,
            senderName =list[0].name,
            senderImage = list[0].image
         )
      _addInvite.value = data
   Log.v("data","$data")

   }







}