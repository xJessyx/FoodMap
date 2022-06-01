package com.jessy.foodmap.itinerary.invite.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jessy.foodmap.data.Invite
import com.jessy.foodmap.databinding.ItemJoinBinding

class JoinAdapter :
    ListAdapter<Invite, JoinAdapter.JoinViewHolder>(
        JoinAdapter.DiffCallback()) {

    class JoinViewHolder private constructor(var binding: ItemJoinBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Invite) {
            binding.invite = item
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