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
import com.jessy.foodmap.data.Journey

class AddArticleViewModel :ViewModel(){
    val db = Firebase.firestore
    var articleId = db.collection("articles").document().id


    val _addArticle = MutableLiveData<Article>()
    val addArticle: LiveData<Article>
        get() = _addArticle

    val articleTitle = MutableLiveData<String>()
    val articleConent = MutableLiveData<String>()
    val articleImage = MutableLiveData<String>()


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
            image = "https://images.unsplash.com/photo-1553452118-621e1f860f43?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MzE4fHxmb29kfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
            author = "jessy",
            authorImage = "https://images.unsplash.com/photo-1524250502761-1ac6f2e30d43?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTh8fGdpcmx8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
            totalLike = 0,
            userId = "32fRAA8nlkV2gAojqHB1",
            title = articleTitle.value!!,
            content = articleConent.value!!,
            placeName = "全家",
            createdTime =  Calendar.getInstance().timeInMillis
        )
        _addArticle.value = data

    }

}