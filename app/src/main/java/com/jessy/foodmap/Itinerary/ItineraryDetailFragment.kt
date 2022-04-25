package com.jessy.foodmap.Itinerary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.jessy.foodmap.Itinerary.detailpaging.ItinerayDetailPagingAdapter
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentAddItineraryBinding
import com.jessy.foodmap.databinding.FragmentItineraryDetailBinding
import com.jessy.foodmap.member.MemberPagingAdapter

class ItineraryDetailFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentItineraryDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this


        val pageAdapter = ItinerayDetailPagingAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.itineraryDeatailViewPager2.adapter = pageAdapter
        TabLayoutMediator(binding.itineraryDeatailTabs,
            binding.itineraryDeatailViewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "第一天"
                }
                1 -> {
                    tab.text = "第二天"
                }
                2 -> {
                    tab.text = "第三天"
                }
                3 -> {
                    tab.text = "第四天"
                }

            }
        }.attach()
        return binding.root
    }

}