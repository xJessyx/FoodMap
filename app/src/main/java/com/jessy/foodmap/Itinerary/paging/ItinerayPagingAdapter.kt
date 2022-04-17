package com.jessy.foodmap.Itinerary.paging

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jessy.foodmap.Itinerary.paging.MyItineraryPagingFragment
import com.jessy.foodmap.Itinerary.paging.RecommendPagingFragment

class ItinerayPagingAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    var fragments: ArrayList<Fragment> = arrayListOf(
        MyItineraryPagingFragment(),
        RecommendPagingFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}