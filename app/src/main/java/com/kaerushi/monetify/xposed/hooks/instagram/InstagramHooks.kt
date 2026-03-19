package com.kaerushi.monetify.xposed.hooks.instagram

import com.kaerushi.monetify.data.INSTAGRAM_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.BaseAppHook
import com.kaerushi.monetify.xposed.utils.PreferenceUtils

object InstagramHooks : BaseAppHook() {
    override val pkgName: String = INSTAGRAM_PACKAGE_NAME
    override fun hookClass() {
        super.hookClass()
        if (PreferenceUtils.getAppMonetEnabled(pkgName)) applyMonetClazz()
    }
}