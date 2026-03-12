package com.kaerushi.monetify.xposed.hooks.github

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.highcapable.yukihookapi.hook.log.YLog
import com.kaerushi.monetify.R
import com.kaerushi.monetify.data.GITHUB_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.BaseAppHook
import com.kaerushi.monetify.xposed.utils.PreferenceUtil
import com.kaerushi.monetify.xposed.utils.colorOnSurface
import com.kaerushi.monetify.xposed.utils.colorSurface

object GitHubHooks : BaseAppHook() {
    override val pkgName: String = GITHUB_PACKAGE_NAME
    override val duotoneDrawables: Map<String, Int> = IconPack.duotoneDrawables

    @LegacyResourcesHook
    override fun hookOnCreate(instance: Activity) {
        if (PreferenceUtil.getAppMonetEnabled(pkgName)) applyMonetRes(instance)
    }

    @LegacyResourcesHook
    override fun hookClass() {
        super.hookClass()
        if (PreferenceUtil.getAppMonetEnabled(pkgName)) {
            applyMonetClazz()
            applyMonetLayout()
        }
    }
}