package com.jessy.foodmap.addPlace

import android.R

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class AddPlaceViewModel : ViewModel() {

   var journeySinner = MutableLiveData<String>()
    var daySinner = MutableLiveData<String>()
    var transportationSinner = MutableLiveData<String>()
    var dwellTime = MutableLiveData<String>()

}

