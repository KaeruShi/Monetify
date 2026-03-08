package com.kaerushi.monetify.xposed.hooks.files

import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.data.FILES_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.BaseAppHook
import com.kaerushi.monetify.xposed.utils.PreferenceUtil.getAppMonetEnabled

object FilesHooks : BaseAppHook() {
    override val pkgName = FILES_PACKAGE_NAME
    override val duotoneDrawables = IconPack.duotoneDrawables
    @OptIn(LegacyResourcesHook::class)
    override fun hookClass() {
        if (getAppMonetEnabled(pkgName)) {
            resources().hook {
                injectResource {
                    conditions {
                        name = "force_material3"
                        bool()
                    }
                    replaceToTrue()
                }
            }
        }
    }
}