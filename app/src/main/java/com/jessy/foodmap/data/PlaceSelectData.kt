package com.jessy.foodmap.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceSelectData (

    val storelnformation: StoreInformation,
    val place: Place,
    val journey: Journey

        ): Parcelable