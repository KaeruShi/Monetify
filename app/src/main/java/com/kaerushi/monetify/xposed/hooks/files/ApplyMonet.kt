package com.kaerushi.monetify.xposed.hooks.files

import android.app.Activity
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.xposed.utils.ColorUtils

object ApplyMonet {
    @OptIn(LegacyResourcesHook::class)
    fun FilesHooks.applyMonet(activity: Activity) {
        injectColor("primary", "primary_m3", "app_icon_background", "app_icon_background_m3") {
            ColorUtils.colorPrimary(activity)
        }
        injectColor("secondary", "secondary_m3") {
            ColorUtils.colorPrimaryContainer(activity)
        }
        injectColor("app_background_color", "app_background_color_m3", "background_floating") {
            ColorUtils.colorSurface(activity)
        }
        injectColor("hairline") {
            ColorUtils.colorOutline(activity)
        }
    }
}