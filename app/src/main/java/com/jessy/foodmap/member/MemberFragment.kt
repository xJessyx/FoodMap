package com.jessy.foodmap.member

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.jessy.foodmap.databinding.FragmentMemberBinding


class MemberFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMemberBinding.inflate(inflater, container, false)

        val pageAdapter = MemberPagingAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.memberViewpager2.adapter = pageAdapter

        TabLayoutMediator(binding.memberTabs, binding.memberViewpager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "收藏"
                }
                1 -> {
                    tab.text = "我的迷霧地圖"
                }

            }        }.attach()

        return binding.root

    }

}