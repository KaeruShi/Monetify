package com.kaerushi.monetify.xposed.hooks.github

import android.app.Activity
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.data.GITHUB_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.BaseAppHook
import com.kaerushi.monetify.xposed.utils.PreferenceUtils

object GitHubHooks : BaseAppHook() {
    override val pkgName: String = GITHUB_PACKAGE_NAME
    override val duotoneDrawables: Map<String, Int> = IconPack.duotoneDrawables

    @LegacyResourcesHook
    override fun hookOnCreate(instance: Activity) {
        if (PreferenceUtils.getAppMonetEnabled(pkgName)) applyMonetRes(instance)
    }

    @LegacyResourcesHook
    override fun hookClass() {
        super.hookClass()
        if (PreferenceUtils.getAppMonetEnabled(pkgName)) {
            applyMonetClazz()
            applyMonetLayout()
        }
    }
}