package com.jessy.foodmap.itinerary.invite.paging

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.datatransport.runtime.scheduling.jobscheduling.WorkInitializer_Factory.newInstance

class InvitePagingAdapter (val journeyId:String, fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    var fragments: ArrayList<Fragment> = arrayListOf(

        WaitingJoinFragment.newInstance(journeyId),
        JoinFragment.newInstance(journeyId)
    )
    override fun getItemCount(): Int {
        return fragments.size

    }

    override fun createFragment( position: Int,): Fragment {

        return  fragments[position]
    }

}