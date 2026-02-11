package com.kaerushi.monetify.xposed.hooks

import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.kaerushi.monetify.R

object SubstratumLiteHooks : YukiBaseHooker() {
    @LegacyResourcesHook
    override fun onHook() {
        resources().hook {
            injectResource {
                conditions {
                    name = "nav_drawer_themes"
                    drawable()
                }
                replaceToModuleResource(R.drawable.telegram)
            }
        }
    }
}