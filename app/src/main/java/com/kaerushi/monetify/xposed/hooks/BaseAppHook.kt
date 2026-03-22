package com.kaerushi.monetify.xposed.hooks

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.log.YLog
import com.kaerushi.monetify.data.FILES_PACKAGE_NAME
import com.kaerushi.monetify.data.model.preferences.AppIconPack
import com.kaerushi.monetify.xposed.MainHook
import com.kaerushi.monetify.xposed.MainHook.bridge
import com.kaerushi.monetify.xposed.extensions.showAlertDialog
import com.kaerushi.monetify.xposed.helper.InjectLayoutHelper
import com.kaerushi.monetify.xposed.utils.PreferenceUtils
import java.lang.ref.WeakReference

abstract class BaseAppHook : YukiBaseHooker() {
    private var currentActivity: WeakReference<Activity>? = null
    private var errorShown = false
    private val errorNames = mutableListOf<String>()
    protected abstract val pkgName: String
    protected open val duotoneDrawables: Map<String, DrawableReplacement> = emptyMap()

    @LegacyResourcesHook
    override fun onHook() {
        loadApp(pkgName) {
            bridge = MainHook.getOrCreateBridge(appInfo.sourceDir, pkgName)
            onAppLifecycle {
                onCreate {
                    if (PreferenceUtils.getAppHookStatus(pkgName) == false) {
                        YLog.debug("Data channel: $pkgName")
                        dataChannel.put(key = "hook_status_${pkgName}", value = true)
                    }
                }
            }
            hookClass()

        }
    }

    @LegacyResourcesHook
    protected open fun hookClass() {
        Activity::class.java.resolve().firstMethod { name = "onCreate"; parameters(Bundle::class.java) }.hook {
            after {
                val instance = instance<Activity>()
                currentActivity = WeakReference(instance)
                getIconPackDrawables(instance)?.let { drawables ->
                    resources().hook {
                        drawables.forEach { (resName, replacement) ->
                            injectResource {
                                conditions {
                                    name = resName
                                    drawable()
                                }
                                when (pkgName) {
                                    FILES_PACKAGE_NAME -> {
                                        replaceTo {
                                            val d = ResourcesCompat.getDrawable(
                                                moduleAppResources,
                                                replacement.resId,
                                                null
                                            )?.mutate()
                                            val color = replacement.colorProvider?.invoke(instance)
                                            d?.setTint(color ?: 0)
                                            d
                                        }
                                    }
                                    else -> replaceToModuleResource(replacement.resId)
                                }

                            }
                        }
                    }
                }
                hookOnCreate(instance)
            }
        }
    }

    protected open fun hookOnCreate(instance: Activity) {}

    protected fun getActivity(): Activity? {
        return currentActivity?.get()
    }

    private fun getIconPackDrawables(context: Context): Map<String, DrawableReplacement>? {
        return when (PreferenceUtils.getAppIconPack(context.packageName)) {
            AppIconPack.DEFAULT.name -> null
            AppIconPack.DUOTONE.name -> duotoneDrawables
            else -> null
        }
    }

    @LegacyResourcesHook
    fun injectColor(
        vararg colorNames: String,
        provider: () -> Int
    ) {
        resources().hook {
            colorNames.forEach { name ->
                injectResource {
                    conditions {
                        this.name = name
                        color()
                    }
                    replaceTo { provider() }
                }.onHookingFailure {
                    errorNames.add(name)
                    getActivity()?.window?.decorView?.postDelayed({
                        if (!errorShown) {
                            errorShown = true
                            val msg = buildString {
                                append("No color resources found for:\n\n")
                                errorNames.forEach {
                                    append("• $it\n")
                                }
                            }
                            showAlertDialog(getActivity()!!, msg, "Report")
                        }
                    }, 300)
                }
            }
        }
    }

    @LegacyResourcesHook
    inline fun hookLayout(
        name: String,
        crossinline block: InjectLayoutHelper.() -> Unit
    ) {
        resources().hook {
            injectResource {
                conditions {
                    this.name = name
                    layout()
                }
                injectAsLayout {
                    InjectLayoutHelper(
                        currentView as ViewGroup,
                        appContext!!
                    ).block()
                }
            }
        }
    }
}
data class DrawableReplacement(
    val resId: Int,
    val colorProvider: ((Context) -> Int)? = null
)