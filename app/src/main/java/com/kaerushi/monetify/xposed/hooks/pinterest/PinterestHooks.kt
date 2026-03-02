package com.kaerushi.monetify.xposed.hooks.pinterest

import com.kaerushi.monetify.data.PINTEREST_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.BaseAppHook

object PinterestHooks : BaseAppHook() {
    override val pkgName: String = PINTEREST_PACKAGE_NAME
    override val duotoneDrawables: Map<String, Int> = IconPack.duotoneDrawables
}