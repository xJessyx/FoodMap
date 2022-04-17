package com.jessy.foodmap.Itinerary.detailpaging

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ItinerayDetailPagingAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    var fragments: ArrayList<Fragment> = arrayListOf(
        AddItinerayDetailDateFragment(),AddItinerayDetailDateFragment(),AddItinerayDetailDateFragment(),AddItinerayDetailDateFragment()

    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}