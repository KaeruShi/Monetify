package com.kaerushi.monetify.xposed.hooks

import com.kaerushi.monetify.R
import com.kaerushi.monetify.data.SUBSTRATUM_LITE_PACKAGE_NAME

object SubstratumLiteHooks : BaseAppHook() {
    override val pkgName: String = SUBSTRATUM_LITE_PACKAGE_NAME
    override val duotoneDrawables: Map<String, Int> = mapOf(
        "ic_appintro_arrow" to R.drawable.duotone_arrow_back,
        "ic_manager_info" to R.drawable.duotone_info,
        "nav_drawer_themes" to R.drawable.duoltone_starfilled,
        "nav_drawer_priorities" to R.drawable.duotone_pin,
        "nav_drawer_manager" to R.drawable.duotone_home
    )
}