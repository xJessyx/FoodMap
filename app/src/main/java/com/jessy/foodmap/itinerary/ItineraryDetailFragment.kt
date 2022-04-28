package com.jessy.foodmap.itinerary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.itinerary.detailpaging.ItineraryDetailPagingAdapter
import com.jessy.foodmap.databinding.FragmentItineraryDetailBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class ItineraryDetailFragment : BottomSheetDialogFragment() {
    private val viewModel: ItineraryDetailViewModel by lazy {
        ViewModelProvider(this).get(ItineraryDetailViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentItineraryDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        val journeyArg = ItineraryDetailFragmentArgs.fromBundle(requireArguments()).journeyKey

        binding.viewModel = viewModel
        binding.itineraryDeatailName.text = journeyArg.name
        binding.itineraryDeatailStartDate.text =journeyArg.startDate
        binding.itineraryDeatailEndDate.text=journeyArg.endDate
//        viewModel.journeyItemId = journeyArg.id

       // viewModel.getFireBasePlace()

//        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//        val mStart = LocalDate.parse(binding.itineraryDeatailStartDate.text, format)
//        val mEnd = LocalDate.parse(binding.itineraryDeatailEndDate.text, format)
//        val difference = ChronoUnit.DAYS.between(mStart, mEnd)
        val pageAdapter = ItineraryDetailPagingAdapter(this,journeyArg)

        binding.itineraryDeatailViewPager2.adapter = pageAdapter
        TabLayoutMediator(binding.itineraryDeatailTabs,
            binding.itineraryDeatailViewPager2) { tab, position ->

            tab.text = "第 ${position + 1} 天"
        }.attach()

        binding.itineraryDetailFabBtn.setOnClickListener {
                view ->
            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
            findNavController().navigate(NavigationDirections.addItineraryDetailDateFragmentFoodMapSearchFragment())
        }
        return binding.root
    }

}