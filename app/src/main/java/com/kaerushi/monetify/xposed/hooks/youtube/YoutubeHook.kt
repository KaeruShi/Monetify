package com.kaerushi.monetify.xposed.hooks.youtube

import android.app.Activity
import com.kaerushi.monetify.data.YOUTUBE_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.BaseAppHook
import com.kaerushi.monetify.xposed.utils.PreferenceUtils

object YoutubeHooks : BaseAppHook() {
    override val pkgName = YOUTUBE_PACKAGE_NAME
    override val duotoneDrawables: Map<String, Int> = IconPack.duotoneDrawables
    override fun hookOnCreate(instance: Activity) {
        if (PreferenceUtils.getAppMonetEnabled(pkgName)) {
            applyMonetRes(instance)
        }
    }

    override fun hookClass() {
        super.hookClass()
        if (PreferenceUtils.getAppMonetEnabled(pkgName)) {
            applyMonetLayout()
        }
    }
}