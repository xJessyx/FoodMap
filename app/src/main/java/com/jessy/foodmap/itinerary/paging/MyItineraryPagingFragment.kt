package com.jessy.foodmap.itinerary.paging

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
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
        binding.myitineraryRecyclerView.adapter = RecommendPagingAdapter(RecommendPagingAdapter.OnClickListener{
            viewModel.navigateToDetailDate(it)
        }
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.getFireBaseJourney()
        viewModel.getAllJourneyLiveData.observe(viewLifecycleOwner){

            (binding.myitineraryRecyclerView.adapter as RecommendPagingAdapter).submitList(viewModel.getAllJourney)
        }

        viewModel.navigateToDetailDate.observe(
            viewLifecycleOwner,
            Observer{
                it?.let {
                    findNavController().navigate(NavigationDirections.recommendPagingFragmentItineraryDetailFragment(it))
                    Log.v("it","#${it}")
                    Log.v("it.id","#${it.id}")

                    viewModel.onDetailNavigated()
                }
            })



        return binding.root

    }

}