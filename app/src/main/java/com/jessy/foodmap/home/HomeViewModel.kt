package com.jessy.foodmap.home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Article
import com.jessy.foodmap.data.Journey

class HomeViewModel : ViewModel(){

    val db = Firebase.firestore


    // Handle navigation to detail
    private val _navigateToDetail = MutableLiveData<Article>()

    val navigateToDetail: LiveData<Article>
        get() = _navigateToDetail
  //  var articleId = db.collection("articles").document().id

    var getAllArticles = mutableListOf<Article>()
    val _getAllArticlesLiveData = MutableLiveData<List<Article>>()
    val getAllArticlesLiveData: LiveData<List<Article>>
        get() = _getAllArticlesLiveData

    var getAllArticlesCollect = mutableListOf<Article>()
    val _getAllArticlesCollectLiveData = MutableLiveData<List<Article>>()
    val getAllArticlesCollectLiveData: LiveData<List<Article>>
        get() = _getAllArticlesCollectLiveData


    fun getFireBaseArticle(){
        db.collection("articles")
            .orderBy("createdTime", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    val data = document.toObject(Article::class.java)
                    getAllArticles.add(data)
                }
                _getAllArticlesLiveData.value = getAllArticles

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    fun getFireBaseArticleCollect(){
        db.collection("articles")
            .whereArrayContains("favoriteUsers","32fRAA8nlkV2gAojqHB1")
           // .orderBy("createdTime", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    val data = document.toObject(Article::class.java)
                    getAllArticlesCollect.add(data)
                }
                _getAllArticlesCollectLiveData.value = getAllArticlesCollect

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    fun navigateToDetail(article: Article) {
        _navigateToDetail.value = article

    }
    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }
}