package com.jessy.foodmap.itinerary.detailpaging

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jessy.foodmap.data.Journey



class ItineraryDetailPagingAdapter(fragment: Fragment, val journey: Journey) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int {
        return journey.totalDay

    }

    override fun createFragment(position: Int): Fragment {
        return AddItineraryDetailDateFragment(position, journey)
    }
}
