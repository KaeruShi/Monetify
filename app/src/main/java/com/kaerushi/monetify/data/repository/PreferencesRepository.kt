package com.kaerushi.monetify.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kaerushi.monetify.data.SHARED_PREFS_NAME
import com.kaerushi.monetify.data.viewmodel.AppIconPack
import com.kaerushi.monetify.data.viewmodel.AppTheme
import com.kaerushi.monetify.data.viewmodel.ColorSchemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SHARED_PREFS_NAME)

class PreferencesRepository(private val context: Context) {

    // Key definitions
    companion object {
        val SHOW_NOT_INSTALLED_APPS = booleanPreferencesKey("show_not_installed_apps")
        val SHOW_APP_ICON_PACK = booleanPreferencesKey("show_app_icon_pack")
        val SHOW_WELCOME_SCREEN = booleanPreferencesKey("show_welcome_screen")
        val APP_THEME_KEY = stringPreferencesKey("app_theme")
        val APP_COLOR_SCHEME_KEY = stringPreferencesKey("app_color_scheme")
    }
    private fun getAppMonetKey(packageName: String) = booleanPreferencesKey("app_${packageName}_monet_enabled")
    private fun getAppAdsKey(packageName: String) = booleanPreferencesKey("app_${packageName}_ads_disabled")
    private fun getAppIconPackKey(packageName: String) = stringPreferencesKey("app_${packageName}_icon_pack")

    // Preference Flows
    val theme = context.dataStore.data.map { AppTheme.valueOf(it[APP_THEME_KEY] ?: AppTheme.SYSTEM.name) }
    val colorSchemeMode = context.dataStore.data.map {
        ColorSchemeMode.valueOf(it[APP_COLOR_SCHEME_KEY] ?: ColorSchemeMode.DYNAMIC.name)
    }
    val showNotInstalledPref: Flow<Boolean> = context.dataStore.data.map { it[SHOW_NOT_INSTALLED_APPS] ?: true }
    val showAppIconPack: Flow<Boolean> = context.dataStore.data.map { it[SHOW_APP_ICON_PACK] ?: false }
    val showWelcomeScreen: Flow<Boolean> = context.dataStore.data.map { it[SHOW_WELCOME_SCREEN] ?: true }

    fun getAppMonetEnabled(packageName: String): Flow<Boolean> =
        context.dataStore.data.map {
            it[getAppMonetKey(packageName)] ?: false
        }
    fun getAppAdsDisabled(packageName: String): Flow<Boolean> =
        context.dataStore.data.map {
            it[getAppAdsKey(packageName)] ?: false
        }
    fun getAppIconPack(packageName: String): Flow<AppIconPack> =
        context.dataStore.data.map {
            AppIconPack.valueOf(it[getAppIconPackKey(packageName)] ?: AppIconPack.DEFAULT.name)
        }

    // Preference Setters
    suspend fun setTheme(theme: AppTheme) { context.dataStore.edit { it[APP_THEME_KEY] = theme.name } }
    suspend fun setColorSchemeMode(mode: ColorSchemeMode) { context.dataStore.edit { it[APP_COLOR_SCHEME_KEY] = mode.name } }
    suspend fun toggleShowInstalledPref(show: Boolean) { context.dataStore.edit { it[SHOW_NOT_INSTALLED_APPS] = show } }
    suspend fun toggleShowWelcomeScreenPref(show: Boolean) { context.dataStore.edit { it[SHOW_WELCOME_SCREEN] = show } }
    suspend fun toggleShowAppIconPack(show: Boolean) { context.dataStore.edit { it[SHOW_APP_ICON_PACK] = show } }
    suspend fun setAppMonetEnabled(packageName: String, enabled: Boolean) {
        context.dataStore.edit {
            it[getAppMonetKey(packageName)] = enabled
        }
    }
    suspend fun setAppAdsDisabled(packageName: String, disabled: Boolean) {
        context.dataStore.edit {
            it[getAppAdsKey(packageName)] = disabled
        }
    }
    suspend fun setAppIconPack(packageName: String, iconPack: AppIconPack) {
        context.dataStore.edit {
            it[getAppIconPackKey(packageName)] = iconPack.name
        }
    }
}