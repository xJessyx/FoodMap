package com.jessy.foodmap.member

import android.content.ContentValues
import android.os.UserManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.User
import com.jessy.foodmap.login.UserManager.Companion.user

class MemberViewModel : ViewModel() {

    val db = Firebase.firestore
    var getUser = mutableListOf<User>()
    private val _getUserLiveData = MutableLiveData<List<User>>()
    val getUserLiveData: LiveData<List<User>>
        get() = _getUserLiveData

    var memberImg = MutableLiveData<String>()

    fun getFireBaseUser() {

        db.collection("users")
            .whereEqualTo("id", user?.id)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    val data = document.toObject(User::class.java)
                    getUser.add(data)
                }
                _getUserLiveData.value = getUser

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

}
