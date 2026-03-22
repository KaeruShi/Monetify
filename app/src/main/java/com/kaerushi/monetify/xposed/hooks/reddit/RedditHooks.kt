package com.kaerushi.monetify.xposed.hooks.reddit

import android.app.Activity
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.data.REDDIT_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.BaseAppHook
import com.kaerushi.monetify.xposed.utils.PreferenceUtils

@OptIn(LegacyResourcesHook::class)
object RedditHooks : BaseAppHook() {
    override val pkgName: String = REDDIT_PACKAGE_NAME
    override val duotoneDrawables = IconPack.duotoneDrawables
    override fun hookClass() {
        super.hookClass()
        if (PreferenceUtils.getAppDisableAds(pkgName)) disableAds()
        if (PreferenceUtils.getAppMonetEnabled(pkgName)) applyMonetClazz()
    }
    override fun hookOnCreate(instance: Activity) {
        if (PreferenceUtils.getAppMonetEnabled(pkgName)) applyMonetRes(instance)
    }
}