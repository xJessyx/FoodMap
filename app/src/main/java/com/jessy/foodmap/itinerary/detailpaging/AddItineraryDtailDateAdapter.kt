package com.jessy.foodmap.itinerary.detailpaging

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Place
import com.jessy.foodmap.databinding.ItemAddItineraryDetailDateBinding
import com.jessy.foodmap.itinerary.ITHelperInterface
import java.util.*

class AddItineraryDtailDateAdapter(
    val onClickListener: AddItineraryDtailDateAdapter.OnClickListener,
    val viewModel: AddItineraryDtailDateViewModel,
) :
    ListAdapter<Place, AddItineraryDtailDateAdapter.AddItineraryDtailDateViewHolder>(
        AddItineraryDtailDateAdapter.DiffCallback()), ITHelperInterface {

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

        fun bind(place: Place, viewModel: AddItineraryDtailDateViewModel) {

            binding.itineraryDetailDateTvStartTime.setText(place.startTime?.let {
                TimeUtil.StampToTime(it, Locale.TAIWAN)
            })



            binding.itineraryDetailDateTvDwellTime.setText(place.dwellTime?.let {
                TimeUtil.StampToTimeText(it, Locale.TAIWAN).toString()
            })

            binding.itineraryDetailDateTvTrafficTime.setText(place.trafficTime?.let {
                TimeUtil.StampToTimeText(it, Locale.TAIWAN).toString()
            })

            binding.itineraryDetailDateTvStoreName.setText(place.name)

            when (place.transportation) {
                1 -> binding.itineraryDetailDateImgTransportation.setImageResource(R.drawable.walking)
                2 -> binding.itineraryDetailDateImgTransportation.setImageResource(R.drawable.sedan)
                3 -> binding.itineraryDetailDateImgTransportation.setImageResource(R.drawable.bicycle)
                4 -> binding.itineraryDetailDateImgTransportation.setImageResource(R.drawable.train)
            }


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

        holder.bind(item, viewModel)

        if (position == currentList.size - 1) {
            holder.binding.itineraryDetailDateLine.visibility = View.GONE
            holder.binding.itineraryDetailDateImgTransportation.visibility = View.GONE
            holder.binding.itineraryDetailDateTvTrafficTime.visibility = View.GONE

        } else {

            holder.binding.itineraryDetailDateLine.visibility = View.VISIBLE
            val nextItem = getItem(position + 1).transportation

            when (nextItem) {

                1 -> holder.binding.itineraryDetailDateImgTransportation.setImageResource(R.drawable.walking)
                2 -> holder.binding.itineraryDetailDateImgTransportation.setImageResource(R.drawable.sedan)
                3 -> holder.binding.itineraryDetailDateImgTransportation.setImageResource(R.drawable.bicycle)
                4 -> holder.binding.itineraryDetailDateImgTransportation.setImageResource(R.drawable.train)
            }

            holder.binding.itineraryDetailDateImgTransportation.visibility = View.VISIBLE
            holder.binding.itineraryDetailDateTvTrafficTime.visibility = View.VISIBLE

        }


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


    override fun onItemDissmiss(position: Int) {
        val list = currentList.toMutableList()
        viewModel.delectFireBaseItem(list, position)

        list.removeAt(position)
        submitList(list)
//        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        val list = currentList.toMutableList()
        Collections.swap(list, fromPosition, toPosition)
        submitList(list)
        viewModel.updateMoveList(list, fromPosition, toPosition)

//        notifyItemMoved(fromPosition,toPosition)
    }
}
