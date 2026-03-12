package com.kaerushi.monetify.xposed.hooks.twitter

import android.app.Activity
import com.kaerushi.monetify.data.X_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.BaseAppHook
import com.kaerushi.monetify.xposed.hooks.twitter.ApplyMonet.applyMonet
import com.kaerushi.monetify.xposed.utils.PreferenceUtil

object TwitterHooks : BaseAppHook() {
    override val pkgName: String = X_PACKAGE_NAME
    override val duotoneDrawables: Map<String, Int> = IconPack.duotoneDrawables
    override fun hookOnCreate(instance: Activity) {
        if (PreferenceUtil.getAppMonetEnabled(pkgName)) applyMonet(instance)
    }
}