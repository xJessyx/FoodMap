package com.jessy.foodmap.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var age:Int=0,
    var email: String="",
    var gender: String="",
    var id:String ="",
    var image :String ="",
    var level:Int = 0,
    var name:String="",
    var blockadeUser:MutableList<String> = mutableListOf()

    ): Parcelable
