package com.kaerushi.monetify.xposed.hooks.youtube

import android.annotation.SuppressLint
import android.app.Activity
import com.kaerushi.monetify.data.YOUTUBE_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.BaseAppHook
import com.kaerushi.monetify.xposed.utils.PreferenceUtil

object YoutubeHooks : BaseAppHook() {
    override val pkgName = YOUTUBE_PACKAGE_NAME
    override val duotoneDrawables: Map<String, Int> = IconPack.duotoneDrawables
    override fun hookOnCreate(instance: Activity) {
        if (PreferenceUtil.getAppMonetEnabled(pkgName)) {
            applyMonetRes(instance)
        }
    }

    override fun hookClass() {
        super.hookClass()
        if (PreferenceUtil.getAppMonetEnabled(pkgName)) {
            applyMonetLayout()
        }
    }
}