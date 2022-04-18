package com.jessy.foodmap.data

import android.graphics.Bitmap
import android.os.Parcelable
import com.google.android.libraries.places.api.model.PhotoMetadata
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreInformation(
    var storeImg: Bitmap,
    var storeTitle:String,
    var storeAddress:String,
    var storeScore:String


): Parcelable