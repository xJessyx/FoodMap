package com.jessy.foodmap.detail

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.jessy.foodmap.data.Article
import java.util.*

class DetailViewModel(private val articleKey: Article): ViewModel(){
    private val _article = MutableLiveData<Article>().apply {
        value = articleKey
    }
    val article: LiveData<Article>
        get() = _article



}