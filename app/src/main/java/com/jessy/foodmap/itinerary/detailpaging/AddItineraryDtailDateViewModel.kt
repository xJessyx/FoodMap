package com.jessy.foodmap.itinerary.detailpaging

import androidx.lifecycle.ViewModel
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Journey

class AddItineraryDtailDateViewModel : ViewModel(){
    val dataList1 = mutableListOf<Journey>()


    fun addData(){
        val item1 = Journey(R.drawable.cake, "天天", ":2022/01/01", "2022/01/10")
        val item2 = Journey(R.drawable.cake_pops, "彎彎", "2022/02/01", "2022/02/03")
        val item3 = Journey(R.drawable.churros, "略綠", "2022/01/01", "2022/01/22")
        val item4 = Journey(R.drawable.cookies, "ㄏ黑", "2022/02/01", "2022/02/21")
        val item5 = Journey(R.drawable.cupcakes, "嘶嘶嘶", "2022/03/01", "2022/03/15")
        val item6 = Journey(R.drawable.macarons, "天已", "2022/12/01", "2022/12/11")
        dataList1.add(item1)
        dataList1.add(item2)
        dataList1.add(item3)
        dataList1.add(item4)
        dataList1.add(item5)
        dataList1.add(item6)

    }
}