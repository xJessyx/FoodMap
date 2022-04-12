package com.jessy.foodmap.foodMapSearch
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentFoodMapSearchBinding
class FoodMapSearchFragment : Fragment(), OnMapReadyCallback {

    val MY_PERMISSIONS_REQUEST_LOCATION = 100
    private lateinit var mMap: GoogleMap
    private var locationManager : LocationManager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentFoodMapSearchBinding.inflate(inflater, container, false)
       // binding.lifecycleOwner = viewLifecycleOwner

            if (ContextCompat.checkSelfPermission(activity as Activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
            } else {
                Log.v("成功","123")
            }
       return binding.root
    }


    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        val mapFragment = SupportMapFragment.newInstance()
//        activity?.supportFragmentManager
//            ?.beginTransaction()
//            ?.add(R.id.my_container, mapFragment)
//            ?.commit()




        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        Log.v("mapFragment","$mapFragment")

        mapFragment.getMapAsync(this)

        // Create persistent LocationManager reference
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        Log.v("locationManager","$locationManager")


        try {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available")
        }
    }

    // define the listener
    private val locationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            Log.v("location","$location")
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 12.0f))
            Log.v("座標","${location.latitude} - ${location.longitude}")

        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            Log.v("a","$requestCode $MY_PERMISSIONS_REQUEST_LOCATION")
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(activity as Activity, "已成功定位功能", Toast.LENGTH_SHORT).show()
                Log.v("已成功定位功能","已成功定位功能")

            } else {
                Toast.makeText(activity as Activity, "需要定位功能", Toast.LENGTH_SHORT).show()
                Log.v("需要定位功能","需要定位功能")

            }
        }

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        Log.v("onMapReady","$googleMap")
        mMap = googleMap
        Log.v("onMapReady2","$googleMap")

        mMap.setMyLocationEnabled(true)
        Log.v("onMapReady3","$googleMap")


    }
}