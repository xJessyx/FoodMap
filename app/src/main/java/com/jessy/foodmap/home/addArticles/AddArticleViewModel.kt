package com.jessy.foodmap.home.addArticles

import android.content.ContentValues
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Article
import com.jessy.foodmap.login.UserManager.Companion.user

class AddArticleViewModel : ViewModel() {
    val db = Firebase.firestore

    private val _addArticle = MutableLiveData<Article>()
    val addArticle: LiveData<Article>
        get() = _addArticle

    val articleTitle = MutableLiveData<String>()
    val articleConent = MutableLiveData<String>()
    var articleImage: String? = null
    var articlePlaceName: String? = ""
    var articleLatitude: Double? = null
    var articleLongitude: Double? = null


    fun addFireBaseArticle() {
        val article = addArticle.value

        if (article != null) {
            val newDoc = db.collection("articles").document()
            val id = newDoc.id
            article.id = id
            newDoc.set(article)
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "DocumentSnapshot successfull")
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }
        }
    }

    fun addArticleItem() {
        val data = Article(
            image = articleImage ?: "",
            author = user!!.name,
            authorImage = user!!.image,
            likeUsers = mutableListOf(),
            userId = user!!.id,
            title = articleTitle.value!!,
            content = articleConent.value!!,
            placeName = articlePlaceName!!,
            createdTime = Calendar.getInstance().timeInMillis,
            favoriteUsers = mutableListOf(),
            latitude = articleLatitude,
            longitude = articleLongitude

        )
        _addArticle.value = data
    }

}