package com.jessy.foodmap.data.source
import androidx.lifecycle.MutableLiveData
import com.jessy.foodmap.data.Result
import com.jessy.foodmap.data.Article
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.Place

interface PublisherRepository {

    suspend fun getArticles(): Result<List<Article>>

    fun getLiveArticles(): MutableLiveData<List<Article>>

    fun getArticleCollect(): MutableLiveData<List<Article>>

}