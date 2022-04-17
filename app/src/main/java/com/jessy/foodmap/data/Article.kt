package com.jessy.foodmap.data

import android.media.Image
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article (
    var image: Int,
    var author:String,
    var collectNumber: String
): Parcelable
