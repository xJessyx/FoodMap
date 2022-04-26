package com.jessy.foodmap.itinerary.paging

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.databinding.FragmentRecommendPagingBinding


class RecommendPagingFragment : Fragment() {


    private val viewModel: RecommendPagingViewModel by lazy {
        ViewModelProvider(this).get(RecommendPagingViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentRecommendPagingBinding.inflate(inflater, container, false)
        val adapter = RecommendPagingAdapter(RecommendPagingAdapter.OnClickListener{
            findNavController().navigate(NavigationDirections.navigateToItineraryDetailFragment(it))

        })
        binding.recommendRecyclerView.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        viewModel.getFireBaseJourney()
        viewModel.getAllJourneyLiveData.observe(viewLifecycleOwner){

            adapter.submitList(viewModel.getAllJourney)
        }


        return binding.root

    }

}