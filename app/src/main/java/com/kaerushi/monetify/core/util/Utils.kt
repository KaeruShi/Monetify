package com.kaerushi.monetify.core.util

import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.highcapable.yukihookapi.YukiHookAPI
import com.kaerushi.monetify.feature.settings.AppLanguage

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

    fun applyLocale(context: Context, language: AppLanguage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(language.code)
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language.code))
        }
    }
}