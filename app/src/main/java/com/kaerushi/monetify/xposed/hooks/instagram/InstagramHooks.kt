package com.kaerushi.monetify.xposed.hooks.instagram

import android.app.Activity
import android.view.ViewGroup
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.data.INSTAGRAM_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.BaseAppHook
import com.kaerushi.monetify.xposed.utils.PreferenceUtil
import com.kaerushi.monetify.xposed.utils.colorSurface

object InstagramHooks : BaseAppHook() {
    override val pkgName: String = INSTAGRAM_PACKAGE_NAME
    override fun hookOnCreate(instance: Activity) {
        if (PreferenceUtil.getAppMonetEnabled(pkgName)) {
            applyMonetRes(instance)
            val root = instance.findViewById<ViewGroup>(android.R.id.content)
            setViewBackgroundColor(root, colorSurface(instance))
        }
    }
    @OptIn(LegacyResourcesHook::class)
    override fun hookClass() {
        super.hookClass()
        if (PreferenceUtil.getAppMonetEnabled(pkgName)) {
            applyMonetClazz()
        }


//        val clazz = bridge.findMethod {
//            matcher {
//                name("<clinit>")
//                usingNumbers(0xff0c1014L)
//            }
//        }.first().getClassInstance(appClassLoader!!)
//        YLog.debug("Class: ${clazz.name}")
//
//
//        clazz.declaredFields.forEach { f ->
//            f.isAccessible = true
//
//            val raw = f.getLong(null)
//
//            val color = (raw ushr 32) and 0xffffffffL
//
//            YLog.debug("Real color: ${java.lang.Long.toHexString(color)}")
//
//
//            f.setLong(null, 0xFFCDEDA3 shl 32)
//        }
    }
}