package com.kaerushi.monetify.xposed.hooks

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.log.YLog
import com.kaerushi.monetify.data.viewmodel.AppIconPack
import com.kaerushi.monetify.xposed.MainHook
import com.kaerushi.monetify.xposed.utils.HookStatusUtil
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
        Activity::class.java.resolve().firstMethod { name = "onCreate"; parameters(Bundle::class.java) }.hook {
            after {
                val instance = instance<Activity>()
                if (HookStatusUtil.shouldSend(pkgName)) {
                    HookStatusUtil.sendHooked(instance, pkgName, true)
                }
                hookOnCreate(instance)
            }
        }
    }

    protected open fun hookOnCreate(instance: Activity) {}

    private fun getIconPackDrawables(): Map<String, Int>? {
        return when (PreferenceUtil.getAppIconPack(packageName)) {
            AppIconPack.DEFAULT.name -> null
            AppIconPack.DUOTONE.name -> duotoneDrawables
            else -> null
        }
    }

    @LegacyResourcesHook
    fun injectColor(
        vararg colorNames: String,
        provider: () -> Int
    ) {
        resources().hook {
            colorNames.forEach { name ->
                injectResource {
                    conditions {
                        this.name = name
                        color()
                    }
                    replaceTo { provider() }
                }
            }
        }
    }

}