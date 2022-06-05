package com.jessy.foodmap.itinerary.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.databinding.ItemRecommendPagingBinding

class RecommendPagingAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Journey, RecommendPagingAdapter.RecommendPagingViewHolder>(
        RecommendPagingAdapter.DiffCallback()) {

    class RecommendPagingViewHolder private constructor(var binding: ItemRecommendPagingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(journey: Journey) {
            binding.journey = journey
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): RecommendPagingViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRecommendPagingBinding.inflate(layoutInflater, parent, false)
                return RecommendPagingViewHolder(binding)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendPagingViewHolder {
        return RecommendPagingViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: RecommendPagingViewHolder, position: Int) {

        val item = getItem(position)



        if (item != null) {
            holder.itemView.setOnClickListener {
                onClickListener.onClick(item)
            }

            holder.bind(item)

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