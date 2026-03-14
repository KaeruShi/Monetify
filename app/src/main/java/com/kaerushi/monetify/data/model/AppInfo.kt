package com.kaerushi.monetify.data.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val name: String,
    val packageName: String,
    val summary: String,
    val icon: Drawable?,
    val altIcon: Int,
    val enableMonet: Boolean = false,
    val disableAds: Boolean = false,
    val iconPack: String = "",
    val enabled: Boolean = true
)
