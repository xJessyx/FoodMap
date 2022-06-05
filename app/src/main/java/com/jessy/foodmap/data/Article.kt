package com.jessy.foodmap.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    var id:String ="",
    var image: String ="",
    var author:String = "",
    var authorImage:String = "",
    var userId:String ="",
    var title:String = "",
    var content: String = "",
    var placeName:String = "",
    var createdTime: Long = -1,
    var favoriteUsers: MutableList<String> = mutableListOf(),
    var likeUsers: MutableList<String> = mutableListOf(),
    var latitude:Double? =null,
    var longitude:Double? =null

    ): Parcelable
