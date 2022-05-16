package com.jessy.foodmap.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Invite (
    var id:String ="",
    var inviteStatus: Int = 0,
    var journeyId: String ="",
    var journeyName: String ="",
    var receiveEmail:String="",
    var receiveId: String ="",
    var receiveName: String ="",
    var senderEmail: String="",
    var senderId: String ="",
    var senderName: String ="",
    var senderImage: String = ""
): Parcelable