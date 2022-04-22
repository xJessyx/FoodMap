package com.jessy.foodmap.data

import android.media.Image
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//@Parcelize
//data class Journey(
//    var journeImage: Int=0,
//    var journeyName:String ="",
//    var startDate: String = "",
//    var endtDate: String = "",
//    var startTime: String = "",
//    var endtTime: String="",
//    var dwellTime: String ="",
//    var user:String= "",
//    var transportation:String= ""
//): Parcelable

@Parcelize
data class Journey(
    var journeImage: Int = 0,
    var journeyName:String ="",
    var startDate: String = "",
    var endtDate: String = ""
//    var startTime: String = "",
//    var endtTime: String="",
//    var dwellTime: String ="",
//    var user:String= "",
//    var transportation:String= ""
): Parcelable