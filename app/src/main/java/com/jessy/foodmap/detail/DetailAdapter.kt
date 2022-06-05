package com.jessy.foodmap.detail

import android.content.Context
import androidx.recyclerview.widget.ListAdapter
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Messages
import com.jessy.foodmap.databinding.ItemMessageBinding
import com.jessy.foodmap.login.UserManager.Companion.user

class DetailAdapter(val context: Context, val viewModel: DetailViewModel) : ListAdapter<Messages,
        DetailAdapter.DetailViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, context, viewModel)
    }

    class DetailViewHolder private constructor(var binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(messages: Messages, context: Context, viewModel: DetailViewModel) {

            binding.messages = messages
            binding.detailMessageMoreBtn.setOnClickListener {
                showPopup(context, it, viewModel, messages.userId)
            }
            binding.executePendingBindings()

        }

        fun showPopup(context: Context, view: View, viewModel: DetailViewModel, userId: String?) {
            val popup = PopupMenu(context, view)
            popup.inflate(R.menu.article_more_menu)

            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

                when (item!!.itemId) {
                    R.id.blockade -> {
                        viewModel.addBlockadeUsers(userId)
                        Toast.makeText(context, "已完成", Toast.LENGTH_SHORT).show()
                    }

                }
                true
            })

            popup.show()
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