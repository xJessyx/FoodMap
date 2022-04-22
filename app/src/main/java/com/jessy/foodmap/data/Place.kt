package com.jessy.foodmap.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Place (
        var PlaceyName:String,
        var anyDay:String,
        var transportation: String,
        var dwellTime: String
        ): Parcelable