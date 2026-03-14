package com.kaerushi.monetify.xposed.hooks

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.kaerushi.monetify.data.model.preferences.AppIconPack
import com.kaerushi.monetify.xposed.MainHook.bridge
import com.kaerushi.monetify.xposed.helper.InjectLayoutHelper
import com.kaerushi.monetify.xposed.utils.PreferenceUtil
import com.kaerushi.monetify.xposed.utils.showAlertDialog
import org.luckypray.dexkit.DexKitBridge
import java.lang.ref.WeakReference

abstract class BaseAppHook : YukiBaseHooker() {
    private var currentActivity: WeakReference<Activity>? = null
    private var errorShown = false
    private val errorNames = mutableListOf<String>()
    protected abstract val pkgName: String
    protected abstract val duotoneDrawables: Map<String, Int>

    @LegacyResourcesHook
    override fun onHook() {
        loadApp(pkgName) {
            bridge = DexKitBridge.create(appInfo.sourceDir)
            onAppLifecycle {
                onCreate {
                    dataChannel.put(key = "hook_status_${pkgName}", value = true)
                }
            }
            getIconPackDrawables()?.let { drawables ->
                resources().hook {
                    drawables.forEach { (resName, replacementDrawable) ->
                        injectResource {
                            conditions {
                                name = resName
                                drawable()
                            }
                            replaceToModuleResource(replacementDrawable)
                        }
                    }
                }
            }
            hookClass()
        }
    }

    protected open fun hookClass() {
        Activity::class.java.resolve().firstMethod { name = "onCreate"; parameters(Bundle::class.java) }.hook {
            after {
                val instance = instance<Activity>()
                currentActivity = WeakReference(instance)
                hookOnCreate(instance)
            }
        }
    }

    protected open fun hookOnCreate(instance: Activity) {}

    protected fun getActivity(): Activity? {
        return currentActivity?.get()
    }

    private fun getIconPackDrawables(): Map<String, Int>? {
        return when (PreferenceUtil.getAppIconPack(packageName)) {
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