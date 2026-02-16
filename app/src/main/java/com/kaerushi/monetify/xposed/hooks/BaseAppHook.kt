package com.kaerushi.monetify.xposed.hooks

import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.log.YLog
import com.kaerushi.monetify.data.viewmodel.AppIconPack
import com.kaerushi.monetify.xposed.MainHook
import com.kaerushi.monetify.xposed.utils.PreferenceUtil

abstract class BaseAppHook : YukiBaseHooker() {
    protected abstract val pkgName: String
    protected abstract val duotoneDrawables: Map<String, Int>

    @LegacyResourcesHook
    override fun onHook() {
        loadApp(pkgName) {
            getIconPackDrawables()?.let { drawables ->
                resources().hook {
                    drawables.forEach { (resName, replacementDrawable) ->
                        injectResource {
                            conditions {
                                name = resName
                                drawable()
                            }
                            replaceToModuleResource(replacementDrawable)
                        }
                    }
                }
            }
            hookClass()
        }
    }

    protected open fun hookClass() {
        if (!MainHook.dexKitLoaded) {
            YLog.error("DexKit not loaded")
            return
        }
    }

    private fun getIconPackDrawables(): Map<String, Int>? {
        return when (PreferenceUtil.getAppIconPack(packageName)) {
            AppIconPack.DEFAULT.name -> null
            AppIconPack.DUOTONE.name -> duotoneDrawables
            else -> null
        }
    }
}