package com.kaerushi.monetify.core.util

import android.content.Context
import android.content.Intent
import com.highcapable.yukihookapi.YukiHookAPI
import com.topjohnwu.superuser.Shell

object Utils {
    val isModuleActive = YukiHookAPI.Status.isXposedModuleActive
    fun Context.launchApp(pkgName: String, kill: Boolean) {
        if (kill) Shell.cmd("am force-stop $pkgName").exec()
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