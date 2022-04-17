package com.jessy.foodmap.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jessy.foodmap.data.Article

class DetailViewModel(private val arguments:Article) : ViewModel(){
    private val _article = MutableLiveData<Article>().apply {
        value = arguments
    }
    val article: LiveData<Article>
        get() = _article

}