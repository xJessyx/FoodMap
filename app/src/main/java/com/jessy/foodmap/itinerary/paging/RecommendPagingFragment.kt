package com.jessy.foodmap.itinerary.paging

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jessy.foodmap.NavigationDirections
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
        binding.recommendRecyclerView.adapter =
            RecommendPagingAdapter(RecommendPagingAdapter.OnClickListener {
                viewModel.navigateToDetailDate(it)
            }
            )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.getFireBaseJourney()
        viewModel.getAllJourneyLiveData.observe(viewLifecycleOwner) {

            (binding.recommendRecyclerView.adapter as RecommendPagingAdapter).submitList(it)

        }

        viewModel.navigateToDetailDate.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(NavigationDirections.myItineraryPagingFragmentItineraryDetailFragment(
                        it))
                    viewModel.onDetailNavigated()
                }
            })
        return binding.root

    }

}