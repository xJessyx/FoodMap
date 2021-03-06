package com.jessy.foodmap.itinerary.paging

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentMyItineraryPagingBinding
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
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel._loadStatus.value = true

        val adapter = MyItineraryPagingAdapter1(MyItineraryPagingAdapter1.OnClickListener {
            viewModel.navigateToDetailDate(it)
        })

        binding.myitineraryRecyclerView.adapter = adapter

        binding.viewModel = viewModel

        viewModel.getAllJourneyLiveData.observe(viewLifecycleOwner) {
            it?.let {
                // query all user's info
                val allCoworkUsers = mutableListOf<String>()
                it.forEach { journey ->

                    journey.coEditUser.forEach {
                        allCoworkUsers.add(it)
                    }
                }
                viewModel.queryAllUsers(allCoworkUsers)
            }

        }

        viewModel.coEditUserInfos.observe(viewLifecycleOwner) {
            it?.let {
                adapter.setUsers(it)

                viewModel.getAllJourneyLiveData.value?.let {
                    adapter.submitList(it)
                    adapter.notifyDataSetChanged()
                    viewModel._loadStatus.value = false

                }

            }
        }

        viewModel.getFireBaseCoEditJourney()
        viewModel.checkInviteItem()

        viewModel.checkInvite.observe(viewLifecycleOwner) {
            for (i in 0..it.lastIndex) {
                checkAlertDialog(it[i].journeyName,
                    it[i].receiveName,
                    it[i].journeyId,
                    it[i].id)
            }
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

        viewModel.loadStatus.observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(NavigationDirections.myItineraryPagingFragmentProgressBarFragment())

            } else {
                findNavController().popBackStack()
            }

        }

        return binding.root

    }

    private fun checkAlertDialog(
        journeyName: String,
        receiveName: String,
        journeyId: String,
        id: String,

        ) {
        AlertDialog.Builder(activity as Activity)
            .setTitle(R.string.journey_invitation)
            .setMessage("$receiveName ????????????????????????$journeyName??? ??????")
            .setPositiveButton(R.string.join) { _, _ ->
                Toast.makeText(activity as Activity, R.string.trip_added, Toast.LENGTH_SHORT).show()
                viewModel.updateInviteStatusTrue(id)
                viewModel.updateCoEdit(journeyId)
            }
            .setNeutralButton(R.string.reject) { _, _ ->
                Toast.makeText(activity as Activity, R.string.trip_rejected, Toast.LENGTH_SHORT)
                    .show()
                viewModel.updateInviteStatusFalse(id)

            }
            .show()
    }

}