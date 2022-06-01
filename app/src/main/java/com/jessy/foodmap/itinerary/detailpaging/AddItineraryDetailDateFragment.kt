package com.jessy.foodmap.itinerary.detailpaging

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.libraries.places.api.Places
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.Place
import com.jessy.foodmap.data.PlaceSelectData
import com.jessy.foodmap.data.StoreInformation
import com.jessy.foodmap.databinding.FragmentAddItineraryDetailDateBinding
import com.jessy.foodmap.itinerary.ItemTouchHelperCallback
import com.jessy.foodmap.login.UserManager
import com.utsman.samplegooglemapsdirection.kotlin.model.DirectionResponses
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class AddItineraryDetailDateFragment(position: Int, journey: Journey) : Fragment() {
    var fromFKIP: String = ""
    var toMonas: String = ""
    val index = position
    val journeyArg = journey
    val db = Firebase.firestore
    val viewModel = AddItineraryDtailDateViewModel(index, journeyArg)
    val adapter = AddItineraryDtailDateAdapter(AddItineraryDtailDateAdapter.OnClickListener {
    }, viewModel)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentAddItineraryDetailDateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewMedel = viewModel
        viewModel.getPlaces()
        binding.detailRecyclerViewDate.adapter = adapter
        val callback = ItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.detailRecyclerViewDate)

        viewModel.places.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isEmpty()) {
                    Log.v("Place 尚未有地點", "$it")
                } else {
                    calculateTravelTime(it)
                    Log.v("Place 有地點", "$it")
                }
            }

        }

        val today = LocalDate.now()
        val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val parseStartDate = LocalDate.parse(journeyArg.startDate, fmt)
        val parseEndDate = LocalDate.parse(journeyArg.endDate, fmt)


        if (journeyArg.userId == UserManager.user?.id && !journeyArg.share) {

            if (today.isBefore(parseStartDate)) {
                binding.itineraryDetailFabBtn.visibility = View.VISIBLE
                Log.v("today< start", "$today <  $parseStartDate")

            } else if (today.isAfter(parseEndDate)) {
                binding.itineraryDetailFabBtn.visibility = View.GONE

                Log.v("today > end", "$today >  $parseEndDate")

            } else {
                binding.itineraryDetailFabBtn.visibility = View.VISIBLE
                Log.v("start <today< end", " $parseStartDate < $today <  $parseEndDate ")
            }
        }

        binding.itineraryDetailFabBtn.setOnClickListener {


            val selectItem = PlaceSelectData(StoreInformation(),
                Place("", "", index + 1, 0, 0, 0, 0, null, null),
                Journey(journeyArg.id,
                    journeyArg.image,
                    journeyArg.name,
                    journeyArg.startDate,
                    journeyArg.endDate,
                    journeyArg.totalDay,
                    journeyArg.status,
                    journeyArg.userId,
                    journeyArg.share))

            findNavController().navigate(NavigationDirections.addItineraryDetailDateFragmentFoodMapSearchFragment(
                selectItem))
        }

        return binding.root

    }

    private fun calculateTravelTime(places: List<Place>) {

        places[0].startTime = 28800000

        if (places.size == 1) {
            onTravelTimeCalculated(places)
        }

        var endCount = places.size - 1

        for (i in places.indices) {
            if (i < places.size - 1) {
                fromFKIP = places[i].latitude.toString() + "," + places[i].longitude.toString()
                toMonas =
                    places[i + 1].latitude.toString() + "," + places[i + 1].longitude.toString()

                val mode = when (places[i + 1].transportation) {
                    1 -> "walking"
                    2 -> "driving"
                    3 -> "bicycling"
                    4 -> "transit"
                    else -> {
                        Log.v("error", "選擇錯誤")
                    }
                }
                val info = (activity as MainActivity).applicationContext.packageManager
                    .getApplicationInfo(
                        (activity as MainActivity).packageName,
                        PackageManager.GET_META_DATA
                    )
                val key = info.metaData[resources.getString(R.string.map_api_key_name)].toString()
                Places.initialize(requireContext(), key)
                Log.v("key", "$key")

                val apiServices = RetrofitClient.apiServices(this)
                apiServices.getDirection(mode as String, fromFKIP, toMonas, key)
                    .enqueue(object : Callback<DirectionResponses> {
                        @SuppressLint("NotifyDataSetChanged")
                        override fun onResponse(
                            call: Call<DirectionResponses>,
                            response: Response<DirectionResponses>,
                        ) {

                            val legs = response.body()?.routes?.get(0)?.legs
                            Log.v("legs", "$legs")
                            var totalDuration: Long = 0
                            if (response.body()!!.routes?.size != 0) {
                                if (legs != null) {
                                    for (leg in legs) {
                                        Log.v("legs for", "$legs")

                                        totalDuration += (leg?.duration?.value ?: 0)
                                    }
                                }
                            } else {
                                totalDuration = (legs?.get(0)?.duration?.value ?: 0) as Long

                                Log.v("legs else", "$legs")

                            }

                            places[i].trafficTime = totalDuration * 1000
                            places[i + 1].startTime =
                                28800000 + totalDuration * 1000 + places[i].dwellTime!!

                            updatePlaceTimes(
                                placeId = places[i].id,
                                startTime = places[i].startTime ?: 1,
                                trafficTime = places[i].trafficTime ?: 0
                            )

                            endCount--
                            if (endCount == 0) {
                                onTravelTimeCalculated(places)
                            }

                        }

                        override fun onFailure(call: Call<DirectionResponses>, t: Throwable) {
                            Log.e("DirectionResponses error", t.localizedMessage)
                            endCount--
                            if (endCount == 0) {
                                onTravelTimeCalculated(places)
                            }
                        }

                    })
            }


        }

    }

    fun onTravelTimeCalculated(places: List<Place>) {
        updatePlaceTimes(
            placeId = places.last().id,
            startTime = places.last().startTime ?: 1,
            trafficTime = places.last().trafficTime ?: 0
        )
        adapter.submitList(places)
        adapter.notifyDataSetChanged()
    }

    fun updatePlaceTimes(placeId: String, startTime: Long, trafficTime: Long) {

        db.collection("journeys").document(viewModel.journeyItemId)
            .collection("places").document(placeId)
            .update("trafficTime", trafficTime)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "success")
                Log.v("trafficTime", "${trafficTime}")
            }
            .addOnFailureListener {
                Log.w(ContentValues.TAG, "Error adding document")
            }

        db.collection("journeys").document(viewModel.journeyItemId)
            .collection("places").document(placeId)
            .update("startTime", startTime)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "success")
                Log.v("startTime", "${startTime}")

            }
            .addOnFailureListener {
                Log.w(ContentValues.TAG, "Error adding document")
            }
    }
}

private interface ApiServices {
    @GET("maps/api/directions/json")
    fun getDirection(
        @Query("mode") mode: String,
        @retrofit2.http.Query("origin") origin: String,
        @retrofit2.http.Query("destination") destination: String,
        @retrofit2.http.Query("key") apiKey: String,
    ): Call<DirectionResponses>
}


private object RetrofitClient {
    fun apiServices(context: AddItineraryDetailDateFragment): ApiServices {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(context.resources.getString(R.string.base_url))
            .build()

        return retrofit.create<ApiServices>(ApiServices::class.java)
    }
}


