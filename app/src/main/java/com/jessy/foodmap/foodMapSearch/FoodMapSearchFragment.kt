package com.jessy.foodmap.foodMapSearch

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.jessy.foodmap.BuildConfig
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentFoodMapSearchBinding


 class FoodMapSearchFragment : Fragment(), OnMapReadyCallback {


    val MY_PERMISSIONS_REQUEST_LOCATION = 100
    private lateinit var mMap: GoogleMap
    private var locationManager: LocationManager? = null
    private lateinit var placesClient: PlacesClient
    lateinit var autocompleteSessionToken: AutocompleteSessionToken
    var predictionList = mutableListOf<AutocompletePrediction>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        val binding = FragmentFoodMapSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

         initPlaces()
//        Places.initialize(requireActivity().getApplicationContext(), BuildConfig.MAPS_API_KEY)
//        placesClient = Places.createClient(activity as Activity)
      //   Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.search_autocomplete)
                    as AutocompleteSupportFragment

//        // Specify the types of place data to return.
        autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT)
//
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID,
            Place.Field.NAME,
            Place.Field.PHOTO_METADATAS,
            Place.Field.RATING,
            Place.Field.LAT_LNG))
        getSuggestions(Place.Field.NAME.toString())
        Log.v("predictionList1","$predictionList")
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.i(TAG, "Place: ${place.name}, ${place.id}, ${Place.Field.PHOTO_METADATAS}, ${Place.Field.RATING} , ${place.latLng}")
                mMap.addMarker(MarkerOptions().position(place.latLng))
            }

            override fun onError(status: Status) {
                Log.i(TAG, "An error occurred: $status")
            }
        })

        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //connent mapfragment
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        Log.v("mapFragment", "$mapFragment")
        mapFragment.getMapAsync(this)


        // Create persistent LocationManager reference
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        Log.v("locationManager", "$locationManager")

        try {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                0L,
                0f,
                locationListener)
        } catch (ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available")
        }

    }

    //define the listener
    private val locationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            Log.v("location", "$location")
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude,
                location.longitude), 12.0f))
            Log.v("座標", "${location.latitude} - ${location.longitude}")

        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        Log.v("b", "$requestCode $MY_PERMISSIONS_REQUEST_LOCATION")

        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            Log.v("a", "$requestCode $MY_PERMISSIONS_REQUEST_LOCATION")
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity as Activity, "已成功定位功能", Toast.LENGTH_SHORT).show()
                Log.v("已成功定位功能", "已成功定位功能")
                mMap.isMyLocationEnabled = true


            } else {
                Toast.makeText(activity as Activity, "需要定位功能", Toast.LENGTH_SHORT).show()
                Log.v("需要定位功能", "需要定位功能")

            }
        }

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        //確認定位權限是否開啟
        //----------bug----聽不到使用者權限是否開啟------只有第一次會跳權限設定-----
        if (ContextCompat.checkSelfPermission(this.requireActivity().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // ActivityCompat.requestPermissions(activity as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION)
            //    mMap.isMyLocationEnabled = true
        } else {
            Log.v("成功", "已定位")

        }

    }

    private fun initPlaces() {
        Places.initialize(requireActivity().getApplicationContext(), BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(activity as Activity)
        autocompleteSessionToken = AutocompleteSessionToken.newInstance()
    }

    private fun getSuggestions(query: String) {
        var findAutocompletePredictionsRequest = FindAutocompletePredictionsRequest
            .builder()
            .setSessionToken(autocompleteSessionToken)
            .setTypeFilter(TypeFilter.ADDRESS)
            .setQuery(query)
            .build()
        placesClient.findAutocompletePredictions(findAutocompletePredictionsRequest)
            .addOnCompleteListener(
                OnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TAG, "getSuggestions: -----------------------SUCCESS")
                        var predictionsResponse = it.result
                        predictionList =
                            predictionsResponse!!.autocompletePredictions
                        Log.v("predictionList2","$predictionList")

                        createList()
                        Log.v("predictionList3","$predictionList")

                    }
                })
        Log.v("predictionList4","$predictionList")


    }


    private fun createList() {
        var suggestionsStringArray = Array<String>(predictionList!!.size) { "" }
        if (predictionList!!.isNotEmpty()) {
            for (i in predictionList!!.indices) {
                suggestionsStringArray[i] = predictionList!![i].getFullText(null).toString()
                Log.v("suggestionsStringArray[]","${suggestionsStringArray[i]}")

            }
            Log.v("predictionList5","$predictionList")
            Log.v("suggestionsStringArray1","${suggestionsStringArray}")

            //  populateAdapter(suggestionsStringArray)
        }
        Log.v("suggestionsStringArray2","${suggestionsStringArray}")

    }


}

