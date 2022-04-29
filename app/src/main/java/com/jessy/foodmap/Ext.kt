package com.jessy.foodmap

import android.icu.text.SimpleDateFormat
import java.util.*

fun Long.toDisplayFormat(): String {
    return SimpleDateFormat("yyyy年MM月dd日 hh:mm", Locale.TAIWAN).format(this)
}