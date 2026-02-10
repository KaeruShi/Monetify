package com.kaerushi.monetify.xposed.utils

import android.content.Context
import android.content.Intent
import com.highcapable.yukihookapi.hook.log.YLog
import com.kaerushi.monetify.data.ACTION_HOOK_STATUS
import com.kaerushi.monetify.data.EXTRA_HOOKED
import com.kaerushi.monetify.data.EXTRA_PACKAGE
import com.kaerushi.monetify.data.PACKAGE_NAME

object HookStatusUtil {
    fun sendHooked(context: Context, pkg: String, hooked: Boolean): Boolean {
        return runCatching {
            val i = Intent(ACTION_HOOK_STATUS).apply {
                setPackage(PACKAGE_NAME)
                putExtra(EXTRA_PACKAGE, pkg)
                putExtra(EXTRA_HOOKED, hooked)
            }
            context.sendBroadcast(i)
            true
        }.getOrElse {
            YLog.error("sendHooked failed for pkg=$pkg hooked=$hooked, msg: ${it.message}")
            false
        }
    }
    private val sent = java.util.concurrent.ConcurrentHashMap<String, Boolean>()
    fun shouldSend(pkg: String) = sent.putIfAbsent(pkg, true) == null
}