package com.jessy.foodmap.detail

import android.content.ContentValues
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Article
import com.jessy.foodmap.data.Messages
import com.jessy.foodmap.data.Place
import com.jessy.foodmap.login.UserManager.Companion.user

class DetailViewModel(private val articleKey: Article) : ViewModel() {

    val db = Firebase.firestore


    val _favoriteStatus = MutableLiveData<Boolean>()
    val favoriteStatus: LiveData<Boolean>
        get() = _favoriteStatus

    val _likeStatus = MutableLiveData<Boolean>()
    val likeStatus: LiveData<Boolean>
        get() = _likeStatus

    val _addMessage = MutableLiveData<Messages>()
    val addMessage: LiveData<Messages>
        get() = _addMessage


    val messageCotent = MutableLiveData<String>()

    val _getMessageLiveData = MutableLiveData<List<Messages>>()
    val getMessageLiveData: LiveData<List<Messages>>
        get() = _getMessageLiveData

    private val _article = MutableLiveData<Article>().apply {
        value = articleKey

    }
    val article: LiveData<Article>
        get() = _article

    init {
        Log.d("DetailViewModel init", "articleKey:$articleKey")
    }

    fun updateLike() {
        article.value?.let {
            db.collection("articles").document(it.id)
                .update("likeUsers", FieldValue.arrayUnion(user!!.id))
        }

    }

    fun updateRemoveLike() {
        article.value?.let {
            db.collection("articles").document(it.id)
                .update("likeUsers", FieldValue.arrayRemove(user!!.id))
        }


    }

    fun updateCollect() {
        Log.v("updateCollect", "articles.id${article.value?.id}")
        Log.v("user!!.id", "${user!!.id}")
        article.value?.let {
            db.collection("articles").document(it.id)
                .update("favoriteUsers", FieldValue.arrayUnion(user!!.id))
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.v("bb", "addOnCompleteListener isSuccessful")
                    } else {
                        Log.v("bb", "addOnCompleteListener exception${task.exception}")
                    }
                }
        }


    }

    fun updateRemoveCollect() {
        article.value?.let {

            db.collection("articles").document(it.id)
                .update("favoriteUsers", FieldValue.arrayRemove(user!!.id))
        }

    }

    fun checkFavoriteStatus() {
        db.collection("articles")
            .whereArrayContains("favoriteUsers", user!!.id)
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    val data = document.toObject(Article::class.java)

                    if (data.id == article.value?.id) {
                        _favoriteStatus.value = true
                    }

                }
                if (result.isEmpty) {
                    _favoriteStatus.value = false
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }

    }

    fun checkLikeStatus() {
        user?.id?.let {
            db.collection("articles")
                .whereArrayContains("likeUsers", it)
                .get()
                .addOnSuccessListener { result ->

                    for (document in result) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        val data = document.toObject(Article::class.java)

                        if (data.id == article.value?.id) {
                            _likeStatus.value = true
                        }

                    }
                    if (result.isEmpty) {
                        _likeStatus.value = false
                    }

                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                }
        }
    }


    fun getFireBaseMessages() {

        var MessageList = mutableListOf<Messages>()

        article.value?.let {
            db.collection("articles").document(it.id)
                .collection("messages")
                .orderBy("createTime", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        val data = document.toObject(Messages::class.java)
                        MessageList.add(data)

                    }
                    _getMessageLiveData.value = MessageList

                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                }
        }

    }

    fun addFireBaseMessages() {

        val message = addMessage.value

        if (message != null) {

            val newDoc = article.value?.id?.let {
                db.collection("articles").document(it)
                    .collection("messages").document()
            }
            val id = newDoc?.id

            message.id = id
            if (newDoc != null) {
                newDoc.set(message)
                    .addOnSuccessListener {
                        Log.d(ContentValues.TAG, "DocumentSnapshot successfull")
                    }
                    .addOnFailureListener { e ->
                        Log.w(ContentValues.TAG, "Error adding document", e)
                    }
            }

        }

    }


    fun addMessagesItem() {
        val data = Messages(
            userName = user?.name,
            userImage = user?.image,
            userId = user?.id,
            content = messageCotent.value,
            createdTime = Calendar.getInstance().timeInMillis

        )

        _addMessage.value = data

    }
}