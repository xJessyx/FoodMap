package com.jessy.foodmap.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.jessy.foodmap.data.Article
import com.jessy.foodmap.databinding.ItemHomeArticleBinding


class HomeAdapter(val onClickListener: OnClickListener): ListAdapter<Article,
        HomeAdapter.HomeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
            holder.bind(item)
    }
    class HomeViewHolder private constructor(var binding:ItemHomeArticleBinding ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(articleData: Article) {
          binding.article = articleData
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

    class DiffCallback : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return    oldItem == newItem
        }
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------

    class OnClickListener(val clickListener: (article:Article) -> Unit) {
        fun onClick(article:Article) = clickListener(article)
    }


}