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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.libraries.places.api.Places
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.Place
import com.jessy.foodmap.databinding.FragmentAddItineraryDetailDateBinding
import com.jessy.foodmap.itinerary.ItemTouchHelperCallback
import com.jessy.foodmap.itinerary.paging.MyItineraryPagingAdapter
import com.utsman.samplegooglemapsdirection.kotlin.model.DirectionResponses
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


class AddItineraryDetailDateFragment(position: Int, journey: Journey) : Fragment() {
    var fromFKIP :String=""
    var toMonas :String=""
    val index = position
    val journeyArg = journey
    val db = Firebase.firestore
    val AddItineraryDtailDateViewModel = AddItineraryDtailDateViewModel(index, journeyArg)
    val adapter = AddItineraryDtailDateAdapter(AddItineraryDtailDateAdapter.OnClickListener {
    },AddItineraryDtailDateViewModel)



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentAddItineraryDetailDateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewMedel = AddItineraryDtailDateViewModel

        AddItineraryDtailDateViewModel.getPlaces()
        binding.detailRecyclerViewDate.adapter = adapter
        val callback = ItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.detailRecyclerViewDate)

        AddItineraryDtailDateViewModel.placeLiveData.observe(viewLifecycleOwner) {


            CalculateTravelTime()
           adapter.submitList(AddItineraryDtailDateViewModel.places)

        }

        return binding.root

    }

    private fun CalculateTravelTime(){
        for( i in 0 until AddItineraryDtailDateViewModel.places.size-1){
            fromFKIP = AddItineraryDtailDateViewModel.places[i].latitude.toString() + ","+AddItineraryDtailDateViewModel.places[i].longitude.toString()
            toMonas = AddItineraryDtailDateViewModel.places[i+1].latitude.toString() + ","+ AddItineraryDtailDateViewModel.places[i+1].longitude.toString()

            val mode = when(AddItineraryDtailDateViewModel.places[i+1].transportation){
                1->"walking"
                2->"driving"
                3->"bicycling"
                4->"transit"
                else -> {
                    Log.v("error","選擇錯誤")
                }
            }
            val info = (activity as MainActivity).applicationContext.packageManager
                .getApplicationInfo(
                    (activity as MainActivity).packageName,
                    PackageManager.GET_META_DATA
                )
            val key = info.metaData[resources.getString(R.string.map_api_key_name)].toString()
            Places.initialize(requireContext(), key)

            val apiServices = RetrofitClient.apiServices(this)
            apiServices.getDirection(mode as String, fromFKIP, toMonas, key)
                .enqueue(object : Callback<DirectionResponses> {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onResponse(
                        call: Call<DirectionResponses>,
                        response: Response<DirectionResponses>,
                    ) {
                        val legs = response.body()!!.routes!![0]!!.legs!!
                        var totalDuration: Long = 0
//
                        if(response.body()!!.routes?.size != 0){
                            for (leg in legs) {
                                totalDuration += (leg?.duration?.value ?: 0)
                            }
                        }
                        else{
                            totalDuration = legs.get(0)!!.duration!!.value!!.toLong()
                        }

                        AddItineraryDtailDateViewModel.places[i].trafficTime = totalDuration*1000
                        AddItineraryDtailDateViewModel.places[i+1].startTime = 28800000 + totalDuration*1000 + AddItineraryDtailDateViewModel.places[i].dwellTime!!



                        db.collection("journeys").document(AddItineraryDtailDateViewModel.journeyItemId)
                            .collection("places").document(AddItineraryDtailDateViewModel.places[i].id)
                            .update("trafficTime",AddItineraryDtailDateViewModel.places[i].trafficTime)
                            .addOnSuccessListener {
                                Log.d(ContentValues.TAG, "success")
                                Log.v("trafficTime","${AddItineraryDtailDateViewModel.places[i].trafficTime}")

                            }
                            .addOnFailureListener {
                                Log.w(ContentValues.TAG, "Error adding document")
                            }

                        db.collection("journeys").document(AddItineraryDtailDateViewModel.journeyItemId)
                            .collection("places").document(AddItineraryDtailDateViewModel.places[i].id)
                            .update("startTime",AddItineraryDtailDateViewModel.places[i].startTime)
                            .addOnSuccessListener {
                                Log.d(ContentValues.TAG, "success")
                                Log.v("trafficTime","${AddItineraryDtailDateViewModel.places[i].trafficTime}")

                            }
                            .addOnFailureListener {
                                Log.w(ContentValues.TAG, "Error adding document")
                            }

                        adapter.submitList(AddItineraryDtailDateViewModel.places)
                        adapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<DirectionResponses>, t: Throwable) {
                        Log.e("error", t.localizedMessage)
                    }

                })
        }
        AddItineraryDtailDateViewModel.places[0].startTime = 28800000
        adapter.submitList(AddItineraryDtailDateViewModel.places)

    }
    }

    private interface ApiServices {
        @GET("maps/api/directions/json")
        fun getDirection(
            @Query("mode") mode: String,
            @retrofit2.http.Query("origin") origin: String,
            @retrofit2.http.Query("destination") destination: String,
            @retrofit2.http.Query("key") apiKey: String,
//            @Query("awypoints")awypoints:Array
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


