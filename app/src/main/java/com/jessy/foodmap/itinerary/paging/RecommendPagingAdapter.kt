package com.jessy.foodmap.itinerary.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jessy.foodmap.data.Article
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.databinding.ItemRecommendPagingBinding
import com.jessy.foodmap.home.HomeAdapter

class RecommendPagingAdapter(val onClickListener: OnClickListener) : ListAdapter<Journey, RecommendPagingAdapter.RecommendPagingViewHolder>(
    RecommendPagingAdapter.DiffCallback()){

    class RecommendPagingViewHolder private constructor(var binding: ItemRecommendPagingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(journey: Journey) {
            binding.recommendImg.setImageResource(journey.journeImage)
            binding.recommendItineraryName.setText(journey.journeyName)
            binding.recommendItineraryStartDate.setText(journey.startDate)
            binding.recommendItineraryEndDate.setText(journey.endtDate)
            binding.recommendName.setText("user: ya")

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
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)

    }
    class DiffCallback : DiffUtil.ItemCallback<Journey>() {

        override fun areItemsTheSame(oldItem: Journey, newItem: Journey): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Journey, newItem: Journey): Boolean {
            return    oldItem == newItem
        }
    }
    class OnClickListener(val clickListener: (journey: Journey) -> Unit) {
        fun onClick(journey: Journey) = clickListener(journey)
    }
}