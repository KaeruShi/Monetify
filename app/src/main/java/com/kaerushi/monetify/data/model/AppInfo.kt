package com.kaerushi.monetify.data.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val name: String,
    val packageName: String,
    val summary: String,
    val icon: Drawable?,
    val altIcon: Int,
    val enabled: Boolean = true
)
