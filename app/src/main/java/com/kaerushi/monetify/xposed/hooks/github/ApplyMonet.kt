package com.kaerushi.monetify.xposed.hooks.github

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.xposed.MainHook.bridge
import com.kaerushi.monetify.xposed.utils.colorInversePrimary
import com.kaerushi.monetify.xposed.utils.colorOnSurface
import com.kaerushi.monetify.xposed.utils.colorPrimary
import com.kaerushi.monetify.xposed.utils.colorPrimaryDark
import com.kaerushi.monetify.xposed.utils.colorPrimaryLight
import com.kaerushi.monetify.xposed.utils.colorSurface
import com.kaerushi.monetify.xposed.utils.colorSurfaceContainer
import com.kaerushi.monetify.xposed.utils.colorSurfaceContainerDark
import com.kaerushi.monetify.xposed.utils.colorSurfaceContainerLight
import com.kaerushi.monetify.xposed.utils.colorSurfaceDark
import com.kaerushi.monetify.xposed.utils.colorSurfaceLight

@LegacyResourcesHook
fun GitHubHooks.applyMonetRes(context: Activity) {
    injectColor(
        "backgroundPrimary", "backgroundElevatedPrimary", "backgroundElevatedSecondary",
        "backgroundElevatedTertiary", "gray_150", "backgroundTertiary",
        "diffLineNumberNeutralBackground", "backgroundInset", "blockquoteBlock", "gray_200", "gray_850"
    ) {
        colorSurfaceContainer(context)
    }
    injectColor("appBackground", "backgroundSecondary", "toolbarBackground") {
        colorSurface(context)
    }
    injectColor(
        "colorAccent", "blue_500", "blue_300",
        "systemBlue", "link", "mh", "mi2_background", "badge_blue_label"
    ) {
        colorPrimary(context)
    }
}

@LegacyResourcesHook
fun GitHubHooks.applyMonetLayout() {
    hookLayout("list_item_reaction_list") {
        val container = findView("reaction_list_container")
        container?.apply {
            setPadding(16, 16, 16, 16)
            setBackgroundColor(colorSurface(appContext!!))
        }
    }
}

fun GitHubHooks.applyMonetClazz() {
    val targetClass = bridge.findMethod {
        matcher {
            usingStrings("SrcOver", "Luminosity", "ColorDodge", "ColorBurn")
        }
    }.single().getClassInstance(appClassLoader!!)

    val transformerMethod = bridge.findMethod {
        matcher {
            declaredClass(targetClass.name)
            returnType = "long"
            paramTypes("long")
        }
    }.single()

    transformerMethod.getMethodInstance(appClassLoader!!).hook {
        before {
            val color = args[0] as Long
            when (color) {
                // Background primary
                0xffffffffL -> args[0] = colorSurfaceLight(appContext!!)
                0xfffefefeL -> args[0] = colorSurfaceLight(appContext!!)
                0xfff7f7f9L -> args[0] = colorSurfaceLight(appContext!!)
                0xff17181cL -> args[0] = colorSurfaceDark(appContext!!)
                0xff0b0b0dL -> args[0] = colorSurfaceDark(appContext!!)
                // Background secondary
                0xfffbfbfcL -> args[0] = colorSurfaceContainerLight(appContext!!)
                0xffeff0f5L -> args[0] = colorSurfaceContainerLight(appContext!!)
                0xff050505L -> args[0] = colorSurfaceContainerDark(appContext!!)
                0xff1f1f24L -> args[0] = colorSurfaceContainerDark(appContext!!)
                0xff383a42L -> args[0] = colorSurfaceContainerDark(appContext!!)
                0xff2e2f37L -> args[0] = colorSurfaceContainerDark(appContext!!)
                // Accent color
                0xff0366d6L -> args[0] = colorPrimaryLight(appContext!!)
                0xff005cc5L -> args[0] = colorPrimaryLight(appContext!!)
                0xff85beffL -> args[0] = colorPrimaryDark(appContext!!)
                // Navbar item background ?
                0x522e8fffL -> args[0] = colorPrimaryDark(appContext!!)
                // Inbox chips selected
                0xffdbedffL -> args[0] = colorSurfaceContainerLight(appContext!!) // Light
                0xff0d6edbL -> args[0] = colorInversePrimary(appContext!!) // Dark
                // Navbar icon unactive
                0xff9194a1L -> args[0] = colorOnSurface(appContext!!)
                0xff6c6f7eL -> args[0] = colorOnSurface(appContext!!) // Dark
                // Badge stroke
                0x292188ffL -> args[0] = colorPrimaryLight(appContext!!) // Light
            }
        }
    }

    "com.github.commonandroid.views.ScrollableTitleToolbar".toClass().resolve().constructor {
        parameters(Context::class.java, AttributeSet::class.java)
    }.hookAll {
        after {
            val toolbar = instance as ViewGroup
            toolbar.setBackgroundColor(colorSurface(appContext!!))
        }
    }

    View::class.java.resolve()
        .method {
            name = "onAttachedToWindow"
        }.hookAll {
            after {
                val view = instance as View
                if (view.javaClass.name == "com.github.android.webview.viewholders.GitHubWebView") {
                    view.post {
                        val color = colorSurface(appContext!!)

                        view.setBackgroundColor(color)
                        (view.parent as? View)?.setBackgroundColor(color)

                        view.viewTreeObserver.addOnGlobalLayoutListener {
                            view.setBackgroundColor(color)
                            (view.parent as? View)?.setBackgroundColor(color)
                        }
                    }
                }
            }
        }
}