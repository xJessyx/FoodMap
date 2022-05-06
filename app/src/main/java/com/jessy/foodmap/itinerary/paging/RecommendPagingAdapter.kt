package com.jessy.foodmap.itinerary.paging

import android.icu.util.Calendar
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jessy.foodmap.data.Article
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.databinding.ItemRecommendPagingBinding
import com.jessy.foodmap.home.HomeAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RecommendPagingAdapter(private val onClickListener: OnClickListener) : ListAdapter<Journey, RecommendPagingAdapter.RecommendPagingViewHolder>(
    RecommendPagingAdapter.DiffCallback()){

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
                Log.v("item in ", "$item")
            }

            holder.bind(item)
            when(item.status){
                0-> holder.binding.recommendStatus.setText("規劃中")
                1-> holder.binding.recommendStatus.setText("進行中")
                2-> holder.binding.recommendStatus.setText("已結束")
            }
//            val today = LocalDate.now()
//            val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//            val startDate= item.startDate
//            val endDate= item.endDate
//            val parseStartDate = LocalDate.parse(startDate, fmt)
//            val parseEndDate = LocalDate.parse(endDate, fmt)
//
//              if(today.isBefore(parseStartDate)) {
//                    holder.itemView.setOnClickListener {
//                      onClickListener.onClick(item)
//                    }
//                    Log.v("today< start","$today <  $parseStartDate")
//                }else if(today.isAfter(parseEndDate)){
//                  Log.v("today > end","$today >  $parseEndDate")
//                  holder.itemView.setOnClickListener {
//                      onClickListener.onClick(item)
//
//                  }
//              }else{
//                  Log.v("start <today< end"," $parseStartDate < $today <  $parseEndDate ")
//                  holder.itemView.setOnClickListener {
//                      onClickListener.onClick(item)
//                  }
//              }

            }

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