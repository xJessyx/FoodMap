package com.jessy.foodmap.member.paging

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.databinding.FragmentCollectPagingBinding
import com.jessy.foodmap.home.HomeAdapter
import com.jessy.foodmap.home.HomeViewModel

class CollectPagingFragment : Fragment() {
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentCollectPagingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        var manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.collectRecyclerView.layoutManager = manager  //佈局管理
        manager.setAutoMeasureEnabled(true)
        binding.collectRecyclerView.setHasFixedSize(true)
        binding.collectRecyclerView.adapter = HomeAdapter(HomeAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        })
        binding.viewModel = viewModel

        viewModel.getFireBaseArticleCollect()

        viewModel.getAllArticlesCollectLiveData.observe(viewLifecycleOwner) {

            (binding.collectRecyclerView.adapter as HomeAdapter).submitList(it)

        }

        viewModel.navigateToDetail.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(NavigationDirections.homeToDetailFragment(it))
                    viewModel.onDetailNavigated()
                }
            }
        )

        return binding.root

    }

}