package com.jessy.foodmap.itinerary.invite.paging

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentJoinBinding
import com.jessy.foodmap.databinding.FragmentWaitingJoinBinding

class WaitingJoinFragment : Fragment() {

    private val viewModel: JoinViewModel by lazy {
        ViewModelProvider(this).get(JoinViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentWaitingJoinBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.getWaitJoinInviteItem()
        val adapter =JoinAdapter()
        binding.waitingJoinRecyclerView.adapter = adapter
        binding.waitingJoinRecyclerView.addItemDecoration(DividerItemDecoration(activity as Activity, DividerItemDecoration.VERTICAL))
//        binding.waitingJoinRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.getWaitJoinInvite.observe(viewLifecycleOwner){it?.let {

            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }

        }

        return binding.root    }

}