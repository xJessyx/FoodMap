package com.jessy.foodmap.data.source.local

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.jessy.foodmap.data.Article
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.Place
import com.jessy.foodmap.data.Result
import com.jessy.foodmap.data.source.PublisherDataSource

class PublisherLocalDataSource (val context: Context) : PublisherDataSource {

    override suspend fun getArticles(): Result<List<Article>> {
        TODO("Not yet implemented")
    }

    override fun getLiveArticles(): MutableLiveData<List<Article>> {
        TODO("not implemented")
    }

    override fun getArticleCollect(): MutableLiveData<List<Article>> {
        TODO("Not yet implemented")
    }

    override fun getMyAllJourney(): MutableLiveData<List<Journey>> {
        TODO("Not yet implemented")
    }

    override fun getMyAllPlace(): MutableLiveData<List<Place>> {
        TODO("Not yet implemented")
    }
}