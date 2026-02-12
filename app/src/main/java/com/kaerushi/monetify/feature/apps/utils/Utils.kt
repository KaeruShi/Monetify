package com.kaerushi.monetify.feature.apps.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import com.kaerushi.monetify.R
import com.kaerushi.monetify.data.INSTAGRAM_PACKAGE_NAME
import com.kaerushi.monetify.data.PINTEREST_PACKAGE_NAME
import com.kaerushi.monetify.data.REDDIT_PACKAGE_NAME
import com.kaerushi.monetify.data.SPOTIFY_PACKAGE_NAME
import com.kaerushi.monetify.data.SUBSTRATUM_LITE_PACKAGE_NAME
import com.kaerushi.monetify.data.X_PACKAGE_NAME
import com.kaerushi.monetify.data.model.AppInfo

object Utils {
    @SuppressLint("QueryPermissionsNeeded")
    fun getInstalledApps(context: Context, showUninstalled: Boolean = true): List<AppInfo> {
        val packageManager = context.packageManager
        val apps = mutableListOf<AppInfo>()
        val targetApps = listOf(
            Triple("Instagram", INSTAGRAM_PACKAGE_NAME, R.drawable.app_ig),
            Triple("X", X_PACKAGE_NAME, R.drawable.app_twitter),
            Triple("Pinterest", PINTEREST_PACKAGE_NAME, R.drawable.app_pinterest),
            Triple("Reddit", REDDIT_PACKAGE_NAME, R.drawable.app_reddit),
            Triple("Substratum Lite", SUBSTRATUM_LITE_PACKAGE_NAME, R.drawable.app_subslite),
            Triple("Spotify", SPOTIFY_PACKAGE_NAME, R.drawable.app_spotify)
        )
        for ((displayName, packageName, altIcon) in targetApps) {
            try {
                val appInfo = packageManager.getApplicationInfo(packageName, 0)
                val appName = packageManager.getApplicationLabel(appInfo).toString()
                val versionName = packageManager.getPackageInfo(packageName, 0).versionName ?: "Unknown"
                val icon = packageManager.getApplicationIcon(appInfo)

                apps.add(AppInfo(appName, packageName, versionName, icon, altIcon))
            } catch (_: PackageManager.NameNotFoundException) {
                if (showUninstalled)
                    apps.add(AppInfo(displayName, packageName, "Not Installed!", null, altIcon, false))
            }
        }

        return apps.sortedBy {
            it.name
        }
    }
}

