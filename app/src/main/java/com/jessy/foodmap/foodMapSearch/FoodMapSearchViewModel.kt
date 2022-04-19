package com.jessy.foodmap.foodMapSearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jessy.foodmap.data.StoreInformation

class FoodMapSearchViewModel : ViewModel(){
    val viewmodelDataList = mutableListOf<StoreInformation>()

    val getSuggestionsList = MutableLiveData<List<StoreInformation>>()

    val _getSuggestionsList: LiveData<List<StoreInformation>>
        get() = _getSuggestionsList


//--------------------------------------------------------------------------------------------------



}