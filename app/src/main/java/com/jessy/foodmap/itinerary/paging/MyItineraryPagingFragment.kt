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
import com.jessy.foodmap.itinerary.ItemTouchHelperCallback
import com.jessy.foodmap.itinerary.paging.MyItineraryPagingAdapter as MyItineraryPagingAdapter1

class MyItineraryPagingFragment : Fragment() {

    private val viewModel: MyItineraryPagingViewModel by lazy {
        ViewModelProvider(this).get(MyItineraryPagingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentMyItineraryPagingBinding.inflate(inflater, container, false)

        binding.myitineraryRecyclerView.adapter = MyItineraryPagingAdapter1(MyItineraryPagingAdapter1.OnClickListener{
            viewModel.navigateToDetailDate(it)
        }
        )
        var MyItineraryPagingAdapter1 = binding.myitineraryRecyclerView.adapter

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        val callback = ItemTouchHelperCallback(MyItineraryPagingAdapter1)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView( binding.myitineraryRecyclerView)
//        val today = LocalDate.now()
//        val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//
//        val parseStartDate = LocalDate.parse(viewModel.getAllJourney.startDate, fmt)
//        val parseEndDate = LocalDate.parse(journeyArg.endDate, fmt)
//
//        if(journeyArg.userId == "32fRAA8nlkV2gAojqHB1" && journeyArg.share == false) {
//            if (today.isBefore(parseStartDate)) {
//                binding.itineraryDetailFabBtn.visibility = View.VISIBLE
//                Log.v("today< start", "$today <  $parseStartDate")
//
//            } else if (today.isAfter(parseEndDate)) {
//                binding.itineraryDetailFabBtn.visibility = View.GONE
//                binding.itineraryDetailShare.visibility = View.VISIBLE
//                binding.itineraryDetailSwitch.visibility = View.VISIBLE
//                db.collection("journeys").document(journeyArg.id)
//                    .update("status", 2)
//
//                binding.itineraryDetailSwitch.setOnClickListener {
//                    if (binding.itineraryDetailSwitch.isChecked) {
////                EndSwitch.setBackgroundColor(Color.DKGRAY)
//                        binding.itineraryDetailSwitch.setTextColor(Color.WHITE)
//                        db.collection("journeys").document(journeyArg.id)
//                            .update("share", true)
//
//                    } else {
//                        binding.itineraryDetailSwitch.setBackgroundColor(Color.WHITE)
//                        binding.itineraryDetailSwitch.setTextColor(Color.BLACK)
//                        db.collection("journeys").document(journeyArg.id)
//                            .update("share", false)
//                    }
//                }
//
//                Log.v("today > end", "$today >  $parseEndDate")
//
//            } else {
//                binding.itineraryDetailFabBtn.visibility = View.VISIBLE
//                Log.v("start <today< end", " $parseStartDate < $today <  $parseEndDate ")
//                db.collection("journeys").document(journeyArg.id)
//                    .update("status", 1)
//            }
//        }
//

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

//                    val today = LocalDate.now()
//                    val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//                    val startDate= it.startDate
//                    val endDate= it.endDate
//                    val parseStartDate = LocalDate.parse(startDate, fmt)
//                    val parseEndDate = LocalDate.parse(endDate, fmt)

//                    if(today.isBefore(parseStartDate)) {
//
//                        findNavController().navigate(NavigationDirections.myItineraryPagingFragmentItineraryDetailFragment(it))
//
//                        Log.v("today< start","$today <  $parseStartDate")
//                    }else if(today.isAfter(parseEndDate)){
//                        findNavController().navigate(NavigationDirections.myItineraryPagingFragmentItineraryDetailEndFragment(it))
//
//                        Log.v("today > end","$today >  $parseEndDate")
//
//                    }else{
//                        findNavController().navigate(NavigationDirections.myItineraryPagingFragmentItineraryDetailFragment(it))
//
//                        Log.v("start <today< end"," $parseStartDate < $today <  $parseEndDate ")
//
//                    }


                    viewModel.onDetailNavigated()
                }
            })



        return binding.root

    }


}