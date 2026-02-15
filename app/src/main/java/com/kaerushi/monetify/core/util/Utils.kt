package com.kaerushi.monetify.core.util

import android.content.Context
import android.content.Intent
import com.highcapable.yukihookapi.YukiHookAPI

object Utils {
    val isModuleActive = YukiHookAPI.Status.isXposedModuleActive
    fun Context.launchApp(pkgName: String) {
        try {
            val launchIntent = this.packageManager.getLaunchIntentForPackage(pkgName)
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(launchIntent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}