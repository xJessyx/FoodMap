package com.jessy.foodmap.itinerary.invite

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentInviteBinding
import com.jessy.foodmap.itinerary.invite.paging.InvitePagingAdapter


class InviteFragment : Fragment() {

    private val viewModel: InviteViewModel by lazy {
        ViewModelProvider(this).get(InviteViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentInviteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val journeyArg = InviteFragmentArgs.fromBundle(requireArguments()).journeyKey
        val viewModel = InviteViewModel(journeyArg)
        binding.viewModel =viewModel
        val pageAdapter = InvitePagingAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.inviteViewpager2.adapter = pageAdapter

        binding.inviteItineraryName.text = journeyArg.name
        binding.inviteStartDate.text = journeyArg.startDate
        binding.inviteEndDate.text = journeyArg.endDate
        binding.inviteBtn.setOnClickListener {
            val item = LayoutInflater.from(activity as Activity).inflate(R.layout.item_layout, null)

            AlertDialog.Builder(activity as Activity)
                .setTitle("請輸入要邀請的email")
                .setView(item)
                .setPositiveButton("確定") { _, _ ->
                    val editText = item.findViewById(R.id.edit_text) as EditText
                    val email = editText.text.toString()
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(activity as Activity, "請輸入要邀請的email", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.addInvitationsItem(email)

                        viewModel.addInvite.observe(viewLifecycleOwner){
                            viewModel.addFireBaseInvitations()

                        }
                    }
                }
                .show()
        }


        TabLayoutMediator(binding.inviteTabs, binding.inviteViewpager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "等待加入"
                }
                1 -> {
                    tab.text = "已加入"
                }
            }

        }.attach()

    return binding.root
    }



}