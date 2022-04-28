package com.jessy.foodmap.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jessy.foodmap.data.Article

class DetailViewModel(private val articleKey: Article): ViewModel(){
    private val _article = MutableLiveData<Article>().apply {
        value = articleKey
    }
    val article: LiveData<Article>
        get() = _article
//    var author :String =""
//    var image :String = ""
//    var authorImage :String= ""

}