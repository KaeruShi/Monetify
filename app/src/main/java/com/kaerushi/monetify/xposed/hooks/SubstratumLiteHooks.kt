package com.kaerushi.monetify.xposed.hooks

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.R
import com.kaerushi.monetify.data.SUBSTRATUM_LITE_PACKAGE_NAME
import com.highcapable.yukihookapi.hook.log.YLog
import com.kaerushi.monetify.xposed.utils.ColorUtils.colorPrimary
import com.kaerushi.monetify.xposed.utils.ColorUtils.colorSurface
import com.kaerushi.monetify.xposed.utils.ColorUtils.colorSurfaceContainer
import com.kaerushi.monetify.xposed.utils.PreferenceUtil.getAppMonetEnabled

@OptIn(LegacyResourcesHook::class)
object SubstratumLiteHooks : BaseAppHook() {
    override val pkgName: String = SUBSTRATUM_LITE_PACKAGE_NAME
    override val duotoneDrawables: Map<String, Int> = mapOf(
        "ic_appintro_arrow" to R.drawable.duotone_arrow_back,
        "ic_manager_info" to R.drawable.duotone_info,
        "nav_drawer_themes" to R.drawable.duoltone_starfilled,
        "nav_drawer_priorities" to R.drawable.duotone_pin,
        "nav_drawer_manager" to R.drawable.duotone_home
    )

    override fun hookOnCreate(instance: Activity) {
        if (getAppMonetEnabled(pkgName)) applyMonetMainActivity(instance)
    }

    private fun applyMonetMainActivity(activity: Activity) {
        injectColor("primary", "secondary") {
            colorPrimary(activity)
        }
        injectColor("surface", "transparent", "background_material_light", "appintro_statusbar_color", "background_floating_material_light") {
            colorSurfaceContainer(activity)
        }
        injectColor("background") {
            colorSurface(activity)
        }
    }
}