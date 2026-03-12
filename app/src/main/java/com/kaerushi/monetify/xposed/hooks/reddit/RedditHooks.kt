package com.kaerushi.monetify.xposed.hooks.reddit

import android.app.Activity
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.data.REDDIT_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.BaseAppHook
import com.kaerushi.monetify.xposed.utils.PreferenceUtil

@OptIn(LegacyResourcesHook::class)
object RedditHooks : BaseAppHook() {
    override val pkgName: String = REDDIT_PACKAGE_NAME
    override val duotoneDrawables: Map<String, Int> = IconPack.duotoneDrawables
    override fun hookClass() {
        super.hookClass()
        if (PreferenceUtil.getAppDisableAds(pkgName)) disableAds()
        if (PreferenceUtil.getAppMonetEnabled(pkgName)) applyMonetClazz()
    }
    override fun hookOnCreate(instance: Activity) {
        if (PreferenceUtil.getAppMonetEnabled(pkgName)) applyMonetRes(instance)
    }
}