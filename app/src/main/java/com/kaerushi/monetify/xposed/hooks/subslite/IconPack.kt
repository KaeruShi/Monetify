package com.kaerushi.monetify.xposed.hooks.subslite

import com.kaerushi.monetify.R
import com.kaerushi.monetify.xposed.hooks.DrawableReplacement

object IconPack {
    val duotoneDrawables = mapOf(
        "ic_manager_info" to DrawableReplacement(R.drawable.duotone_info),
        "nav_drawer_themes" to DrawableReplacement(R.drawable.duotone_starfilled),
        "nav_drawer_priorities" to DrawableReplacement(R.drawable.duotone_pin),
        "nav_drawer_manager" to DrawableReplacement(R.drawable.duotone_home)
    )
}