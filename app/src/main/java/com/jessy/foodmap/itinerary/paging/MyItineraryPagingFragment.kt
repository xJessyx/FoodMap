package com.jessy.foodmap.itinerary.paging

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.databinding.FragmentMyItineraryPagingBinding
import com.jessy.foodmap.itinerary.ITHelperInterface
import com.jessy.foodmap.itinerary.ItemTouchHelperCallback
import com.jessy.foodmap.itinerary.paging.MyItineraryPagingAdapter as MyItineraryPagingAdapter1

class MyItineraryPagingFragment : Fragment(){

    private val viewModel: MyItineraryPagingViewModel by lazy {
        ViewModelProvider(this).get(MyItineraryPagingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentMyItineraryPagingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.myitineraryRecyclerView.adapter = MyItineraryPagingAdapter1(MyItineraryPagingAdapter1.OnClickListener{
            viewModel.navigateToDetailDate(it)
        }
        )
        binding.viewModel = viewModel
        viewModel.getFireBaseJourney()

        viewModel.getAllJourneyLiveData.observe(viewLifecycleOwner){
            (binding.myitineraryRecyclerView.adapter as MyItineraryPagingAdapter1).submitList(viewModel.getAllJourney)
            (binding.myitineraryRecyclerView.adapter as MyItineraryPagingAdapter1).notifyDataSetChanged()

        }

        viewModel.navigateToDetailDate.observe(
            viewLifecycleOwner,
            Observer{
                it?.let {


                    findNavController().navigate(NavigationDirections.myItineraryPagingFragmentItineraryDetailFragment(it))


                    viewModel.onDetailNavigated()
                }
            })



        return binding.root

    }


}