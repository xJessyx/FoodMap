package com.jessy.foodmap.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jessy.foodmap.data.source.PublisherRepository
import com.jessy.foodmap.home.HomeViewModel
import com.jessy.foodmap.member.paging.MapsPagingViewModel

class ViewModelFactory  constructor(
    private val repository: PublisherRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(repository)

                isAssignableFrom(MapsPagingViewModel::class.java) ->
                    MapsPagingViewModel(repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}