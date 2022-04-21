package com.jessy.foodmap.itinerary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.jessy.foodmap.R
import com.jessy.foodmap.itinerary.detailpaging.ItineraryDetailPagingAdapter
import com.jessy.foodmap.databinding.FragmentItineraryDetailBinding
import com.jessy.foodmap.detail.DetailFragmentArgs

class ItineraryDetailFragment : BottomSheetDialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentItineraryDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        val journey = ItineraryDetailFragmentArgs.fromBundle(requireArguments()).journeyKey
        Log.v("journey","$journey")


        val pageAdapter = ItineraryDetailPagingAdapter(requireActivity().supportFragmentManager, lifecycle)
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