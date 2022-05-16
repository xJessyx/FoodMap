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
//
//    val _newUser = MutableLiveData<String>()
//    val newUser: LiveData<String>
//        get() = _newUser

    val _getUserLiveData = MutableLiveData<List<User>>()
    val getUserLiveData: LiveData<List<User>>
        get() = _getUserLiveData

    val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome



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
        Log.v("_addUser.value","${_addUser.value}")
        Log.v("2 add ", "${UserManager.user}")
    }

    fun addFireBaseUser(user: User) {

        val userObject = user
        db.collection("users").document(userDocumentId)
            .set(userObject)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "DocumentSnapshot successfull")
                _navigateToHome.value = true
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
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
//                    _newUser.value = "true"
                    addUser()

                    Log.v("result.isEmpty()", "${result.isEmpty()}")
                } else {
                    _getUserLiveData.value = getUser
                    _navigateToHome.value = true
                    Log.v("getUser", "${getUser}")
                }

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)

            }


    }

    fun onHomeNavigated() {
        _navigateToHome.value = null
    }
}