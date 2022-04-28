package com.jessy.foodmap.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Article

class HomeViewModel : ViewModel(){

    val dataList = mutableListOf<Article>()


    // Handle navigation to detail
    private val _navigateToDetail = MutableLiveData<Article>()

    val navigateToDetail: LiveData<Article>
        get() = _navigateToDetail

    //---------------------------------------------------------------------------------------
//
//    init {
//
//        addData()
//    }

    //---------------------------------------------------------------------------------------
//    fun addData(){
//        val item1 = Article(R.drawable.cake,"天天","5212")
//        val item2 = Article(R.drawable.cake_pops,"彎彎","234")
//        val item3 = Article(R.drawable.churros,"略綠","33")
//        val item4 = Article(R.drawable.cookies,"ㄏ黑","3432")
//        val item5 = Article(R.drawable.cupcakes,"嘶嘶嘶","5555")
//        val item6 = Article(R.drawable.macarons,"天已","5216662")
//        dataList.add(item1)
//        dataList.add(item2)
//        dataList.add(item3)
//        dataList.add(item4)
//        dataList.add(item5)
//        dataList.add(item6)
//    }

    fun navigateToDetail(article: Article) {
        _navigateToDetail.value = article
        Log.v("article","${article}")

    }
    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }
}