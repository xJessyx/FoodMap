package com.jessy.foodmap.itinerary.detailpaging

import androidx.lifecycle.ViewModel
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Journey

class AddItineraryDtailDateViewModel : ViewModel(){
    val dataList1 = mutableListOf<Journey>()


    fun addData(){
        val item1 = Journey("","https://images.unsplash.com/photo-1551024506-0bccd828d307?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8ZGVzc2VydHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60", "天天", ":2022/01/01", "2022/01/10", 0,1)
        val item2 = Journey("","https://images.unsplash.com/photo-1563805042-7684c019e1cb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8ZGVzc2VydHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60", "彎彎", "2022/02/01", "2022/02/03",0,1)
        val item3 = Journey("","https://images.unsplash.com/photo-1587314168485-3236d6710814?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8ZGVzc2VydHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60", "略綠", "2022/01/01", "2022/01/22", 0,1)
        val item4 = Journey("","https://images.unsplash.com/photo-1514517220017-8ce97a34a7b6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fGRlc3NlcnR8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60", "ㄏ黑", "2022/02/01", "2022/02/21", 0,1)
        val item5 = Journey("","https://images.unsplash.com/photo-1558326567-98ae2405596b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTV8fGRlc3NlcnR8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60", "嘶嘶嘶", "2022/03/01", "2022/03/15",0,1)
        val item6 = Journey("","https://images.unsplash.com/photo-1593424718424-cf4d83f3def1?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTh8fGRlc3NlcnR8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60", "天已", "2022/12/01", "2022/12/11", 0,1)
        dataList1.add(item1)
        dataList1.add(item2)
        dataList1.add(item3)
        dataList1.add(item4)
        dataList1.add(item5)
        dataList1.add(item6)

    }
}