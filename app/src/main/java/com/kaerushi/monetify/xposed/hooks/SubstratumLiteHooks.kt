package com.kaerushi.monetify.xposed.hooks

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.kaerushi.monetify.xposed.utils.HookStatusUtil
import com.kaerushi.monetify.xposed.utils.HookStatusUtil.shouldSend
import com.kaerushi.monetify.xposed.utils.PreferenceUtil
import androidx.core.graphics.toColorInt
import com.highcapable.kavaref.extension.toClass
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.type.android.ContextClass
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.kaerushi.monetify.R
import com.kaerushi.monetify.data.SUBSTRATUM_LITE_PACKAGE_NAME

object SubstratumLiteHooks : YukiBaseHooker() {
    override fun onHook() {
        resources().hook {
            injectResource {
                conditions {
                    name = "ic_launcher"
                    mipmap()
                }
                replaceToModuleResource(R.drawable.github)
            }
        }
    }
}