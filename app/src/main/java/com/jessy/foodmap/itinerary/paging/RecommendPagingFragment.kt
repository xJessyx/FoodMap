package com.jessy.foodmap.itinerary.paging

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.databinding.FragmentRecommendPagingBinding


class RecommendPagingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentRecommendPagingBinding.inflate(inflater, container, false)


        val adapter = RecommendPagingAdapter()
        binding.recommendRecyclerView.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner

        val dataList1 = mutableListOf<Journey>()

        val item1 = Journey(R.drawable.cake, "天天aaa ", ":2022/01/01", "2022/01/10","ya","","","ya","")
        val item2 = Journey(R.drawable.cake_pops, "彎彎bbbb", "2022/02/01", "2022/02/03","cc","","","cc","")
        val item3 = Journey(R.drawable.churros, "略綠ccc", "2022/01/01", "2022/01/22","","","","dd","")
        val item4 = Journey(R.drawable.cookies, "ㄏ黑ss", "2022/02/01", "2022/02/21","qq","","","dds","")
        val item5 = Journey(R.drawable.cupcakes, "嘶嘶嘶ddddd", "2022/03/01", "2022/03/15","joy","","","cds","")
        val item6 = Journey(R.drawable.macarons, "天已aaaaaf", "2022/12/01", "2022/12/11","cd","","","ccc","")
        dataList1.add(item1)
        dataList1.add(item2)
        dataList1.add(item3)
        dataList1.add(item4)
        dataList1.add(item5)
        dataList1.add(item6)
         adapter.submitList(dataList1)






        return binding.root

    }

}