package com.jessy.foodmap

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.User



class LoginMainActivityViewModel:  ViewModel() {

    val db = Firebase.firestore
    var userDocumentId = db.collection("users").document().id
    var userName:String= ""
    var email:String =""

    val _addUser = MutableLiveData<User>()
    val addUser: LiveData<User>
        get() = _addUser

    fun addUser(){
        val data = User(
            age = 0,
            email = email,
            gender ="",
            id=userDocumentId,
            image = "",
            level= 0,
            name = userName

        )
        _addUser.value  = data
    }

    fun addFireBaseUser(){
        val userObject = addUser.value

        if (userObject != null) {
            db.collection("users").document(userDocumentId)
                .set(userObject)
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "DocumentSnapshot successfull")
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }
        }


    }

}