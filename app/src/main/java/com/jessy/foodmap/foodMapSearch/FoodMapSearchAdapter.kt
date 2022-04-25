package com.jessy.foodmap.foodMapSearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jessy.foodmap.data.Article
import com.jessy.foodmap.data.StoreInformation
import com.jessy.foodmap.databinding.ItemFoodmapsearchBinding
import com.jessy.foodmap.home.HomeAdapter

class FoodMapSearchAdapter(val onClickListener: FoodMapSearchAdapter.OnClickListener) : ListAdapter<StoreInformation, FoodMapSearchAdapter.FoodMapSearchViewHolder>(
    DiffCallback()){

    class FoodMapSearchViewHolder private constructor(var binding: ItemFoodmapsearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(storeInformation: StoreInformation) {
            binding.foodMapSearchImg.setImageBitmap(storeInformation.storeImg)
            binding.foodMapSearchTitle.setText(storeInformation.storeTitle)
            binding.foodMapSearchAddress.setText(storeInformation.storeAddress)
            binding.foodMapSearchScore.setText(storeInformation.storeScore)
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): FoodMapSearchViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFoodmapsearchBinding.inflate(layoutInflater, parent, false)
                return FoodMapSearchViewHolder(binding)
            }
        }
    }



    class DiffCallback : DiffUtil.ItemCallback<StoreInformation>() {

        override fun areItemsTheSame(oldItem: StoreInformation, newItem: StoreInformation): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: StoreInformation, newItem: StoreInformation): Boolean {
            return    oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodMapSearchViewHolder {
        return FoodMapSearchViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FoodMapSearchViewHolder, position: Int) {
//        holder.bind(getItem(position))
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)

    }


    class OnClickListener(val clickListener: (storeInformation: StoreInformation) -> Unit) {
        fun onClick(storeInformation: StoreInformation) = clickListener(storeInformation)
    }
}