package com.jessy.foodmap.itinerary.paging

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.databinding.FragmentMyItineraryPagingBinding
import com.jessy.foodmap.itinerary.ITHelperInterface
import com.jessy.foodmap.itinerary.ItemTouchHelperCallback
import com.jessy.foodmap.login.UserManager.Companion.user
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
            (binding.myitineraryRecyclerView.adapter as MyItineraryPagingAdapter1).submitList(it)
            (binding.myitineraryRecyclerView.adapter as MyItineraryPagingAdapter1).notifyDataSetChanged()

        }
        viewModel.checkInviteItem()

        viewModel.checkInvite.observe(viewLifecycleOwner){
            for (i in 0..it.lastIndex){
                checkAlertDialog(it[i].journeyName,it[i].receiveName,it[i].journeyId,it[i].id,it[i].senderId,it[i].senderImage,it[i].senderName)
            Log.v("it","$it")
             }
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

    private fun checkAlertDialog(
        journeyName: String,
        receiveName: String,
        journeyId: String,
        id: String,
        senderId: String,
        senderImage: String,
        senderName: String
    ) {
        AlertDialog.Builder(activity as Activity)
            .setTitle("旅程邀請")
            .setMessage("$receiveName 邀請是否要加入【$journeyName】 行程")
            .setPositiveButton("加入") {
                    _, _ -> Toast.makeText(activity as Activity,"已加入行程", Toast.LENGTH_SHORT).show()
                viewModel.updateInviteStatusTrue(id)
                viewModel.updateCoEdit(journeyId)
            }
            .setNeutralButton("拒絕") { _, _ -> Toast.makeText(activity as Activity, "已拒絕行程", Toast.LENGTH_SHORT).show()
                viewModel.updateInviteStatusFalse(id)
                viewModel.updateSenderData(id,senderId,senderImage,senderName)
            }
            .show()
    }

}