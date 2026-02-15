package com.kaerushi.monetify.xposed.hooks

import android.graphics.Color
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.log.YLog
import com.kaerushi.monetify.R
import com.kaerushi.monetify.data.SUBSTRATUM_LITE_PACKAGE_NAME
import com.kaerushi.monetify.data.viewmodel.AppIconPack
import com.kaerushi.monetify.xposed.utils.PreferenceUtil.getAppIconPack

object SubstratumLiteHooks : YukiBaseHooker() {
    @LegacyResourcesHook
    override fun onHook() {
        val pkg = SUBSTRATUM_LITE_PACKAGE_NAME
        loadApp(pkg) {
            val duotoneDrawables = mapOf(
                "ic_appintro_arrow" to R.drawable.duotone_arrow_back,
                "ic_manager_info" to R.drawable.duotone_info,
                "nav_drawer_themes" to R.drawable.duoltone_starfilled,
                "nav_drawer_priorities" to R.drawable.duotone_pin,
                "nav_drawer_manager" to R.drawable.duotone_home
            )

            val iconPack = when (getAppIconPack(pkg)) {
                AppIconPack.DEFAULT.name -> null
                AppIconPack.DUOTONE.name -> duotoneDrawables
                else -> null
            }

            resources().hook {
                iconPack?.forEach { (resourceName, replacementDrawable) ->
                    injectResource {
                        conditions {
                            name = resourceName
                            drawable()
                        }
                        replaceToModuleResource(replacementDrawable)
                    }
                }
            }
        }
    }
}