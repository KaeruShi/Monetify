package com.kaerushi.monetify.xposed.hooks.reddit

import android.app.Activity
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.xposed.utils.ColorUtils

object ApplyMonet {
    @OptIn(LegacyResourcesHook::class)
    fun RedditHooks.applyMonetReddit(activity: Activity) {
        injectColor("alienblue_secondary", "alienblue_tone1", "alienblue_primary", "night_canvas",
            "alienblue_admin", "alienblue_live", "alienblue_upvote", "anonymousbrowsing_admin",
            "anonymousbrowsing_live", "anonymousbrowsing_upvote", "ds_primitive_orangered_500", "mint_admin",
            "mint_live", "mint_upvote", "palette_red", "pony_admin", "pony_live", "pony_upvote", "rdt_orangered",
            "rdt_orangered_new", "trees_admin", "trees_live", "trees_upvote", "rw_rdt_orangered") {
            ColorUtils.colorPrimaryLight(activity)
        }
        injectColor("white", "alienblue_canvas", "alienblue_tone6", "alienblue_tone8",
            "mint_canvas", "mint_tone6", "ds_primitive_narwhal_100") {
            ColorUtils.colorSurfaceLight(activity)
        }

        // Dark
        injectColor("night_primary", "night_secondary", "night_tone1", "night_tone2", "midnight_admin",
            "midnight_live", "midnight_upvote", "night_admin", "night_live", "night_upvote") {
            ColorUtils.colorPrimaryDark(activity)
        }
        injectColor("night_canvas") {
            ColorUtils.colorSurfaceContainerDark(activity)
        }
        injectColor("midnight_tone6", "midnight_tone7", "midnight_tone8", "midnight_canvas") {
            ColorUtils.colorSurfaceDark(activity)
        }
    }
}