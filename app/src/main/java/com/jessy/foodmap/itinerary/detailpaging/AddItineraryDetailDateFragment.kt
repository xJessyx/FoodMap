package com.jessy.foodmap.itinerary.detailpaging

import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.places.api.Places
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.R
import com.jessy.foodmap.data.DirectionResponses
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.databinding.FragmentAddItineraryDetailDateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class AddItineraryDetailDateFragment(position: Int, journey: Journey) : Fragment() {
    val index = position
    val journeyArg = journey

    private val viewModel: AddItineraryDtailDateViewModel by lazy {
        ViewModelProvider(this).get(AddItineraryDtailDateViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        getApiResult()
        val binding = FragmentAddItineraryDetailDateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val AddItineraryDtailDateViewModel = AddItineraryDtailDateViewModel(index,journeyArg)
        binding.viewMedel = AddItineraryDtailDateViewModel

        AddItineraryDtailDateViewModel.getPlaces()

            val adapter = AddItineraryDtailDateAdapter(AddItineraryDtailDateAdapter.OnClickListener {
            })

            binding.detailRecyclerViewDate.adapter = adapter

        AddItineraryDtailDateViewModel.placeLiveData.observe(viewLifecycleOwner, {
            Log.v("viewModel.places","${AddItineraryDtailDateViewModel.places}")

            adapter.submitList(AddItineraryDtailDateViewModel.places)
        })


            //adapter.submitList(viewModel.dataList1)

        return binding.root

    }


    fun getApiResult(){
        val fromFKIP = "39.65393880000001" + "," + "-104.9805056"
        val toMonas = "27.7150462" + "," + "85.2342752"

        val info = (activity as MainActivity).applicationContext.packageManager
            .getApplicationInfo(
                (activity as MainActivity).packageName,
                PackageManager.GET_META_DATA
            )

        val key = info.metaData[resources.getString(R.string.map_api_key_name)].toString()
        Places.initialize(requireContext(), key)

        val apiServices = RetrofitClient.apiServices(this)
        apiServices.getDirection(fromFKIP, toMonas,key)
            .enqueue(object : Callback<DirectionResponses> {
                override fun onResponse(call: Call<DirectionResponses>, response: Response<DirectionResponses>) {
                    //drawPolyline(response)
                    Log.d("bisa dong oke", response.message())
                    Log.d("response", "$response")

                }

                override fun onFailure(call: Call<DirectionResponses>, t: Throwable) {
                    Log.e("anjir error", t.localizedMessage)
                }
            })
    }




    private interface ApiServices {
        @GET("maps/api/directions/json")
        fun getDirection(@retrofit2.http.Query("origin") origin: String,
                         @retrofit2.http.Query("destination") destination: String,
                         @retrofit2.http.Query("key") apiKey: String): Call<DirectionResponses>
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

}