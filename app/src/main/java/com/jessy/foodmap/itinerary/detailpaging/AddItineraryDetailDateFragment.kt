package com.jessy.foodmap.itinerary.detailpaging

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.databinding.FragmentAddItineraryDetailDateBinding


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

}