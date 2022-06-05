package com.jessy.foodmap.member.paging

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.res.Resources
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.jessy.foodmap.R
import com.jessy.foodmap.data.MarkerItem
import com.jessy.foodmap.databinding.FragmentMapsPagingBinding
import com.jessy.foodmap.ext.getVmFactory

class MapsPagingFragment : Fragment(), OnMapReadyCallback {

    private val viewModel by viewModels<MapsPagingViewModel> { getVmFactory() }
    private lateinit var clusterManager: ClusterManager<MarkerItem>
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentMapsPagingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.getMyAllJourney()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.myAllJourney.observe(viewLifecycleOwner) {
            viewModel.getMyAllPlace()
            Log.v("myAllJourney","${it}")
        }
        viewModel.myAllPlace.observe(viewLifecycleOwner) {
            Log.v("myAllPlace observe","${it}")

            setUpClusterer()
            Log.v("myAllPlace observe2","${it}")

        }
        return binding.root

    }


    private fun setMapStyle(map: GoogleMap) {
        try {

            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    activity as Activity,
                    R.raw.map_style
                )
            )
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }


    private fun setUpClusterer() {
        Log.v("setUpClusterer","setUpClusterer--")
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.105239181734905,
            121.54844413550062), 10f))
        clusterManager = ClusterManager(context, mMap)
        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)
        Log.v("addItems11","addItems--")

        addItems()
        Log.v("addItem22","addItems--")

    }

   private fun addItems() {
            Log.v("addItem","1")
        for (i in 0 until viewModel.myAllPlace.value!!.lastIndex+1) {
            Log.v("viewModel.myAllPlace.value!!.lastIndex+1","${viewModel.myAllPlace.value!!.lastIndex+1}")
            val lat = viewModel.myAllPlace.value!![i].latitude
            val lng = viewModel.myAllPlace.value!![i].longitude
            val name = viewModel.myAllPlace.value!![i].name
            val offsetItem =
                MarkerItem(lat!!, lng!!, name, "")
                Log.v("offsetItem","${offsetItem.title}")

            clusterManager.addItem(offsetItem)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setMapStyle(mMap)

    }

}