package com.kaerushi.monetify.xposed.hooks

import android.app.Activity
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.R
import com.kaerushi.monetify.data.SUBSTRATUM_LITE_PACKAGE_NAME
import com.kaerushi.monetify.xposed.utils.ColorUtils.colorPrimary
import com.kaerushi.monetify.xposed.utils.ColorUtils.colorSurface
import com.kaerushi.monetify.xposed.utils.ColorUtils.colorSurfaceContainer
import com.kaerushi.monetify.xposed.utils.PreferenceUtil.getAppMonetEnabled

@OptIn(LegacyResourcesHook::class)
object SubstratumLiteHooks : BaseAppHook() {
    override val pkgName: String = SUBSTRATUM_LITE_PACKAGE_NAME
    override val duotoneDrawables: Map<String, Int> = mapOf(
        "ic_manager_info" to R.drawable.duotone_info,
        "nav_drawer_themes" to R.drawable.duoltone_starfilled,
        "nav_drawer_priorities" to R.drawable.duotone_pin,
        "nav_drawer_manager" to R.drawable.duotone_home
    )

    override fun hookOnCreate(instance: Activity) {
        if (getAppMonetEnabled(pkgName)) applyMonetMainActivity(instance)
    }

    private fun applyMonetMainActivity(activity: Activity) {
        injectColor("primary", "primary_variant", "secondary", "secondary_variant") {
            colorPrimary(activity)
        }
        injectColor("surface", "transparent", "background_material_light", "background_floating_material_light") {
            colorSurfaceContainer(activity)
        }
        injectColor("background") {
            colorSurface(activity)
        }
    }
}