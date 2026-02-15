package com.kaerushi.monetify.xposed.hooks

import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.kaerushi.monetify.R
import com.kaerushi.monetify.data.PINTEREST_PACKAGE_NAME
import com.kaerushi.monetify.data.viewmodel.AppIconPack
import com.kaerushi.monetify.xposed.utils.PreferenceUtil.getAppIconPack

object PinterestHooks : YukiBaseHooker() {
    @LegacyResourcesHook
    override fun onHook() {
        loadApp(PINTEREST_PACKAGE_NAME) {
            val duotoneDrawables = mapOf(
                "ic_vr_home_gestalt" to R.drawable.duotone_home,
                "ic_vr_home_fill_gestalt" to R.drawable.duotone_home,
                "ic_vr_search_gestalt" to R.drawable.duotone_search,
                "ic_vr_search_fill_gestalt" to R.drawable.duotone_search,
                "ic_vr_add_gestalt" to R.drawable.duotone_plus,
                "ic_vr_speech_ellipsis_gestalt" to R.drawable.duotone_chat,
                "ic_vr_speech_ellipsis_fill_gestalt" to R.drawable.duotone_chat,
                "ic_vr_camera_gestalt" to R.drawable.duotone_camera,
                "ic_vr_pin_gestalt" to R.drawable.duotone_pin_vert,
                "ic_vr_angled_pin_gestalt" to R.drawable.duotone_pin,
                "ic_vr_cancel_gestalt" to R.drawable.duotone_cross,
                "ic_vr_android_share_gestalt" to R.drawable.duotone_share,
                "ic_vr_download_gestalt" to R.drawable.duotone_download,
                "ic_vr_report_gestalt" to R.drawable.duotone_report,
                "ic_vr_heart_outline_gestalt" to R.drawable.duotone_heart,
                "ic_vr_ellipsis_gestalt" to R.drawable.duotone_more
            )
            val iconPack = when (getAppIconPack(PINTEREST_PACKAGE_NAME)) {
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