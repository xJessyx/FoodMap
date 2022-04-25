package com.jessy.foodmap.data

import android.media.Image
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Journey (
    var journeImage: Int,
    var journeyName:String,
    var startDate: String,
    var endtDate: String,
    var user:String
): Parcelable