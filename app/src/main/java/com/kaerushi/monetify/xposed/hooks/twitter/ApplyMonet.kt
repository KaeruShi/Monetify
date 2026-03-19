package com.kaerushi.monetify.xposed.hooks.twitter

import android.app.Activity
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.xposed.extensions.isNightMode
import com.kaerushi.monetify.xposed.utils.*

@OptIn(LegacyResourcesHook::class)
fun TwitterHooks.applyMonet(activity: Activity) {
    injectColor(
        "twitter_blue_opacity_30",
        "twitter_blue_opacity_50",
        "twitter_blue_opacity_58",
        "blue_500",
        "twitter_blue",
        "text_black"
    ) {
        colorPrimary(activity)
    }
    injectColor("appBackground", "ps__black") {
        colorSurface(activity)
    }
    injectColor("black") {
        if (isNightMode(activity)) colorSurfaceContainerDark(activity) else colorSurfaceContainerLight(
            activity
        )
    }
    if (isNightMode(activity))
        injectColor("white") {
            colorSurfaceContainer(activity)
        }
}
