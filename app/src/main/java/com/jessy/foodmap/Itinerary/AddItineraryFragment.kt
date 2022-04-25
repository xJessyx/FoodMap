package com.jessy.foodmap.Itinerary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentAddItineraryBinding


class AddItineraryFragment : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentAddItineraryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this


        binding.addItineraryBt.setOnClickListener {
            findNavController().navigate(NavigationDirections.addItineraryFragmentToItineraryDetailFragment())
        }
        return binding.root
    }

}