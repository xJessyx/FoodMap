package com.jessy.foodmap.itinerary.detailpaging

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.jessy.foodmap.databinding.FragmentAddItineraryDetailDateBinding
import com.jessy.foodmap.home.HomeAdapter
import com.jessy.foodmap.home.HomeViewModel


class AddItineraryDetailDateFragment(position: Int) : Fragment() {
    private val viewModel: AddItineraryDtailDateViewModel by lazy {
        ViewModelProvider(this).get(AddItineraryDtailDateViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentAddItineraryDetailDateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewMedel = viewModel
            viewModel.addData()
            val adapter = AddItineraryDtailDateAdapter(AddItineraryDtailDateAdapter.OnClickListener {

            })

            binding.detailRecyclerViewDate.adapter = adapter
            adapter.submitList(viewModel.dataList1)

        return binding.root

    }

}