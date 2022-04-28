package com.jessy.foodmap.itinerary.detailpaging

import android.icu.text.SimpleDateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.Place
import com.jessy.foodmap.databinding.ItemAddItineraryDetailDateBinding
import java.util.*

class AddItineraryDtailDateAdapter(val onClickListener: AddItineraryDtailDateAdapter.OnClickListener) :
    ListAdapter<Place, AddItineraryDtailDateAdapter.AddItineraryDtailDateViewHolder>(
        AddItineraryDtailDateAdapter.DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<Place>() {

        override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem == newItem
        }
    }


    class AddItineraryDtailDateViewHolder private constructor(var binding: ItemAddItineraryDetailDateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(place: Place) {

//            binding.place = place

            binding.itineraryDetailDateTvStartTime.setText(place.startTime?.let {
                TimeUtil.StampToTime(it, Locale.TAIWAN).toString()
            })


            binding.itineraryDetailDateTvDwellTime.setText(place.dwellTime?.let {
                TimeUtil.StampToTimeText(it, Locale.TAIWAN).toString()
            })

            binding.itineraryDetailDateTvTrafficTime.setText(place.trafficTime?.let {
                TimeUtil.StampToTimeText(it, Locale.TAIWAN).toString()
            })

            binding.itineraryDetailDateTvStoreName.setText(place.name)

        }

        companion object {
            fun from(parent: ViewGroup): AddItineraryDtailDateViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemAddItineraryDetailDateBinding.inflate(layoutInflater, parent, false)
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
        holder: AddItineraryDtailDateViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    class OnClickListener(val clickListener: (place: Place) -> Unit) {
        fun onClick(place: Place) = clickListener(place)
    }

    object TimeUtil {

        @JvmStatic
        fun StampToTime(time: Long, locale: Locale): String {
            // 進來的time以秒為單位，Date輸入為毫秒為單位，要注意

            val simpleDateFormat = SimpleDateFormat("HH:mm", locale)

            return simpleDateFormat.format(Date(time))
        }


        @JvmStatic
        fun StampToTimeText(time: Long, locale: Locale): String {
            // 進來的time以秒為單位，Date輸入為毫秒為單位，要注意

            val simpleDateFormat = SimpleDateFormat("HH 時 mm 分", locale)

            return simpleDateFormat.format(Date(time))
        }


    }
}
