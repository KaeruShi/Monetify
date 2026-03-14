@file:OptIn(LegacyResourcesHook::class)

package com.kaerushi.monetify.xposed.hooks.youtube

import android.app.Activity
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.xposed.utils.colorPrimary
import com.kaerushi.monetify.xposed.utils.colorPrimaryLight
import com.kaerushi.monetify.xposed.utils.colorSurface
import com.kaerushi.monetify.xposed.utils.colorSurfaceDark
import com.kaerushi.monetify.xposed.utils.colorSurfaceLight
import com.kaerushi.monetify.xposed.utils.isNightMode

fun YoutubeHooks.applyMonetRes(activity: Activity) {
    injectColor(
        "yt_white1_opacity25", "yt_white1_opacity60",
        "yt_ref_color_constants_baseline_white_white1",
        "yt_ref_color_constants_baseline_white_white2",
        "yt_ref_color_constants_baseline_white_white3",
        "yt_ref_color_constants_baseline_white_white4",
    ) {
        colorSurfaceLight(activity)
    }

    // Dark
    injectColor(
        "yt_ref_color_constants_baseline_black_black0",
        "yt_ref_color_constants_baseline_black_black1",
        "yt_black_pure"
    ) {
        colorSurfaceDark(activity)
    }
    injectColor("lc_button_style_primary_background", "yt_ref_color_constants_baseline_blue_lightblue") {
        if (!isNightMode(activity)) colorPrimary(activity) else colorPrimaryLight(activity)
    }
    injectColor("yt_ref_color_constants_baseline_black_black3", "yt_ref_color_constants_baseline_opaque_black3_overlay10") {
        colorSurfaceDark(activity)
    }
}

fun YoutubeHooks.applyMonetLayout() {
    hookLayout("appbar_layout") {
        val target = findView("toolbar_container")
        target.apply {
            setBackgroundColor(colorSurface(appContext!!))
        }
    }
    hookLayout("pane_fragment_with_top_bar") {
        val target = findView("pane_fragment_contents")
        target.apply {
            setBackgroundColor(colorSurface(appContext!!))
        }
    }
    hookLayout("watch_panel") {
        val target = findView("watch_list")
        target.apply {
            setBackgroundColor(colorSurface(appContext!!))
        }
    }
    hookLayout("constraint_default_engagement_panel") {
        val content = findView("content")
        val footer = findView("footer")
        content.apply {
            setBackgroundColor(colorSurface(appContext!!))
        }
        footer.apply {
            setBackgroundColor(colorSurface(appContext!!))
        }
    }
}