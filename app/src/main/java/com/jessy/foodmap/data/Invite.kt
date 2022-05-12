package com.jessy.foodmap.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Invite (
    var invite_status: Int = 0,
    var journey_id: String ="",
    var journey_name: String ="",
    var receive_id: String ="",
    var receive_name: String ="",
    var sender_id: String ="",
    var sender_name: String =""
): Parcelable