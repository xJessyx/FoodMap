package com.jessy.foodmap.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Place(
        var id: String = "",
        var name:String = "",
        var day:Int = 0,
        var transportation: Int = 0,
        var dwellTime:  Long? = null,
        var startTime: Long? = null,
        var trafficTime: Long? = null
        ): Parcelable