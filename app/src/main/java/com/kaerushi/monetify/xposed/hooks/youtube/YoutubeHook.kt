package com.kaerushi.monetify.xposed.hooks.youtube

import android.app.Activity
import android.view.ViewGroup
import com.highcapable.yukihookapi.hook.log.YLog
import com.kaerushi.monetify.data.YOUTUBE_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.BaseAppHook
import com.kaerushi.monetify.xposed.utils.PreferenceUtil
import com.kaerushi.monetify.xposed.utils.colorSurface

object YoutubeHooks : BaseAppHook() {
    override val pkgName = YOUTUBE_PACKAGE_NAME
    override val duotoneDrawables: Map<String, Int> = IconPack.duotoneDrawables
    override fun hookOnCreate(instance: Activity) {
        if (PreferenceUtil.getAppMonetEnabled(pkgName)) {
            applyMonet(instance)
            val root = instance.findViewById<ViewGroup>(android.R.id.content)
            root.setBackgroundColor(colorSurface(instance)).also {
                YLog.debug("Monetify: $pkgName background color applied")
            }
        }
    }
}