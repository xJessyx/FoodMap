package com.jessy.foodmap.foodMapSearch

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
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
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.*
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.jessy.foodmap.BuildConfig
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Article
import com.jessy.foodmap.data.StoreInformation
import com.jessy.foodmap.databinding.FragmentFoodMapSearchBinding
import com.jessy.foodmap.databinding.FragmentHomeBinding
import com.jessy.foodmap.home.HomeAdapter


class FoodMapSearchFragment : Fragment(), OnMapReadyCallback {


    val MY_PERMISSIONS_REQUEST_LOCATION = 100
    private lateinit var mMap: GoogleMap
    private var locationManager: LocationManager? = null
    private lateinit var placesClient: PlacesClient
    lateinit var autocompleteSessionToken: AutocompleteSessionToken
    var predictionList = mutableListOf<AutocompletePrediction>()
//    private val AUTOCOMPLETE_REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        (activity as MainActivity).hideToolBar()

        val binding = FragmentFoodMapSearchBinding.inflate(inflater, container, false)
        //binding.lifecycleOwner = viewLifecycleOwner
        initPlaces()
//        Places.initialize(requireActivity().getApplicationContext(), BuildConfig.MAPS_API_KEY)
//        placesClient = Places.createClient(activity as Activity)


        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
//        val fields = listOf(Place.Field.ID, Place.Field.NAME)
//
//        // Start the autocomplete intent.
//        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
//            .build(activity as Activity)
//        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)


        //   Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.search_autocomplete)
                    as AutocompleteSupportFragment

//        // Specify the types of place data to return.
        // autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT)

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID,
            Place.Field.NAME,
            Place.Field.PHOTO_METADATAS,
            Place.Field.RATING,
            Place.Field.LAT_LNG))
        getSuggestions(Place.Field.NAME.toString())
        Log.v("predictionList1", "$predictionList")
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.i(TAG,
                    "Place: ${place.name}, ${place.id}, ${Place.Field.PHOTO_METADATAS}, ${Place.Field.RATING} , ${place.latLng}")
                mMap.addMarker(MarkerOptions().position(place.latLng))
            }

            override fun onError(status: Status) {
                Log.i(TAG, "An error occurred: $status")
            }
        })

        val adapter = FoodMapSearchAdapter()
        binding.searchRecyclerView.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner

        val dataList1 = mutableListOf<StoreInformation>()

        val item1 = StoreInformation(R.drawable.cake, "天天", ":台灣少莉莉路123號5樓", "5.0")
        val item2 = StoreInformation(R.drawable.cake_pops, "彎彎", "金星省原來路235號1樓", "4.6")
        val item3 = StoreInformation(R.drawable.churros, "略綠", "黑心省黃泉路33號9樓", "8.2")
        val item4 = StoreInformation(R.drawable.cookies, "ㄏ黑", "台北彎彎曲第一人民廣場5號3432號1樓", "3.0")
        val item5 = StoreInformation(R.drawable.cupcakes, "嘶嘶嘶", "北極星天天里5555號2樓", "1.0")
        val item6 = StoreInformation(R.drawable.macarons, "天已", "台灣省南投區自投羅網路522號1樓", "5.0")
        dataList1.add(item1)
        dataList1.add(item2)
        dataList1.add(item3)
        dataList1.add(item4)
        dataList1.add(item5)
        dataList1.add(item6)

        adapter.submitList(dataList1)

        return binding.root
    }

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


//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray,
//    ) {
//        Log.v("b", "$requestCode $MY_PERMISSIONS_REQUEST_LOCATION")
//
//        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
//            Log.v("a", "$requestCode $MY_PERMISSIONS_REQUEST_LOCATION")
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(activity as Activity, "已成功定位功能", Toast.LENGTH_SHORT).show()
//                Log.v("已成功定位功能", "已成功定位功能")
//
//
//            } else {
//                Toast.makeText(activity as Activity, "需要定位功能", Toast.LENGTH_SHORT).show()
//                Log.v("需要定位功能", "需要定位功能")
//
//
//
//            }
//        }
//
//    }
    @SuppressLint("MissingPermission")

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
    mMap.isMyLocationEnabled = true


        //確認定位權限是否開啟
        if (ContextCompat.checkSelfPermission(this.requireActivity().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // ActivityCompat.requestPermissions(activity as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION)
        } else {
            Log.v("成功", "已定位")

        }

    }

    //---------------------------------------------------------------------------------------------
    private fun initPlaces() {
        Places.initialize(requireActivity().getApplicationContext(), BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(activity as Activity)
        autocompleteSessionToken = AutocompleteSessionToken.newInstance()
    }

    private fun getSuggestions(query: String) {
        var findAutocompletePredictionsRequest = FindAutocompletePredictionsRequest
            .builder()
            .setSessionToken(autocompleteSessionToken)
            .setTypeFilter(TypeFilter.ESTABLISHMENT)
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

                        createList()
                    }
                })


    }

    private fun createList() {
        var suggestionsStringArray = Array<String>(predictionList!!.size) { "" }
        if (predictionList!!.isNotEmpty()) {
            for (i in predictionList!!.indices) {
                suggestionsStringArray[i] = predictionList!![i].getFullText(null).toString()
                Log.v("suggestionsStringArray[]", "${suggestionsStringArray[i]}")

            }


            //  populateAdapter(suggestionsStringArray)
        }
    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
//            when (resultCode) {
//                Activity.RESULT_OK -> {
//                    data?.let {
//                        val place = Autocomplete.getPlaceFromIntent(data)
//                        Log.i(TAG, "Place: ${place.name}, ${place.id}")
//                    }
//                }
//                AutocompleteActivity.RESULT_ERROR -> {
//                    // TODO: Handle the error.
//                    data?.let {
//                        val status = Autocomplete.getStatusFromIntent(data)
//                        Log.i(TAG, status.statusMessage ?: "")
//                    }
//                }
//                Activity.RESULT_CANCELED -> {
//                    // The user canceled the operation.
//                }
//            }
//            return
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }

}

