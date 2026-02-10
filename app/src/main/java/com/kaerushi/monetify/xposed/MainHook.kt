package com.kaerushi.monetify.xposed

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import com.kaerushi.monetify.data.PINTEREST_PACKAGE_NAME
import com.kaerushi.monetify.data.REDDIT_PACKAGE_NAME
import com.kaerushi.monetify.xposed.utils.HookStatusUtil
import com.kaerushi.monetify.xposed.utils.HookStatusUtil.shouldSend
import com.kaerushi.monetify.xposed.utils.PreferenceUtil

@InjectYukiHookWithXposed
class MainHook : IYukiHookXposedInit {
    override fun onHook() = encase {
        loadApp(name = PINTEREST_PACKAGE_NAME) {
            Activity::class.java.resolve().firstMethod {
                name("onCreate")
                parameters(Bundle::class.java)
            }.hook {
                after {
                    val ctx = instance<Context>()
                    val pkg = ctx.packageName
                    val enabled = PreferenceUtil.getAppMonetEnabled(pkg)
                    if (!enabled) return@after
                    if (shouldSend(pkg)) HookStatusUtil.sendHooked(ctx, pkg, true)
                    AlertDialog.Builder(instance())
                        .setTitle("Monetify")
                        .setMessage("Pinterest Hooked Successfully!")
                        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        .show()
                }
            }
        }
        loadApp(name = REDDIT_PACKAGE_NAME) {
            Activity::class.java.resolve().firstMethod {
                name("onCreate")
                parameters(Bundle::class.java)
            }.hook {
                after {
                    val ctx = instance<Context>()
                    val pkg = ctx.packageName
                    val enabled = PreferenceUtil.getAppMonetEnabled(pkg)
                    if (!enabled) return@after
                    if (shouldSend(pkg)) HookStatusUtil.sendHooked(ctx, pkg, true)
                    AlertDialog.Builder(instance())
                        .setTitle("Monetify")
                        .setMessage("Reddit Hooked Successfully!")
                        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        .show()
                }
            }
        }
    }
}