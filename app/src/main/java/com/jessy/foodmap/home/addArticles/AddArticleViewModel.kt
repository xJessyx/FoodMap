package com.jessy.foodmap.home.addArticles

import android.content.ContentValues
import android.icu.util.Calendar
import android.util.Log
import android.widget.ImageButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.Article

class AddArticleViewModel :ViewModel(){
    val db = Firebase.firestore
    var articleId = db.collection("articles").document().id


    val _addArticle = MutableLiveData<Article>()
    val addArticle: LiveData<Article>
        get() = _addArticle

    val articleTitle = MutableLiveData<String>()
    val articleConent = MutableLiveData<String>()
    var articleImage :String? = null
    var articlePlaceName :String =""

    // Create a storage reference from our app


    fun addFireBaseArticle() {
        val articleObject = addArticle.value

        if (articleObject != null) {
            db.collection("articles").document(articleId)
                .set(articleObject)
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
            id = articleId,
            image = articleImage ?: "",
            author = "jessy",
            authorImage = "https://images.unsplash.com/photo-1504203772830-87fba72385ee?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8Ym95fGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
            totalLike = 0,
            userId = "32fRAA8nlkV2gAojqHB1",
            title = articleTitle.value!!,
            content = articleConent.value!!,
            placeName = articlePlaceName,
            createdTime =  Calendar.getInstance().timeInMillis,
            favoriteUsers = mutableListOf()

        )

        _addArticle.value = data

    }


}