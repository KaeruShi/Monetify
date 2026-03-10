package com.kaerushi.monetify.data.repository

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PrefKeys {
    val SHOW_NOT_INSTALLED_APPS = booleanPreferencesKey("show_not_installed_apps")
    val SHOW_APP_ICON_PACK = booleanPreferencesKey("show_app_icon_pack")
    val SHOW_WARNING_DIALOG = booleanPreferencesKey("show_warning_dialog")
    val SHOW_WELCOME_SCREEN = booleanPreferencesKey("show_welcome_screen")
    val APP_THEME_KEY = stringPreferencesKey("app_theme")
    val APP_COLOR_SCHEME_KEY = stringPreferencesKey("app_color_scheme")
    val APP_LANGUAGE_KEY = stringPreferencesKey("app_language")
    val KILL_BEFORE_LAUNCH = booleanPreferencesKey("kill_before_launch")

    object Xposed {
        fun appMonetKey(packageName: String) = booleanPreferencesKey("app_${packageName}_monet_enabled")
        fun appAdsKey(packageName: String) = booleanPreferencesKey("app_${packageName}_ads_disabled")
        fun appIconPackKey(packageName: String) = stringPreferencesKey("app_${packageName}_icon_pack")
    }
}