package com.kaerushi.monetify.xposed.hooks.instagram

import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.data.INSTAGRAM_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.BaseAppHook
import com.kaerushi.monetify.xposed.utils.PreferenceUtils

class InstagramHooks : BaseAppHook() {
    public override val pkgName: String = INSTAGRAM_PACKAGE_NAME
    @OptIn(LegacyResourcesHook::class)
    override fun hookClass() {
        super.hookClass()
        if (PreferenceUtils.getAppMonetEnabled(pkgName)) applyMonetClazz()
        if (PreferenceUtils.getAppDisableAds(pkgName)) disableAds()
    }
}