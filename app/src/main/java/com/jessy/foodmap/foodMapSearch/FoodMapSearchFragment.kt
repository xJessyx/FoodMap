package com.jessy.foodmap.foodMapSearch

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
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
import com.google.android.libraries.places.api.net.*
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.jessy.foodmap.BuildConfig
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.R
import com.jessy.foodmap.data.StoreInformation
import com.jessy.foodmap.databinding.FragmentFoodMapSearchBinding


class FoodMapSearchFragment : Fragment(), OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {

    private val viewModel: FoodMapSearchViewModel by lazy {
        ViewModelProvider(this).get(FoodMapSearchViewModel::class.java)
    }
    val dataList = mutableListOf<StoreInformation>()
    val MY_PERMISSIONS_REQUEST_LOCATION = 100
    private lateinit var mMap: GoogleMap
    private var locationManager: LocationManager? = null
    private lateinit var placesClient: PlacesClient
    lateinit var autocompleteSessionToken: AutocompleteSessionToken
    var predictionList = mutableListOf<AutocompletePrediction>()
    val adapter = FoodMapSearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        (activity as MainActivity).hideToolBar()

        val binding = FragmentFoodMapSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.searchRecyclerView.adapter = adapter
        binding.viewModel = viewModel

        initPlaces()

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
        //   Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.search_autocomplete)
                    as AutocompleteSupportFragment

//        // Specify the types of place data to return.
        // autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT)

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS,
            Place.Field.PHOTO_METADATAS,
            Place.Field.RATING,
            Place.Field.LAT_LNG))
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                getSuggestions(place.name)
                mMap.addMarker(MarkerOptions().position(place.latLng))
            }
            override fun onError(status: Status) {
                Log.i(TAG, "An error occurred: $status")
            }
        })

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

    //---------------------------------------------------------------------------------------------

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        mMap.isMyLocationEnabled = true

    }
    private fun initPlaces() {
        Places.initialize(requireActivity().getApplicationContext(), BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(activity as Activity)
        autocompleteSessionToken = AutocompleteSessionToken.newInstance()
    }

    //抓取搜尋的預測相關列表

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
                        Log.d("PlaceTest", "getSuggestions: -----------------------SUCCESS")
                        Log.d("PlaceTest", "${it.result!!.autocompletePredictions}")
                        Log.d("PlaceTest", "size=${it.result!!.autocompletePredictions.size}")
                        predictionList =
                            it.result!!.autocompletePredictions
                        createList()

                    }
                })
    }

    //剖析預測相關列表，並加入adapter
    private fun createList() {

        var totalCount = 0
        Log.i(TAG, "Place found: predictionList.size=${predictionList.size}")
        for (predictionItem in predictionList) {
            // Specify the fields to return.
            val placeFields = listOf(Place.Field.PHOTO_METADATAS,
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.RATING)

            // Construct a request object, passing the place ID and fields array.
            val request = FetchPlaceRequest.newInstance(predictionItem.placeId, placeFields)
            placesClient.fetchPlace(request)
                .addOnSuccessListener { response: FetchPlaceResponse ->
                    val metada = response.place.photoMetadatas
                    if (metada == null || metada.isEmpty()) {
                        Log.w(TAG, "No photo metadata.")
                        return@addOnSuccessListener
                    }
                    val photoMetadata = metada.first()

                    // Create a FetchPhotoRequest.
                    val photoRequest = FetchPhotoRequest.builder(photoMetadata)
                        .setMaxWidth(400) // Optional.
                        .setMaxHeight(200) // Optional.
                        .build()
                    placesClient.fetchPhoto(photoRequest)
                        .addOnSuccessListener { fetchPhotoResponse: FetchPhotoResponse ->
                            val bitmap = fetchPhotoResponse.bitmap

                            val place = StoreInformation(bitmap,
                                response.place.name,
                                response.place.address,
                                response.place.rating.toString())
                                dataList.add(place)
                                adapter.submitList(dataList)
                        Log.v("dataList","dataList additem= $dataList")

                        }.addOnFailureListener { exception: Exception ->
                            if (exception is ApiException) {
                                Log.e(TAG, "Place not found: " + exception.message)
                                val statusCode = exception.statusCode
                            }
                        }
                    totalCount += 1
                    if (totalCount == predictionList.size) {
                    } else {
                    }
                }.addOnFailureListener { exception: Exception ->
                    totalCount += 1
                    if (exception is ApiException) {
                        Log.e(TAG, "Place not found: ${exception.message}")
                        val statusCode = exception.statusCode
                        TODO("Handle error with given status code")
                    }
                }

            Log.v("dataList","createList內結束前1= $dataList")

        }
        Log.v("dataList","createList內結束後2 = $dataList")

    }

}




