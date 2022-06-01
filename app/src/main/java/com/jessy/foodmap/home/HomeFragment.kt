package com.jessy.foodmap.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        (activity as MainActivity).showToolBar()
        (activity as MainActivity).showBottomNavigation()

        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        var manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.homeRecyclerView.layoutManager = manager
        manager.setAutoMeasureEnabled(true)
        binding.homeRecyclerView.setHasFixedSize(true)
        binding.homeRecyclerView.adapter = HomeAdapter(HomeAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        })
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.getFireBaseArticle()


        viewModel.getAllArticlesLiveData.observe(viewLifecycleOwner){
            (binding.homeRecyclerView.adapter as HomeAdapter).submitList(it)

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

        binding.homePostFabBtn.setOnClickListener {

            findNavController().navigate(NavigationDirections.homeFragmentAddArticleFragment())

        }

        return binding.root
    }

}