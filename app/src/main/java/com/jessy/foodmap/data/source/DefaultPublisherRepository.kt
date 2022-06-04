package com.jessy.foodmap.data.source

import androidx.lifecycle.MutableLiveData
import com.jessy.foodmap.data.Article
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.Place
import com.jessy.foodmap.data.Result

class DefaultPublisherRepository (private val remoteDataSource: PublisherDataSource,
                                  private val localDataSource: PublisherDataSource
) : PublisherRepository {

    override suspend fun getArticles(): Result<List<Article>> {
        return remoteDataSource.getArticles()
    }

    override fun getLiveArticles(): MutableLiveData<List<Article>> {
        return remoteDataSource.getLiveArticles()
    }

    override fun getArticleCollect(): MutableLiveData<List<Article>> {
        return remoteDataSource.getArticleCollect()
    }

    override fun getMyAllJourney(): MutableLiveData<List<Journey>> {
        return remoteDataSource.getMyAllJourney()
    }

    override fun getMyAllPlace(): MutableLiveData<List<Place>> {
        return remoteDataSource.getMyAllPlace()
    }
}