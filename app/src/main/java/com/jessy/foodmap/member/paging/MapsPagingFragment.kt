package com.jessy.foodmap.member.paging

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.content.res.Resources
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.maps.android.clustering.ClusterManager
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.R
import com.jessy.foodmap.data.MarkerItem
import com.jessy.foodmap.databinding.FragmentMapsPagingBinding

class MapsPagingFragment : Fragment(), OnMapReadyCallback {

    private lateinit var clusterManager: ClusterManager<MarkerItem>
    private lateinit var mMap: GoogleMap
    private val viewModel: MapsPagingViewModel by lazy {
        ViewModelProvider(this).get(MapsPagingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentMapsPagingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.getMyAllJourney()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        viewModel.myAllJourney.observe(viewLifecycleOwner){
            viewModel.getMyAllPlace()
        }
        // Set the lat/long coordinates for the marker.
//        val lat = 51.5009
//        val lng = -0.122
//
//// Set the title and snippet strings.
//        val title = "This is the title"
//        val snippet = "and this is the snippet."
//
//// Create a cluster item for the marker and set the title and snippet using the constructor.
//        val infoWindowItem = MarkerItem(lat, lng, title, snippet)
//
//// Add the cluster item (marker) to the cluster manager.
//        clusterManager.addItem(infoWindowItem)

        viewModel.myAllPlace.observe(viewLifecycleOwner){

            //addItems()
            setUpClusterer()

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
        }catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }


    private fun setUpClusterer() {
        // Position the map.,
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.105239181734905, 121.54844413550062), 10f))

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        clusterManager = ClusterManager(context, mMap)

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)

        // Add cluster items (markers) to the cluster manager.
       addItems()
    }

    private fun addItems() {

        for (i in 0 until viewModel.myAllPlaceList.size) {
            val lat = viewModel.myAllPlace.value!![i].latitude
            val lng = viewModel.myAllPlace.value!![i].longitude
            val name = viewModel.myAllPlace.value!![i].name

            val offsetItem =
                MarkerItem(lat!!, lng!!, name, "")
            Log.v("viewModel.myAllPlaceList.size","${viewModel.myAllPlaceList.size}")
            Log.v("i","$i")
            Log.v("offsetItem","${offsetItem.position}")
            clusterManager.addItem(offsetItem)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        setMapStyle(mMap)


    }

}