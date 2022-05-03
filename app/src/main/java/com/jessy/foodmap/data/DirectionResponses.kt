package com.jessy.foodmap.data

import com.akexorcist.googledirection.model.GeocodedWaypoint
import com.google.gson.annotations.SerializedName
import okhttp3.Route

data class DirectionResponses(
    @SerializedName("geocoded_waypoints")
    var geocodedWaypoints: List<GeocodedWaypoint?>?,
    @SerializedName("routes")
    var routes: List<Route?>?,
    @SerializedName("status")
    var status: String?
)