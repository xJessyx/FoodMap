package com.jessy.foodmap.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreInformation (
    var storeImg:Int,
    var storeTitle:String,
    var storeAddress:String,
    var storeScore:String


): Parcelable