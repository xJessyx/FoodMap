package com.jessy.foodmap

import android.app.Application
import com.jessy.foodmap.data.source.PublisherRepository
import com.jessy.foodmap.util.ServiceLocator
import kotlin.properties.Delegates

class PublisherApplication : Application() {

    val repository: PublisherRepository
        get() = ServiceLocator.provideRepository(this)

    companion object {
        var instance: PublisherApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun isLiveDataDesign() = true
}