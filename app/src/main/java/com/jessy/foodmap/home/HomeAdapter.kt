package com.jessy.foodmap.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.jessy.foodmap.data.article
import com.jessy.foodmap.databinding.ItemHomeArticleBinding


class HomeAdapter: ListAdapter<article,
        HomeAdapter.HomeViewHolder>(SiylishDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

    }
    class HomeViewHolder private constructor(var binding:ItemHomeArticleBinding ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(articleData: article) {
         //   binding.article = articleData
//            articleData.author = binding.homeAuthor.toString()
//            articleData.image = binding.homeImg.toString()
//            articleData.collectNumber = binding.homeCollectNumber.toString()
            binding.homeAuthor.setText(articleData.author)
            binding.homeImg.setImageResource(articleData.image)
            binding.homeCollectNumber.setText(articleData.collectNumber)


            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): HomeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHomeArticleBinding.inflate(layoutInflater, parent, false)
                return HomeViewHolder(binding)
            }
        }
    }

    class SiylishDiffCallback : DiffUtil.ItemCallback<article>() {

        override fun areItemsTheSame(oldItem: article, newItem: article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: article, newItem: article): Boolean {
            return    oldItem == newItem
        }
    }


}