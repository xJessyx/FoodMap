package com.jessy.foodmap.data

import android.media.Image
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Journey(
    var id: String= "",
    var image: String = "",
    var name:String ="",
    var startDate: String = "",
    var endDate: String = "",
    var totalDay: Int =0,
    var status:Int =0,
    var userId: String =""
): Parcelable