package com.kaerushi.monetify.xposed.hooks.files

import android.app.Activity
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.xposed.utils.*

object ApplyMonet {
    @OptIn(LegacyResourcesHook::class)
    fun FilesHooks.applyMonet(activity: Activity) {
        injectColor("primary", "primary_m3", "app_icon_background", "app_icon_background_m3") {
            colorPrimary(activity)
        }
        injectColor("secondary", "secondary_m3") {
            colorPrimaryContainer(activity)
        }
        injectColor("app_background_color", "app_background_color_m3", "background_floating") {
            colorSurface(activity)
        }
        injectColor("hairline") {
            colorOutline(activity)
        }
    }
}