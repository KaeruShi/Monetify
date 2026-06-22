package com.kaerushi.monetify.xposed.hooks.android

import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.kaerushi.monetify.data.model.preferences.AppIconPack
import com.kaerushi.monetify.xposed.utils.PreferenceUtils

class FrameworkHook : YukiBaseHooker() {
    @OptIn(LegacyResourcesHook::class)
    override fun onHook() {
        loadSystem {
            val getIconPack = when (PreferenceUtils.getAppIconPack("android")) {
                AppIconPack.DUOTONE.name -> IconPack.duotoneDrawables
                else -> return@loadSystem
            }
            getIconPack.forEach { (name, replacement) ->
                resources().hook {
                    injectResource {
                        conditions {
                            this.name = name
                            drawable()
                        }
                        replaceToModuleResource(replacement.resId)
                    }
                }
            }
        }
    }
}