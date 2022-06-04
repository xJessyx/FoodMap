package com.jessy.foodmap.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.jessy.foodmap.data.source.DefaultPublisherRepository
import com.jessy.foodmap.data.source.PublisherDataSource
import com.jessy.foodmap.data.source.PublisherRepository
import com.jessy.foodmap.data.source.local.PublisherLocalDataSource
import com.jessy.foodmap.data.source.remote.PublisherRemoteDataSource

object ServiceLocator {
    @Volatile
    var repository: PublisherRepository? = null
        @VisibleForTesting set

    fun provideRepository(context: Context): PublisherRepository {
        synchronized(this) {
            return repository
                ?: repository
                ?: createPublisherRepository(context)
        }
    }

    private fun createPublisherRepository(context: Context): PublisherRepository {
        return DefaultPublisherRepository(
            PublisherRemoteDataSource,
            createLocalDataSource(context)
        )
    }

    private fun createLocalDataSource(context: Context): PublisherDataSource {
        return PublisherLocalDataSource(context)
    }
}