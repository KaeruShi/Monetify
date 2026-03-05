package com.kaerushi.monetify.xposed.hooks.files

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.kaerushi.monetify.data.FILES_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.BaseAppHook
import com.kaerushi.monetify.xposed.hooks.files.ApplyMonet.applyMonet
import com.kaerushi.monetify.xposed.utils.ColorUtils
import com.kaerushi.monetify.xposed.utils.PreferenceUtil.getAppMonetEnabled
import androidx.core.graphics.drawable.toDrawable

object FilesHooks : BaseAppHook() {
    override val pkgName = FILES_PACKAGE_NAME
    override val duotoneDrawables = IconPack.duotoneDrawables
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