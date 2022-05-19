package com.jessy.foodmap.data

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem


class MarkerItem(
    lat: Double,
    lng: Double,
    title: String,
    snippet: String,
//    alpha: Float,

    ) : ClusterItem {

    private val position: LatLng
    private val title: String
    private val snippet: String
  //  private val alpha : Float


    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String? {
        return title
    }

    override fun getSnippet(): String? {
        return snippet
    }
//    override fun getAlpha(): Float {
//        return alpha
//    }


    init {
        position = LatLng(lat, lng)
        this.title = title
        this.snippet = snippet
  //      this.alpha = alpha
    }
}
