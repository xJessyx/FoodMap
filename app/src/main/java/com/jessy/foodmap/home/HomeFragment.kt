package com.jessy.foodmap.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jessy.foodmap.databinding.FragmentHomeBinding

class homeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        var manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.homeRecyclerView.layoutManager = manager  //佈局管理

        return binding.root
    }

}