package com.kaerushi.monetify.xposed.hooks

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.kaerushi.monetify.xposed.utils.HookStatusUtil
import com.kaerushi.monetify.xposed.utils.HookStatusUtil.shouldSend
import com.kaerushi.monetify.xposed.utils.PreferenceUtil

object RedditHooks : YukiBaseHooker() {
    override fun onHook() {
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