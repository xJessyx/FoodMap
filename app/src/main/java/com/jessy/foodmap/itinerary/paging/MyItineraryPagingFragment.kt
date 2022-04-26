package com.jessy.foodmap.itinerary.paging

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.databinding.FragmentMyItineraryPagingBinding
import com.jessy.foodmap.itinerary.ItineraryDetailViewModel

class MyItineraryPagingFragment : Fragment() {

    private val viewModel: MyItineraryPagingViewModel by lazy {
        ViewModelProvider(this).get(MyItineraryPagingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentMyItineraryPagingBinding.inflate(inflater, container, false)
        val adapter = RecommendPagingAdapter(RecommendPagingAdapter.OnClickListener{
            findNavController().navigate(NavigationDirections.navigateToItineraryDetailFragment(it))
        })
        binding.myitineraryRecyclerView.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.getFireBaseJourney()
        viewModel.getAllJourneyLiveData.observe(viewLifecycleOwner){

            adapter.submitList(viewModel.getAllJourney)
        }



        binding.myitineraryButton.setOnClickListener {
            findNavController().navigate(NavigationDirections.actionMyItineraryPagingFragmentToAddItineraryFragment())
        }

        return binding.root

    }

}