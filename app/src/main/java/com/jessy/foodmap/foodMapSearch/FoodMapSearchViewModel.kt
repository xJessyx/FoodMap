package com.jessy.foodmap.foodMapSearch

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.*
import com.jessy.foodmap.BuildConfig
import com.jessy.foodmap.R
import com.jessy.foodmap.data.StoreInformation

class FoodMapSearchViewModel : ViewModel(){

    val viewmodelDataList = mutableListOf<StoreInformation>()

    val getSuggestionsList = MutableLiveData<List<StoreInformation>>()

    val _getSuggestionsList: LiveData<List<StoreInformation>>
        get() = _getSuggestionsList

//

    //--------------------------------------------------------------------------------------------------




//--------------------------------------------------------------------------------------------------

}