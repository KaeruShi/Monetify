package com.kaerushi.monetify.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kaerushi.monetify.data.viewmodel.AppTheme
import com.kaerushi.monetify.data.viewmodel.ColorSchemeMode
import com.kaerushi.monetify.data.viewmodel.AppIconPack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreferencesRepository(private val context: Context) {

    companion object {
        val SHOW_NOT_INSTALLED_APPS = booleanPreferencesKey("show_not_installed_apps")
        val SHOW_APP_ICON_PACK = booleanPreferencesKey("show_app_icon_pack")
    }

    private val themeKey = stringPreferencesKey("theme")
    val theme = context.dataStore.data.map {
        AppTheme.valueOf(it[themeKey] ?: AppTheme.SYSTEM.name)
    }

    private val colorKey = stringPreferencesKey("color_scheme_mode")
    val colorSchemeMode = context.dataStore.data.map {
        ColorSchemeMode.valueOf(it[colorKey] ?: ColorSchemeMode.DYNAMIC.name)
    }

    val showNotInstalledPref: Flow<Boolean> = context.dataStore.data.map {
        it[SHOW_NOT_INSTALLED_APPS] ?: true
    }
    val showAppIconPack: Flow<Boolean> = context.dataStore.data.map {
        it[SHOW_APP_ICON_PACK] ?: false
    }

    private fun getAppMonetKey(packageName: String) =
        booleanPreferencesKey("app_${packageName}_monet_enabled")
    private fun getAppAdsKey(packageName: String) =
        booleanPreferencesKey("app_${packageName}_ads_disabled")
    private fun getAppIconPackKey(packageName: String) =
        stringPreferencesKey("app_${packageName}_icon_pack")

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

    suspend fun setTheme(theme: AppTheme) {
        context.dataStore.edit {
            it[themeKey] = theme.name
        }
    }

    suspend fun setColorSchemeMode(mode: ColorSchemeMode) {
        context.dataStore.edit {
            it[colorKey] = mode.name
        }
    }

    suspend fun toggleShowInstalledPref(show: Boolean) {
        context.dataStore.edit {
            it[SHOW_NOT_INSTALLED_APPS] = show
        }
    }
    suspend fun toggleShowAppIconPack(show: Boolean) {
        context.dataStore.edit {
            it[SHOW_APP_ICON_PACK] = show
        }
    }

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