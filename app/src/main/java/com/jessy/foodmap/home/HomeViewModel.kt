package com.jessy.foodmap.home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.PublisherApplication
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Article
import com.jessy.foodmap.login.UserManager.Companion.user
import com.jessy.foodmap.network.LoadApiStatus
import com.jessy.foodmap.util.ServiceLocator.repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.jessy.foodmap.data.Result
import com.jessy.foodmap.data.source.PublisherRepository
import com.jessy.foodmap.data.source.remote.PublisherRemoteDataSource.getArticleCollect

class HomeViewModel(private val repository: PublisherRepository) : ViewModel(){

    private val _navigateToDetail = MutableLiveData<Article>()
    val navigateToDetail: LiveData<Article>
        get() = _navigateToDetail

    private var _articles = MutableLiveData<List<Article>>()

    val articles: LiveData<List<Article>>
        get() = _articles

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error


    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    var liveArticles = MutableLiveData<List<Article>>()
    var articlesCollect = MutableLiveData<List<Article>>()

init {

    if (PublisherApplication.instance.isLiveDataDesign()) {
                getLiveArticlesResult()
    } else {
        getArticlesResult()
    }
    getArticleCollectResult()
}


    private fun getArticlesResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository?.getArticles()

            _articles.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = PublisherApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    fun navigateToDetail(article: Article) {
        _navigateToDetail.value = article

    }
    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

   fun getLiveArticlesResult() {
        liveArticles = repository.getLiveArticles()
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false
    }
    fun getArticleCollectResult() {
        articlesCollect = repository.getArticleCollect()
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false
    }

    fun refresh() {

        if (PublisherApplication.instance.isLiveDataDesign()) {
            _status.value = LoadApiStatus.DONE
            _refreshStatus.value = false

        } else {
            if (status.value != LoadApiStatus.LOADING) {
                getArticlesResult()
            }
        }
    }

}