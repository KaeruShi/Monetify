package com.kaerushi.monetify.xposed.hooks.youtube

import android.app.Activity
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.xposed.hooks.pinterest.PinterestHooks.injectColor
import com.kaerushi.monetify.xposed.utils.*

object ApplyMonet {
    @OptIn(LegacyResourcesHook::class)
    fun YoutubeHooks.applyMonet(activity: Activity) {
        injectColor(
            "yt_white1_opacity25", "yt_white1_opacity60",
            "yt_ref_color_constants_baseline_white_white1",
            "yt_ref_color_constants_baseline_white_white2",
            "yt_ref_color_constants_baseline_white_white3",
            "yt_ref_color_constants_baseline_white_white4",
            "yt_ref_color_constants_baseline_transparent_white1_alpha0",
            "yt_ref_color_constants_baseline_transparent_white1_alpha10",
            "yt_ref_color_constants_baseline_transparent_white1_alpha20",
            "yt_ref_color_constants_baseline_transparent_white1_alpha30",
            "yt_ref_color_constants_baseline_transparent_white1_alpha70",
            "yt_ref_color_constants_baseline_transparent_white1_alpha90",
            "yt_ref_color_constants_baseline_transparent_white1_alpha95",
            "yt_ref_color_constants_baseline_transparent_white1_alpha98"
        ) {
            colorSurfaceLight(activity)
        }
        injectColor("yt_ref_color_constants_baseline_black_black1", "yt_ref_color_constants_baseline_black_black2", "yt_black_pure") {
            colorSurfaceDark(activity)
        }
        injectColor("yt_ref_color_constants_expressive_red_red50", "yt_youtube_red",
//            "yt_ref_color_constants_baseline_black_black3",
            "yt_ref_color_constants_baseline_blue_darkblue") {
            colorPrimary(activity)
        }
    }
}