package com.kaerushi.monetify.xposed.hooks.twitter

import android.app.Activity
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.xposed.utils.ColorUtils
import com.kaerushi.monetify.xposed.utils.Utils

object ApplyMonet {
    @OptIn(LegacyResourcesHook::class)
    fun TwitterHooks.applyMonetTwitter(activity: Activity) {
        injectColor(
            "twitter_blue_opacity_30",
            "twitter_blue_opacity_50",
            "twitter_blue_opacity_58",
            "blue_500",
            "twitter_blue",
            "text_black"
        ) {
            ColorUtils.colorPrimary(activity)
        }
        injectColor("appBackground", "ps__black") {
            ColorUtils.colorSurface(activity)
        }
        injectColor("black") {
            if (Utils.isNightMode(activity)) ColorUtils.colorSurfaceContainerDark(activity) else ColorUtils.colorSurfaceContainerLight(
                activity
            )
        }
        if (!Utils.isNightMode(activity))
            injectColor("white") {
                ColorUtils.colorSurfaceContainer(activity)
            }
    }
}