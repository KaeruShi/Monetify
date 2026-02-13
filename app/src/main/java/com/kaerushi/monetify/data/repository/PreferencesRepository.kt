package com.kaerushi.monetify.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.kaerushi.monetify.data.repository.PrefKeys.getAppAdsKey
import com.kaerushi.monetify.data.repository.PrefKeys.getAppIconPackKey
import com.kaerushi.monetify.data.repository.PrefKeys.getAppMonetKey
import com.kaerushi.monetify.data.viewmodel.AppIconPack
import com.kaerushi.monetify.data.viewmodel.AppTheme
import com.kaerushi.monetify.data.viewmodel.ColorSchemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val xposedPrefs: SharedPreferences
) {

    private val tag = "PreferencesRepository"

    private fun <T> getPreferenceFlow(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                Log.e(tag, "Error reading preferences: ${exception.message}")
                emit(emptyPreferences())
            } else throw exception
        }.map { it[key] ?: defaultValue }
    }

    private suspend inline fun <reified T> savePreference(
        key: Preferences.Key<T>,
        value: T,
        sharedPrefsKey: String? = null
    ) {
        try {
            dataStore.edit { preferences ->
                preferences[key] = value
            }

            // Save to SharedPreferences for Xposed
            if (sharedPrefsKey != null) {
                val editor = xposedPrefs.edit()
                when (value) {
                    is Boolean -> editor.putBoolean(sharedPrefsKey, value)
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
            }
        } catch (e: Exception) {
            Log.e(tag, "Error saving preference $sharedPrefsKey: ${e.message}")
        }
    }

    // Hooked apps preference flows
    val hookedApps: Flow<Map<String, Boolean>> =
        dataStore.data.map { prefs ->
            prefs.asMap()
                .filterKeys { it.name.startsWith("app_") && it.name.endsWith("_hooked") }
                .mapNotNull { (key, value) ->
                    val pkg = key.name
                        .removePrefix("app_")
                        .removeSuffix("_hooked")

                    (value as? Boolean)?.let { pkg to it }
                }
                .toMap()
        }

    suspend fun setAppHooked(packageName: String, hooked: Boolean) =
        savePreference(PrefKeys.hookedAppKey(packageName), hooked)

    // Preference Flows
    val theme = getPreferenceFlow(PrefKeys.APP_THEME_KEY, AppTheme.SYSTEM.name).map {
        AppTheme.valueOf(it)
    }
    val colorSchemeMode = getPreferenceFlow(PrefKeys.APP_COLOR_SCHEME_KEY, ColorSchemeMode.DYNAMIC.name)
        .map { ColorSchemeMode.valueOf(it) }
    val showNotInstalledPref = getPreferenceFlow(PrefKeys.SHOW_NOT_INSTALLED_APPS, true)
    val showAppIconPack = getPreferenceFlow(PrefKeys.SHOW_APP_ICON_PACK, false)
    val showWelcomeScreen = getPreferenceFlow(PrefKeys.SHOW_WELCOME_SCREEN, true)

    fun getAppMonetEnabled(packageName: String) = getPreferenceFlow(getAppMonetKey(packageName), false)
    fun getAppAdsDisabled(packageName: String) = getPreferenceFlow(getAppAdsKey(packageName), false)
    fun getAppIconPack(packageName: String) =
        getPreferenceFlow(getAppIconPackKey(packageName), AppIconPack.DEFAULT.name)
            .map { AppIconPack.valueOf(it) }

    // Preference Setters
    suspend fun toggleShowInstalledPref(show: Boolean) = savePreference(PrefKeys.SHOW_NOT_INSTALLED_APPS, show)
    suspend fun toggleShowWelcomeScreenPref(show: Boolean) = savePreference(PrefKeys.SHOW_WELCOME_SCREEN, show)
    suspend fun toggleShowAppIconPack(show: Boolean) = savePreference(PrefKeys.SHOW_APP_ICON_PACK, show)
    suspend fun setTheme(theme: AppTheme) = savePreference(PrefKeys.APP_THEME_KEY, theme.name)
    suspend fun setColorSchemeMode(mode: ColorSchemeMode) = savePreference(PrefKeys.APP_COLOR_SCHEME_KEY, mode.name)
    suspend fun setAppMonetEnabled(packageName: String, enabled: Boolean) =
        savePreference(getAppMonetKey(packageName), enabled, "app_${packageName}_monet_enabled")

    suspend fun setAppAdsDisabled(packageName: String, disabled: Boolean) =
        savePreference(getAppAdsKey(packageName), disabled, "app_${packageName}_ads_disabled")

    suspend fun setAppIconPack(packageName: String, iconPack: AppIconPack) = savePreference(
        getAppIconPackKey(packageName),
        iconPack.name
    )
}