package com.jessy.foodmap.itinerary.paging

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.databinding.FragmentMyItineraryPagingBinding

class MyItineraryPagingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentMyItineraryPagingBinding.inflate(inflater, container, false)


        val adapter = RecommendPagingAdapter()
        binding.myitineraryRecyclerView.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner

        val dataList1 = mutableListOf<Journey>()

        val item1 = Journey(R.drawable.cake, "天天", ":2022/01/01", "2022/01/10","ya","","","aa","")
        val item2 = Journey(R.drawable.cake_pops, "彎彎", "2022/02/01", "2022/02/03","cc","","","xx","")
        val item3 = Journey(R.drawable.churros, "略綠", "2022/01/01", "2022/01/22","","","","ta","")
        val item4 = Journey(R.drawable.cookies, "ㄏ黑", "2022/02/01", "2022/02/21","qq","","","ff","")
        val item5 = Journey(R.drawable.cupcakes, "嘶嘶嘶", "2022/03/01", "2022/03/15","joy","","","dd","")
        val item6 = Journey(R.drawable.macarons, "天已", "2022/12/01", "2022/12/11","cd","","","dd","")
        dataList1.add(item1)
        dataList1.add(item2)
        dataList1.add(item3)
        dataList1.add(item4)
        dataList1.add(item5)
        dataList1.add(item6)

        adapter.submitList(dataList1)


        binding.myitineraryButton.setOnClickListener {
            findNavController().navigate(NavigationDirections.actionMyItineraryPagingFragmentToAddItineraryFragment())
        }

        return binding.root

    }

}