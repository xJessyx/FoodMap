package com.jessy.foodmap.member


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.databinding.FragmentMemberBinding
import com.jessy.foodmap.itinerary.paging.MyItineraryPagingViewModel


class MemberFragment : Fragment() {

    private val viewModel: MemberViewModel by lazy {
        ViewModelProvider(this).get(MemberViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        (activity as MainActivity).hideToolBar()
        val binding = FragmentMemberBinding.inflate(inflater, container, false)

        val pageAdapter = MemberPagingAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.memberViewpager2.adapter = pageAdapter
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.getFireBaseUser()
        viewModel.getUserLiveData.observe(viewLifecycleOwner){
                    binding.memberTvNamePerson.text = viewModel.getUser[0].name
                    binding.memberEmail.text = viewModel.getUser[0].email
        }

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