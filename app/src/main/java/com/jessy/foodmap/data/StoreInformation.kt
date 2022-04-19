package com.jessy.foodmap.data

import android.graphics.Bitmap
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.PhotoMetadata
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreInformation(
    var storeImg: Bitmap? = null,
    var storeTitle:String ="",
    var storeAddress:String ="",
    var storeScore:String="",
    var storeLatLng: LatLng

): Parcelable