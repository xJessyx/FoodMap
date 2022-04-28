package com.jessy.foodmap.data

import android.media.Image
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article (
    var id:String,
    var image: String,
    var author:String,
    var authorImage:String,
    var totalLike:Int
): Parcelable
