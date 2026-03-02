package com.kaerushi.monetify.xposed.hooks.subslite

import android.app.Activity
import com.kaerushi.monetify.data.SUBSTRATUM_LITE_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.BaseAppHook
import com.kaerushi.monetify.xposed.hooks.subslite.ApplyMonet.applyMonetSubstratumLite
import com.kaerushi.monetify.xposed.utils.PreferenceUtil

object SubstratumLiteHooks : BaseAppHook() {
    override val pkgName: String = SUBSTRATUM_LITE_PACKAGE_NAME
    override val duotoneDrawables: Map<String, Int> = IconPack.duotoneDrawables
    override fun hookOnCreate(instance: Activity) {
        if (PreferenceUtil.getAppMonetEnabled(pkgName)) applyMonetSubstratumLite(instance)
    }
}