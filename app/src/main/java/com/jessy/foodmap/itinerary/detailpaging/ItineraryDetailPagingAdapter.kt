package com.jessy.foodmap.itinerary.detailpaging

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
//
//class ItineraryDetailPagingAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
//    FragmentStateAdapter(fragmentManager, lifecycle) {
//
//
//    var fragments: ArrayList<Fragment> = arrayListOf(
//        AddItineraryDetailDateFragment()
//    )
//
//    override fun getItemCount(): Int {
//        return fragments.size
//    }
//
//    override fun createFragment(position: Int): Fragment {
//        return fragments[position]
//    }
//}

class ItineraryDetailPagingAdapter(fragment: Fragment, val count: Int) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return count
    }

    override fun createFragment(position: Int): Fragment {
        return AddItineraryDetailDateFragment(position)
    }
}
