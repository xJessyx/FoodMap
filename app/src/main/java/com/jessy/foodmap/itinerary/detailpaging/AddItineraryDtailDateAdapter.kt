package com.jessy.foodmap.itinerary.detailpaging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.databinding.ItemAddItineraryDetailDateBinding

class AddItineraryDtailDateAdapter (val onClickListener: AddItineraryDtailDateAdapter.OnClickListener) : ListAdapter<Journey, AddItineraryDtailDateAdapter.AddItineraryDtailDateViewHolder>(
    AddItineraryDtailDateAdapter.DiffCallback()){

    class DiffCallback : DiffUtil.ItemCallback<Journey>() {

        override fun areItemsTheSame(oldItem: Journey, newItem: Journey): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Journey, newItem: Journey): Boolean {
            return    oldItem == newItem
        }
    }


    class AddItineraryDtailDateViewHolder private constructor(var binding: ItemAddItineraryDetailDateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(journey: Journey) {
            binding.itineraryDetailDateBtPlace.setText(journey.journeyName)
            binding.itineraryDetailDateTvTime.setText("10:00")//假資料
            binding.itineraryDetailDateTvDwellTime.setText("Place.dwellTime")
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): AddItineraryDtailDateViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAddItineraryDetailDateBinding.inflate(layoutInflater, parent, false)
                return AddItineraryDtailDateViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AddItineraryDtailDateViewHolder {
        return AddItineraryDtailDateViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: AddItineraryDtailDateViewHolder ,
        position: Int,
    ) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    class OnClickListener(val clickListener: (journey: Journey) -> Unit) {
        fun onClick(journey: Journey) = clickListener(journey)
    }

}