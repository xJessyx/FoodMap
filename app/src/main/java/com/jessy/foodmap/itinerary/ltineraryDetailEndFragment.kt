package com.jessy.foodmap.itinerary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentLtineraryDetailEndBinding
import com.jessy.foodmap.databinding.FragmentMyItineraryPagingBinding


class ltineraryDetailEndFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val journeyArg = ltineraryDetailEndFragmentArgs.fromBundle(requireArguments()).journeyKey

        val binding = FragmentLtineraryDetailEndBinding.inflate(inflater, container, false)
        return binding.root
    }
}