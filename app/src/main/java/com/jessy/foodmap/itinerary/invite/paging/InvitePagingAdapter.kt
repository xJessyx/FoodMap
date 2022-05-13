package com.jessy.foodmap.itinerary.invite.paging

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jessy.foodmap.data.Invite
import com.jessy.foodmap.member.paging.CollectPagingFragment
import com.jessy.foodmap.member.paging.MapsPagingFragment

class InvitePagingAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    var fragments: ArrayList<Fragment> = arrayListOf(

        WaitingJoinFragment(),
        JoinFragment()
    )
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment( position: Int): Fragment {
        return fragments[position]
    }

}