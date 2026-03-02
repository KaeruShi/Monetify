package com.kaerushi.monetify.xposed.hooks.subslite

import android.app.Activity
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.xposed.utils.ColorUtils

object ApplyMonet {
    @OptIn(LegacyResourcesHook::class)
    fun SubstratumLiteHooks.applyMonetSubstratumLite(activity: Activity) {
        injectColor("primary", "primary_variant", "secondary", "secondary_variant") {
            ColorUtils.colorPrimary(activity)
        }
        injectColor("surface", "transparent", "background_material_light", "background_floating_material_light") {
            ColorUtils.colorSurfaceContainer(activity)
        }
        injectColor("background") {
            ColorUtils.colorSurface(activity)
        }
    }
}