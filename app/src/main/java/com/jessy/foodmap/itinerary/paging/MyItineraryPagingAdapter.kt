package com.jessy.foodmap.itinerary.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.databinding.ItemMyLtineraryPagingBinding
import com.jessy.foodmap.itinerary.ITHelperInterface
import com.jessy.foodmap.login.UserManager
import java.util.*

class MyItineraryPagingAdapter (private val onClickListener: OnClickListener) :
    ListAdapter<Journey, MyItineraryPagingAdapter.MyItineraryPagingViewHolder>(
        MyItineraryPagingAdapter.DiffCallback()) {

    class MyItineraryPagingViewHolder private constructor(var binding: ItemMyLtineraryPagingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(journey: Journey) {
            binding.journey = journey
            binding.executePendingBindings()
            binding.myitineraryCoeditImage.setOnClickListener {
                it.findNavController()
                    .navigate(NavigationDirections.myItineraryPagingFragmentInviteFragment(journey))
            }

        }

        companion object {
            fun from(parent: ViewGroup): MyItineraryPagingViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMyLtineraryPagingBinding.inflate(layoutInflater, parent, false)
                return MyItineraryPagingViewHolder(binding)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItineraryPagingViewHolder {
        return MyItineraryPagingViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: MyItineraryPagingViewHolder, position: Int) {

        val item = getItem(position)



        if (item != null) {
            holder.itemView.setOnClickListener {
                onClickListener.onClick(item)
            }

           holder.bind(item)
            when (item.status) {
                0 -> holder.binding.myitineraryStatus.setText("規劃中")
                1 -> holder.binding.myitineraryStatus.setText("進行中")
                2 -> holder.binding.myitineraryStatus.setText("已結束")
            }

        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Journey>() {

        override fun areItemsTheSame(oldItem: Journey, newItem: Journey): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Journey, newItem: Journey): Boolean {
            return oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (journey: Journey) -> Unit) {
        fun onClick(journey: Journey) = clickListener(journey)
    }



}