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
import com.jessy.foodmap.login.UserManager
import com.jessy.foodmap.login.UserManager.Companion.user

class HomeViewModel : ViewModel(){

    val db = Firebase.firestore

    private val _navigateToDetail = MutableLiveData<Article>()
    val navigateToDetail: LiveData<Article>
        get() = _navigateToDetail

    private var getAllArticles = mutableListOf<Article>()
    private val _getAllArticlesLiveData = MutableLiveData<List<Article>>()
    val getAllArticlesLiveData: LiveData<List<Article>>
        get() = _getAllArticlesLiveData

    private var getAllArticlesCollect = mutableListOf<Article>()
    private val _getAllArticlesCollectLiveData = MutableLiveData<List<Article>>()
    val getAllArticlesCollectLiveData: LiveData<List<Article>>
        get() = _getAllArticlesCollectLiveData


    fun getFireBaseArticle(){
        db.collection("articles")
            .orderBy("createdTime", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->

                if (e != null) {
                    Log.w("yaya", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot?.documents?.isNullOrEmpty() == false) {

                    for (document in snapshot.documents) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        val data = document.toObject(Article::class.java)
                        data?.let {
                            getAllArticles.add(data)
                        }
                    }
                    _getAllArticlesLiveData.value = getAllArticles
                } else {
                    Log.d("yaya", "Current data: null")
                }

            }
    }

    fun getFireBaseArticleCollect(){
        db.collection("articles")
            .whereArrayContains("favoriteUsers", user!!.id)
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