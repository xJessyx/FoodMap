package com.jessy.foodmap.foodMapSearch

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.*
import com.google.android.libraries.places.api.net.*
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.R
import com.jessy.foodmap.data.StoreInformation
import com.jessy.foodmap.databinding.FragmentFoodMapSearchBinding
import com.jessy.foodmap.foodMapSearch.FoodMapSearchFragment.ImgUtil.getBitmapDescriptor


class FoodMapSearchFragment : Fragment(), OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {

    private val viewModel: FoodMapSearchViewModel by lazy {
        ViewModelProvider(this)[FoodMapSearchViewModel::class.java]
    }

    val MY_PERMISSIONS_REQUEST_LOCATION = 100
    private lateinit var mMap: GoogleMap
    private var locationManager: LocationManager? = null
    private lateinit var placesClient: PlacesClient
    lateinit var autocompleteSessionToken: AutocompleteSessionToken
    var predictionList = mutableListOf<AutocompletePrediction>()

    var oriLocation: Location? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        (activity as MainActivity).hideToolBar()

        val binding = FragmentFoodMapSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        var placeSelectDataArgs =
            FoodMapSearchFragmentArgs.fromBundle(requireArguments()).placeSelectDataKey
        val adapter =

            FoodMapSearchAdapter(FoodMapSearchAdapter.OnClickListener {

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude!!,
                    it.longitude!!), 12.0f))

            }, placeSelectDataArgs)

        binding.searchRecyclerView.adapter = adapter
        binding.viewModel = viewModel

        //   Initialize
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.search_autocomplete)
                    as AutocompleteSupportFragment

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.search_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initPlaces()
        checkGPS()
        locationManager()

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS,
            Place.Field.PHOTO_METADATAS,
            Place.Field.RATING,
            Place.Field.LAT_LNG)).setCountry("TW")

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {

                getSuggestions(place.name)
            }

            override fun onError(status: Status) {
                Log.i(TAG, "An error occurred: $status")
            }
        })

        viewModel.getSuggestionsList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "?????????????????????", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(activity, "??????????????????", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //?????????????????????
    private fun checkGPS() {
        //??????????????????????????????
        if (ContextCompat.checkSelfPermission(this.requireActivity().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            //????????????

            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION)

        } else {
            Log.v("??????", "?????????")

        }
    }

    //???????????????????????????gps
    @SuppressLint("MissingPermission")
    private fun locationManager() {

        // Create persistent LocationManager reference
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        Log.v("locationManager", "$locationManager")

        var isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        var isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!(isGPSEnabled || isNetworkEnabled))
            Toast.makeText(activity, "?????????????????????????????????", Toast.LENGTH_SHORT).show()
        else
            try {
                if (isGPSEnabled) {
                    locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        0L, 0f, locationListener)
                    oriLocation =
                        locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    Toast.makeText(activity, "??????????????? GPS ????????????", Toast.LENGTH_SHORT).show()
                    // mMap.isMyLocationEnabled = true

                } else if (isNetworkEnabled) {
                    locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        0L, 0f, locationListener)
                    oriLocation =
                        locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    Toast.makeText(activity, "?????????????????????????????????", Toast.LENGTH_SHORT).show()
                    // mMap.isMyLocationEnabled = true

                }
            } catch (ex: SecurityException) {
                Log.d("myTag", "Security Exception, no location available")
            }

    }

    //define the myLocationListener
    private val locationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {

//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude,
//                location.longitude), 12.0f))
            //   Log.v("??????", "${location.latitude} - ${location.longitude}")

        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true

    }

    // Initialize the SDK
    private fun initPlaces() {
        val info = (activity as MainActivity).applicationContext.packageManager
            .getApplicationInfo(
                (activity as MainActivity).packageName,
                PackageManager.GET_META_DATA
            )

        val key = info.metaData[resources.getString(R.string.map_api_key_name)].toString()
        Places.initialize(requireContext(), key)
        placesClient = Places.createClient(activity as Activity)
        autocompleteSessionToken = AutocompleteSessionToken.newInstance()
    }

    //?????????????????????????????????
    private fun getSuggestions(query: String) {
        var findAutocompletePredictionsRequest = FindAutocompletePredictionsRequest
            .builder()
            .setCountries("TW")
            .setTypeFilter(TypeFilter.ESTABLISHMENT)
            .setSessionToken(autocompleteSessionToken)
            .setQuery(query)
            .build()

        placesClient.findAutocompletePredictions(findAutocompletePredictionsRequest)
            .addOnCompleteListener(
                OnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("PlaceTest", "getSuggestions: -----------------------SUCCESS")
                        Log.d("PlaceTest", "${it.result!!.autocompletePredictions.get(0)}")
                        Log.d("PlaceTest", "size=${it.result!!.autocompletePredictions.size}")
                        predictionList =
                            it.result!!.autocompletePredictions
                        createList()
                    }
                })
    }

    //????????????????????????????????????adapter
    private fun createList() {
        val dataList = mutableListOf<StoreInformation>()

        dataList.clear()
        mMap.clear()
        var totalCount = 0
        Log.i(TAG, "Place found: predictionList.size=${predictionList.size}")
        for (predictionItem in predictionList) {
            // Specify the fields to return.
            val placeFields = listOf(Place.Field.PHOTO_METADATAS,
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.RATING,
                Place.Field.LAT_LNG)

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
                                response.place.rating.toString(),
                                response.place.latLng.latitude, response.place.latLng.longitude)
                            dataList.add(place)
                            mMap.addMarker(MarkerOptions()
                                .position(response.place.latLng)
                                .icon(
                                    context?.let {
                                        getBitmapDescriptor(
                                            it,
                                            R.drawable.marker2,
                                            150,
                                            150
                                        )
                                    }
                                )
                                .title(response.place.name)
                            )
                            viewModel.getSuggestionsList.value = dataList

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
                    }
                }
        }

    }

    //????????????????????? bitmap ???????????????????????? Int ?????????????????? dp ??? px ?????????
    object ImgUtil {
        fun getBitmapDescriptor(
            context: Context,
            id: Int,
            width: Int = 0,
            height: Int = 0,
        ): BitmapDescriptor? {
            val vectorDrawable: Drawable? =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    context.getDrawable(id)
                } else {
                    ContextCompat.getDrawable(context, id)
                }
            return if (vectorDrawable != null) {
                if (width == 0) vectorDrawable.intrinsicWidth
                if (height == 0) vectorDrawable.intrinsicHeight
                vectorDrawable.setBounds(0, 0, width, height)
                val bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                val canvas = Canvas(bm);
                vectorDrawable.draw(canvas);
                BitmapDescriptorFactory.fromBitmap(bm);
            } else {
                null
            }
        }
    }
}

