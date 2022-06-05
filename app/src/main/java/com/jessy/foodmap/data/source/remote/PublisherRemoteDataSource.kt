package com.jessy.foodmap.data.source.remote


import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.jessy.foodmap.PublisherApplication
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Result
import com.jessy.foodmap.data.Article
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.Place
import com.jessy.foodmap.data.source.PublisherDataSource
import com.jessy.foodmap.login.UserManager
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object PublisherRemoteDataSource : PublisherDataSource {

    private const val PATH_ARTICLES = "articles"
    private const val PATH_JOURNEYS = "journeys"
    private const val PATH_PLACES = "places"
    private const val KEY_CREATED_TIME = "createdTime"
    private const val KEY_USERID = "userId"
    private const val KEY_STATUS = "status"
    private const val KEY_FAVORITEUSERS = "favoriteUsers"


    private val myAllJourneyList = mutableListOf<Journey>()

    override suspend fun getArticles(): Result<List<Article>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES)
            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Article>()
                    for (document in task.result!!) {

                        val article = document.toObject(Article::class.java)
                        list.add(article)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PublisherApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override fun getLiveArticles(): MutableLiveData<List<Article>> {

        val liveData = MutableLiveData<List<Article>>()

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES)
            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->

                exception?.let {
                    Log.v("exception", "$exception")
                }
                val list = mutableListOf<Article>()
                for (document in snapshot!!) {

                    val article = document.toObject(Article::class.java)
                    list.add(article)
                }

                liveData.value = list
            }
        return liveData
    }

    override fun getArticleCollect(): MutableLiveData<List<Article>> {
        val collectLiveData = MutableLiveData<List<Article>>()
        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES)
            .whereArrayContains(KEY_FAVORITEUSERS, UserManager.user!!.id)

            .addSnapshotListener { snapshot, exception ->

                exception?.let {
                    Log.v("exception", "$exception")
                }
                val collectList = mutableListOf<Article>()
                for (document in snapshot!!) {

                    val article = document.toObject(Article::class.java)
                    collectList.add(article)
                }

                collectLiveData.value = collectList
            }

        return collectLiveData
    }

}

