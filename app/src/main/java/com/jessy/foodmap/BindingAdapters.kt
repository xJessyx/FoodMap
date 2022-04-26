package com.jessy.foodmap

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().build()
        Glide.with(imgView.context)
            .load(imgUri)
            .centerCrop()
            .disallowHardwareConfig()

            .apply(
                RequestOptions()
                    .placeholder(R.drawable.load)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}
