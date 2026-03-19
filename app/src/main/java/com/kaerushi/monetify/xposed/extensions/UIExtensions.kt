package com.kaerushi.monetify.xposed.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import com.highcapable.yukihookapi.hook.log.YLog
import androidx.core.net.toUri

fun isNightMode(context: Context): Boolean {
    return context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}

fun showAlertDialog(act: Activity, msg: String, positiveText: String = "OK") {
    act.runOnUiThread {
        try {
            AlertDialog.Builder(act)
                .setTitle("Monetify")
                .setMessage(msg)
                .setPositiveButton(positiveText) { _, _ ->
                    val intent = Intent(
                        Intent.ACTION_VIEW,
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
