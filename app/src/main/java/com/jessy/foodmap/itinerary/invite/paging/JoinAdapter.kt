package com.jessy.foodmap.itinerary.invite.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.data.Invite
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.databinding.ItemJoinBinding
import com.jessy.foodmap.databinding.ItemMyLtineraryPagingBinding
import com.jessy.foodmap.itinerary.paging.MyItineraryPagingAdapter

class JoinAdapter :
    ListAdapter<Invite, JoinAdapter.JoinViewHolder>(
        JoinAdapter.DiffCallback()) {

    class JoinViewHolder private constructor(var binding: ItemJoinBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item:Invite) {
            binding.invite = item
//            binding.joinEmail.text = item.senderEmail
//            binding.joinName.text = item.senderName
//            binding.joinImg.
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): JoinViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemJoinBinding.inflate(layoutInflater, parent, false)
                return JoinViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JoinViewHolder {
        return JoinAdapter.JoinViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: JoinViewHolder, position: Int) {


        val item = getItem(position)
        Log.v(  "item","$item")
        holder.bind(item)

    }
    class DiffCallback : DiffUtil.ItemCallback<Invite>() {

        override fun areItemsTheSame(oldItem: Invite, newItem: Invite): Boolean {

            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Invite, newItem: Invite): Boolean {
            return oldItem == newItem
        }
    }

}