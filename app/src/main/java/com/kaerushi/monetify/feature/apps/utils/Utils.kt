package com.kaerushi.monetify.feature.apps.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import com.kaerushi.monetify.R
import com.kaerushi.monetify.data.model.AppInfo

object Utils {
    @SuppressLint("QueryPermissionsNeeded")
    fun getInstalledApps(context: Context, showUninstalled: Boolean = true): List<AppInfo> {
        val packageManager = context.packageManager
        val apps = mutableListOf<AppInfo>()
        val targetApps = listOf(
            Triple("Instagram", "com.instagram.android", R.drawable.app_ig),
            Triple("X", "com.twitter.android", R.drawable.app_twitter),
            Triple("Pinterest", "com.pinterest", R.drawable.app_pinterest),
            Triple("Reddit", "com.reddit.frontpage", R.drawable.app_reddit)
        )
        for ((displayName, packageName, altIcon) in targetApps) {
            try {
                val appInfo = packageManager.getApplicationInfo(packageName, 0)
                val appName = packageManager.getApplicationLabel(appInfo).toString()
                val versionName = packageManager.getPackageInfo(packageName, 0).versionName ?: "Unknown"
                val icon = packageManager.getApplicationIcon(appInfo)

                apps.add(AppInfo(appName, packageName, versionName, icon, altIcon))
            } catch (e: PackageManager.NameNotFoundException) {
                if (showUninstalled)
                    apps.add(AppInfo(displayName, packageName, "Not Installed!", null, altIcon, false))
            }
        }

        return apps.sortedBy {
            it.name
        }
    }
}