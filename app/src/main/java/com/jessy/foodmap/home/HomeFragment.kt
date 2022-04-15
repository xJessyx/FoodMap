package com.jessy.foodmap.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jessy.foodmap.R
import com.jessy.foodmap.data.article
import com.jessy.foodmap.databinding.FragmentHomeBinding

class homeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        var manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.homeRecyclerView.layoutManager = manager  //佈局管理
        manager.setAutoMeasureEnabled(true)
        val adapter = HomeAdapter()
        binding.homeRecyclerView.adapter = adapter

        val item1 = article(R.drawable.cake,"天天","5212")
        val item2 = article(R.drawable.cake_pops,"彎彎","234")
        val item3 = article(R.drawable.churros,"略綠","33")
        val item4 = article(R.drawable.cookies,"ㄏ黑","3432")
        val item5 = article(R.drawable.cupcakes,"嘶嘶嘶","5555")
        val item6 = article(R.drawable.macarons,"天已","5216662")

        val dataList = mutableListOf<article>()

        dataList.add(item1)
        dataList.add(item2)
        dataList.add(item3)
        dataList.add(item4)
        dataList.add(item5)
        dataList.add(item6)
        Log.v("dataList1","$dataList")
        adapter.submitList(dataList)
            Log.v("dataList2","$dataList")

        return binding.root
    }

}