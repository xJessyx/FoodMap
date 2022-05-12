package com.jessy.foodmap.data

import android.os.Parcelable
import com.jessy.foodmap.login.UserManager
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
    var userId: String = UserManager.user!!.id,
    var share: Boolean = false
): Parcelable