package com.jessy.foodmap

import android.icu.text.SimpleDateFormat
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().build()
        Glide.with(imgView.context)
            .load(imgUri)
//            .centerCrop()
            .disallowHardwareConfig()

            .apply(
                RequestOptions()
                    .placeholder(R.drawable.load)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }


}
//
//@BindingAdapter("time")
//
//fun StampToTime(textView: TextView,time: Long, locale: Locale) {
//    // 進來的time以秒為單位，Date輸入為毫秒為單位，要注意
//
//    val simpleDateFormat = SimpleDateFormat("HH:mm", locale)
//
//    textView.text = simpleDateFormat.format(Date(time))
//
//}