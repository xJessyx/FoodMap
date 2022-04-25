package com.jessy.foodmap.Itinerary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.jessy.foodmap.Itinerary.paging.ItinerayPagingAdapter
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.databinding.FragmentItineraryPlanningBinding


class ItineraryPlanningFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        (activity as MainActivity).hideToolBar()

        val binding = FragmentItineraryPlanningBinding.inflate(inflater, container, false)

        val pageAdapter = ItinerayPagingAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.itineraryPlanningViewPager2.adapter = pageAdapter

        TabLayoutMediator(binding.itineraryPlanningTabs,
            binding.itineraryPlanningViewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "我的行程"
                }
                1 -> {
                    tab.text = "達人推薦"
                }

            }
        }.attach()
        return binding.root

    }

}