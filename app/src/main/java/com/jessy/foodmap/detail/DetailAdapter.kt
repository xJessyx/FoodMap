package com.jessy.foodmap.detail
import androidx.recyclerview.widget.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jessy.foodmap.data.Messages
import com.jessy.foodmap.databinding.ItemMessageBinding

class DetailAdapter : ListAdapter<Messages,
        DetailAdapter.DetailViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):DetailViewHolder {
        return DetailViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DetailViewHolder private constructor(var binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(messages: Messages) {

            binding.messages = messages
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): DetailViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMessageBinding.inflate(layoutInflater, parent, false)
                return DetailViewHolder(binding)
            }
        }
    }



    class DiffCallback : DiffUtil.ItemCallback<Messages>() {

        override fun areItemsTheSame(oldItem: Messages, newItem: Messages): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Messages, newItem: Messages): Boolean {
            return oldItem == newItem
        }
    }


}