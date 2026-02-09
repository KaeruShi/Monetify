package com.kaerushi.monetify.xposed

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import com.kaerushi.monetify.data.PINTEREST_PACKAGE_NAME
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
                    if (!PreferenceUtil.getAppMonetEnabled(PINTEREST_PACKAGE_NAME)) {
                        YLog.debug("Value: ${PreferenceUtil.getAppMonetEnabled(PINTEREST_PACKAGE_NAME)}")
                        return@after
                    }
                    AlertDialog.Builder(instance())
                        .setTitle("Monetify")
                        .setMessage("Monetify is not yet supported for Pinterest. Stay tuned for future updates!")
                        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        .show()
                }
            }
        }
    }
}