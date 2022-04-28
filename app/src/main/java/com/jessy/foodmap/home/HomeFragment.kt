package com.jessy.foodmap.home

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
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentHomeBinding
import com.jessy.foodmap.itinerary.paging.RecommendPagingAdapter

class homeFragment : Fragment() {
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        (activity as MainActivity).showToolBar()
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        var manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.homeRecyclerView.layoutManager = manager  //佈局管理
        manager.setAutoMeasureEnabled(true)
        binding.homeRecyclerView.adapter = HomeAdapter(HomeAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        })
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.getFireBaseArticle()

        viewModel.getAllArticlesLiveData.observe(viewLifecycleOwner){
            (binding.homeRecyclerView.adapter as HomeAdapter).submitList(it)
            Log.v("it","$it")

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