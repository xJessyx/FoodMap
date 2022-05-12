package com.jessy.foodmap.login

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.User


class LoginViewModel : ViewModel() {

    val db = Firebase.firestore
    var userName: String = ""
    var email: String = ""
    val _addUser = MutableLiveData<User>()
    val addUser: LiveData<User>
        get() = _addUser
    var userDocumentId = db.collection("users").document().id
    var getUser = mutableListOf<User>()

    val _getUserLiveData = MutableLiveData<List<User>>()
    val getUserLiveData: LiveData<List<User>>
        get() = _getUserLiveData

    fun addUser() {
    Log.v("Data","data")
        val data = User(
            age = 0,
            email = email,
            gender = "",
            id = userDocumentId,
            image = "",
            level = 0,
            name = userName
        )
        Log.v("Data","$data")

        _addUser.value = data

    }

    fun addFireBaseUser() {

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

    fun getFireBaseUser() {
        Log.v("email", "${email}")
        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    val data = document.toObject(User::class.java)
                    getUser.add(data)
                    UserManager.user = data
                    Log.v("user","${UserManager.user}")
                }
                _getUserLiveData.value = getUser

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                Log.v("Data","data")
                addUser()

            }


    }
}