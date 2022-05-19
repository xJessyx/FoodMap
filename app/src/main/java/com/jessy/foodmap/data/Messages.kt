package com.jessy.foodmap.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize

data class Messages (
    var id:String ="",
    var userName: String="",
    var userImage:String = "",
    var userId:String ="",
    var content: String = "",
    var createdTime: Long = -1,

): Parcelable
