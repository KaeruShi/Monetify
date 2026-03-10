package com.kaerushi.monetify.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.kaerushi.monetify.data.model.preferences.AppIconPack
import com.kaerushi.monetify.data.model.preferences.AppTheme
import com.kaerushi.monetify.data.model.preferences.ColorSchemeMode
import com.kaerushi.monetify.data.model.preferences.AppLanguage
import com.kaerushi.monetify.data.repository.PrefKeys.Xposed.appAdsKey
import com.kaerushi.monetify.data.repository.PrefKeys.Xposed.appIconPackKey
import com.kaerushi.monetify.data.repository.PrefKeys.Xposed.appMonetKey
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
    private fun <T> getPreferenceFlow(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences: ${exception.message}")
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
                xposedPrefs.edit {
                    when (value) {
                        is Boolean -> putBoolean(sharedPrefsKey, value)
                        is String -> putString(sharedPrefsKey, value)
                        is Float -> putFloat(sharedPrefsKey, value)
                        is Int -> putInt(sharedPrefsKey, value)
                        is Long -> putLong(sharedPrefsKey, value)
                        is Double -> {
                            val bits = java.lang.Double.doubleToRawLongBits(value)
                            putLong(sharedPrefsKey, bits)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving preference $sharedPrefsKey: ${e.message}")
        }
    }

    // Preference Flows
    val theme = getPreferenceFlow(PrefKeys.APP_THEME_KEY, AppTheme.SYSTEM.name).map {
        AppTheme.valueOf(it)
    }
    val language = getPreferenceFlow(PrefKeys.APP_LANGUAGE_KEY, AppLanguage.ENGLISH.code).map { code ->
        AppLanguage.entries.find { it.code == code } ?: AppLanguage.ENGLISH
    }
    val colorSchemeMode = getPreferenceFlow(PrefKeys.APP_COLOR_SCHEME_KEY, ColorSchemeMode.DYNAMIC.name)
        .map { ColorSchemeMode.valueOf(it) }
    val killBeforeLaunch = getPreferenceFlow(PrefKeys.KILL_BEFORE_LAUNCH, true)
    val showNotInstalledPref = getPreferenceFlow(PrefKeys.SHOW_NOT_INSTALLED_APPS, true)
    val showAppIconPack = getPreferenceFlow(PrefKeys.SHOW_APP_ICON_PACK, false)
    val showWarningDialog = getPreferenceFlow(PrefKeys.SHOW_WARNING_DIALOG, true)
    val showWelcomeScreen = getPreferenceFlow(PrefKeys.SHOW_WELCOME_SCREEN, true)

    fun getAppMonetEnabled(packageName: String) = getPreferenceFlow(appMonetKey(packageName), false)
    fun getAppAdsDisabled(packageName: String) = getPreferenceFlow(appAdsKey(packageName), false)
    fun getAppIconPack(packageName: String) =
        getPreferenceFlow(appIconPackKey(packageName), AppIconPack.DEFAULT.name)
            .map { AppIconPack.valueOf(it) }

    // Preference Setters
    suspend fun setShowInstalledPref(show: Boolean) = savePreference(PrefKeys.SHOW_NOT_INSTALLED_APPS, show)
    suspend fun setShowWarningDialog(show: Boolean) = savePreference(PrefKeys.SHOW_WARNING_DIALOG, show)
    suspend fun setShowWelcomeScreen(show: Boolean) = savePreference(PrefKeys.SHOW_WELCOME_SCREEN, show)
    suspend fun setShowAppIconPack(show: Boolean) = savePreference(PrefKeys.SHOW_APP_ICON_PACK, show)
    suspend fun setTheme(theme: AppTheme) = savePreference(PrefKeys.APP_THEME_KEY, theme.name)
    suspend fun setLanguage(language: AppLanguage) = savePreference(PrefKeys.APP_LANGUAGE_KEY, language.code, "app_language")
    suspend fun setColorSchemeMode(mode: ColorSchemeMode) = savePreference(PrefKeys.APP_COLOR_SCHEME_KEY, mode.name)
    suspend fun setKillBeforeLaunch(kill: Boolean) = savePreference(PrefKeys.KILL_BEFORE_LAUNCH, kill)

    // Xposed
    suspend fun setAppMonetEnabled(packageName: String, enabled: Boolean) =
        savePreference(appMonetKey(packageName), enabled, "app_${packageName}_monet_enabled")

    suspend fun setAppAdsDisabled(packageName: String, disabled: Boolean) =
        savePreference(appAdsKey(packageName), disabled, "app_${packageName}_ads_disabled")

    suspend fun setAppIconPack(packageName: String, iconPack: AppIconPack) = savePreference(
        appIconPackKey(packageName),
        iconPack.name, "app_${packageName}_icon_pack"
    )

    suspend fun resetXposedPrefs() {
        val excludedKeys = setOf(
            PrefKeys.APP_THEME_KEY,
            PrefKeys.APP_LANGUAGE_KEY,
            PrefKeys.APP_COLOR_SCHEME_KEY,
            PrefKeys.SHOW_NOT_INSTALLED_APPS,
            PrefKeys.SHOW_APP_ICON_PACK,
            PrefKeys.SHOW_WELCOME_SCREEN,
            PrefKeys.SHOW_WARNING_DIALOG,
            PrefKeys.KILL_BEFORE_LAUNCH
        )
        dataStore.edit { preferences ->
            preferences.asMap().keys.filter {
                it !in excludedKeys
            }.forEach {
                preferences.remove(it)
            }
        }
        xposedPrefs.edit { clear() }
    }

    companion object {
        private const val TAG = "PreferencesRepository"
    }
}