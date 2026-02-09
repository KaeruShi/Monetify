package com.kaerushi.monetify.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kaerushi.monetify.data.SHARED_PREFS_NAME
import com.kaerushi.monetify.data.viewmodel.AppIconPack
import com.kaerushi.monetify.data.viewmodel.AppTheme
import com.kaerushi.monetify.data.viewmodel.ColorSchemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SHARED_PREFS_NAME)

class PreferencesRepository(private val context: Context) {

    private val tag = "PreferencesRepository"

    @SuppressLint("WorldReadableFiles")
    private val sharedPrefs = try {
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_WORLD_READABLE)
    } catch (e: SecurityException) {
        Log.e(tag, "Failed to access shared preferences: ${e.message}")
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Generic Preference Flow Getter
    private fun <T> getPreferenceFlow(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                Log.e(tag, "Error reading preferences: ${exception.message}")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[key] ?: defaultValue
        }
    }

    private suspend inline fun <reified T> savePreference(
        key: Preferences.Key<T>,
        value: T,
        sharedPrefsKey: String,
        sharedPrefValue: Any
    ) {
        try {
            context.dataStore.edit { preferences ->
                preferences[key] = value
            }

            // Save to SharedPreferences for Xposed
            val editor = sharedPrefs.edit()
            when (value) {
                is Boolean -> {
                    Log.d(tag, "Preference $sharedPrefsKey saved successfully, value: $value")
                    editor.putBoolean(sharedPrefsKey, value)
                }
                is String -> editor.putString(sharedPrefsKey, value)
                is Float -> editor.putFloat(sharedPrefsKey, value)
                is Int -> editor.putInt(sharedPrefsKey, value)
                is Long -> editor.putLong(sharedPrefsKey, value)
                is Double -> {
                    val bits = java.lang.Double.doubleToRawLongBits(value)
                    editor.putLong(sharedPrefsKey, bits)
                }
            }
            editor.apply()
        } catch (e: Exception) {
            Log.e(tag, "Error saving preference $sharedPrefsKey: ${e.message}")
        }
    }
    private fun getAppMonetKey(packageName: String) = booleanPreferencesKey("app_${packageName}_monet_enabled")
    private fun getAppAdsKey(packageName: String) = booleanPreferencesKey("app_${packageName}_ads_disabled")
    private fun getAppIconPackKey(packageName: String) = stringPreferencesKey("app_${packageName}_icon_pack")

    // Preference Flows
    val theme = context.dataStore.data.map { AppTheme.valueOf(it[PrefKeys.APP_THEME_KEY] ?: AppTheme.SYSTEM.name) }
    val colorSchemeMode = context.dataStore.data.map {
        ColorSchemeMode.valueOf(it[PrefKeys.APP_COLOR_SCHEME_KEY] ?: ColorSchemeMode.DYNAMIC.name)
    }
    val showNotInstalledPref: Flow<Boolean> =
        context.dataStore.data.map { it[PrefKeys.SHOW_NOT_INSTALLED_APPS] ?: true }
    val showAppIconPack: Flow<Boolean> = context.dataStore.data.map { it[PrefKeys.SHOW_APP_ICON_PACK] ?: false }
    val showWelcomeScreen: Flow<Boolean> = context.dataStore.data.map { it[PrefKeys.SHOW_WELCOME_SCREEN] ?: true }

    fun getAppMonetEnabled(packageName: String): Flow<Boolean> {
        return getPreferenceFlow(getAppMonetKey(packageName), false)
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
    suspend fun setTheme(theme: AppTheme) {
        context.dataStore.edit { it[PrefKeys.APP_THEME_KEY] = theme.name }
    }

    suspend fun setColorSchemeMode(mode: ColorSchemeMode) {
        context.dataStore.edit { it[PrefKeys.APP_COLOR_SCHEME_KEY] = mode.name }
    }

    suspend fun toggleShowInstalledPref(show: Boolean) {
        context.dataStore.edit { it[PrefKeys.SHOW_NOT_INSTALLED_APPS] = show }
    }

    suspend fun toggleShowWelcomeScreenPref(show: Boolean) {
        context.dataStore.edit { it[PrefKeys.SHOW_WELCOME_SCREEN] = show }
    }

    suspend fun toggleShowAppIconPack(show: Boolean) {
        context.dataStore.edit { it[PrefKeys.SHOW_APP_ICON_PACK] = show }
    }

    suspend fun setAppMonetEnabled(packageName: String, enabled: Boolean) {
        savePreference(getAppMonetKey(packageName), enabled, "app_${packageName}_monet_enabled", enabled)
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