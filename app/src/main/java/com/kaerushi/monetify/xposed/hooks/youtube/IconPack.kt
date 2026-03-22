package com.kaerushi.monetify.xposed.hooks.youtube

import com.kaerushi.monetify.R
import com.kaerushi.monetify.xposed.hooks.DrawableReplacement

object IconPack {
    val duotoneDrawables = mapOf(
        "yt_outline_experimental_home_vd_theme_24" to DrawableReplacement(R.drawable.duotone_home),
        "yt_fill_experimental_home_vd_theme_24" to DrawableReplacement(R.drawable.duotone_home_fill),
        "yt_outline_experimental_bell_vd_theme_24" to DrawableReplacement(R.drawable.duotone_notification),
        "yt_fill_experimental_bell_vd_theme_24" to DrawableReplacement(R.drawable.duotone_notification_fill),
        "yt_outline_experimental_search_vd_theme_24" to DrawableReplacement(R.drawable.duotone_search),
        "yt_outline_experimental_overflow_vertical_vd_theme_24" to DrawableReplacement(R.drawable.duotone_more_vert_24),
        "yt_outline_experimental_overflow_vertical_vd_theme_36" to DrawableReplacement(R.drawable.duotone_more_vert_24),
        "yt_outline_gear_vd_theme_24" to DrawableReplacement(R.drawable.duotone_settings),
        "yt_outline_experimental_gear_vd_theme_24" to DrawableReplacement(R.drawable.duotone_settings),
    )
}