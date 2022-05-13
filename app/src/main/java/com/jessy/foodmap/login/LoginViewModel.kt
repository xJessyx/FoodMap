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
    var image: String = ""
    val _addUser = MutableLiveData<User>()
    val addUser: LiveData<User>
        get() = _addUser
    var userDocumentId = db.collection("users").document().id
    var getUser = mutableListOf<User>()

    val _newUser = MutableLiveData<String>()
    val newUser: LiveData<String>
        get() = _newUser

    val _getUserLiveData = MutableLiveData<List<User>>()
    val getUserLiveData: LiveData<List<User>>
        get() = _getUserLiveData

    fun addUser() {
        Log.v("Data1", "data")
        val data = User(
            age = 18,
            email = email,
            gender = "M",
            id = userDocumentId,
            image = image,
            level = 1,
            name = userName
        )
        Log.v("Data2", "$data")

        _addUser.value = data
        UserManager.user = data
        Log.v("user add ", "${UserManager.user}")
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
                    Log.v("user get", "${UserManager.user}")
                }
                if (result.isEmpty()) {
                    Log.v("_newUser.value", "${_newUser.value}")
                    _newUser.value = "true"
                    Log.v("_newUser.value", "${_newUser.value}")
                    Log.v("result.isEmpty()", "${result.isEmpty()}")
                } else {
                    _getUserLiveData.value = getUser
                    Log.v("getUser", "${getUser}")
                }

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)

            }


    }
}