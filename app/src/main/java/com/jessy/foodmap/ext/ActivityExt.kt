package com.jessy.foodmap.ext

import android.app.Activity
import android.view.Gravity
import android.widget.Toast
import com.jessy.foodmap.PublisherApplication
import com.jessy.foodmap.factory.ViewModelFactory

fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as PublisherApplication).repository
    return ViewModelFactory(repository)
}

fun Activity?.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}