package com.jessy.foodmap.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var userName:String="",
    var age:Int=0,
    var gender: String="",
    var email: String="",
    var password: String="",
    var image :String ="",
    var level:Int = 0
): Parcelable
