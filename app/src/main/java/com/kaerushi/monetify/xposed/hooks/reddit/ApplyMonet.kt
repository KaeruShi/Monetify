package com.kaerushi.monetify.xposed.hooks.reddit

import android.annotation.SuppressLint
import android.app.Activity
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.xposed.MainHook.bridge
import com.kaerushi.monetify.xposed.utils.colorPrimary
import com.kaerushi.monetify.xposed.utils.colorPrimaryLight
import com.kaerushi.monetify.xposed.utils.colorSurfaceContainerDark
import com.kaerushi.monetify.xposed.utils.colorSurfaceContainerLight
import com.kaerushi.monetify.xposed.utils.colorSurfaceDark
import com.kaerushi.monetify.xposed.utils.colorSurfaceLight

@OptIn(LegacyResourcesHook::class)
fun RedditHooks.applyMonetRes(activity: Activity) {
    injectColor(
        "alienblue_primary", "night_primary"
    ) {
        colorPrimary(activity)
    }
    injectColor(
        "white", "alienblue_tone6", "alienblue_tone8", "ds_primitive_narwhal_100"
    ) {
        colorSurfaceLight(activity)
    }
    injectColor("alienblue_canvas") {
        colorSurfaceContainerLight(activity)
    }

    // Dark
    injectColor("night_canvas", "alienblue_tone1", "night_tone8") {
        colorSurfaceDark(activity)
    }
    injectColor("night_tone6") {
        colorSurfaceContainerDark(activity)
    }
}

@SuppressLint("DiscouragedPrivateApi", "NonUniqueDexKitData")
fun RedditHooks.applyMonetClazz() {
    val method = bridge.findMethod {
        matcher {
            usingNumbers(0xff121212L)
        }
    }

    val caller = method.first().invokes.first().getMethodInstance(appClassLoader!!)
    caller.hook {
        before {
            val color = args[0] as Long
            when (color) {
                // Accent color
                0xff648efcL -> args[0] = colorPrimary(appContext!!)
                0xff0a449bL -> args[0] = colorPrimary(appContext!!)
                0xff115bcaL -> args[0] = colorPrimaryLight(appContext!!)
                // Background Primary
                0xffffffffL -> args[0] = colorSurfaceLight(appContext!!)
                0xff000000L -> args[0] = colorSurfaceDark(appContext!!)
                0xff121212L -> args[0] = colorSurfaceDark(appContext!!)
                0xff0e1113L -> args[0] = colorSurfaceDark(appContext!!)
                0xff0a0a0aL -> args[0] = colorSurfaceDark(appContext!!)
                // Background Secondary
                0xffe5ebeeL -> args[0] = colorSurfaceContainerLight(appContext!!)
                0xfff6f8f9L -> args[0] = colorSurfaceContainerLight(appContext!!)
                0xffeef1f3L -> args[0] = colorSurfaceContainerLight(appContext!!)
                0xfff1f3f5L -> args[0] = colorSurfaceContainerLight(appContext!!)
                0xffc9d7deL -> args[0] = colorSurfaceContainerLight(appContext!!)
                0xff181c1fL -> args[0] = colorSurfaceContainerDark(appContext!!)
                0xff3d494eL -> args[0] = colorSurfaceContainerDark(appContext!!)
                0xff2a3236L -> args[0] = colorSurfaceContainerDark(appContext!!)
            }
        }
    }
}
