package com.kaerushi.monetify.xposed.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import com.highcapable.yukihookapi.hook.log.YLog
import androidx.core.net.toUri

fun isNightMode(context: Context): Boolean {
    return context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}

fun showAlertDialog(act: Activity, msg: String, positiveText: String = "OK") {
    act.runOnUiThread {
        try {
            android.app.AlertDialog.Builder(act)
                .setTitle("Monetify")
                .setMessage(msg)
                .setPositiveButton(positiveText) { _, _ ->
                    val intent = android.content.Intent(
                        android.content.Intent.ACTION_VIEW,
                        "https://weeabooifychat.t.me".toUri()
                    )
                    act.startActivity(intent)
                }
                .show()
        } catch (e: Throwable) {
            YLog.error("Dialog error: ${e.message}")
        }
    }
}
