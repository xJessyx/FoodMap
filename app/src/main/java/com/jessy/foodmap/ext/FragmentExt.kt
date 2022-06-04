package com.jessy.foodmap.ext

import androidx.fragment.app.Fragment
import com.jessy.foodmap.PublisherApplication
import com.jessy.foodmap.factory.ViewModelFactory

fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as PublisherApplication).repository
    return ViewModelFactory(repository)
}
