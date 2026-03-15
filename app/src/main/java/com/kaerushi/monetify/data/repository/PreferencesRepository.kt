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
import com.kaerushi.monetify.data.model.preferences.AppLanguage
import com.kaerushi.monetify.data.model.preferences.AppTheme
import com.kaerushi.monetify.data.model.preferences.ColorSchemeMode
import com.kaerushi.monetify.data.repository.PrefKeys.Xposed.appAdsKey
import com.kaerushi.monetify.data.repository.PrefKeys.Xposed.appHookStatusKey
import com.kaerushi.monetify.data.repository.PrefKeys.Xposed.appIconPackKey
import com.kaerushi.monetify.data.repository.PrefKeys.Xposed.appMonetKey
import com.kaerushi.monetify.data.repository.PrefKeys.Xposed.hookStatusKey
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

    private suspend fun <T> savePreference(
        key: Preferences.Key<T>,
        value: T,
        xposedKey: String? = null
    ) {
        try {
            dataStore.edit { it[key] = value }

            // Save to SharedPreferences for Xposed
            if (xposedKey != null) {
                xposedPrefs.edit {
                    when (value) {
                        is Boolean -> putBoolean(xposedKey, value)
                        is String -> putString(xposedKey, value)
                        is Float -> putFloat(xposedKey, value)
                        is Int -> putInt(xposedKey, value)
                        is Long -> putLong(xposedKey, value)
                        else -> Log.w(TAG, "Unsupported type for xposed mirror: ${value!!::class.simpleName}")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving preference $xposedKey: ${e.message}")
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
    
    fun hookStatuses(packageNames: List<String>): Flow<Map<String, Boolean>> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences: ${exception.message}")
                emit(emptyPreferences())
            } else throw exception
        }.map { prefs ->
            packageNames.associateWith { pkg ->
                prefs[appHookStatusKey(pkg)] ?: false
            }
        }
    }

    fun getAppMonetEnabled(packageName: String) = getPreferenceFlow(appMonetKey(packageName), false)
    fun getAppAdsDisabled(packageName: String) = getPreferenceFlow(appAdsKey(packageName), false)
    fun getAppIconPack(packageName: String) =
        getPreferenceFlow(appIconPackKey(packageName), AppIconPack.DEFAULT.name)
            .map { AppIconPack.valueOf(it) }

    // UI Preference Setters
    suspend fun setShowInstalledPref(show: Boolean) = savePreference(PrefKeys.SHOW_NOT_INSTALLED_APPS, show)
    suspend fun setShowWarningDialog(show: Boolean) = savePreference(PrefKeys.SHOW_WARNING_DIALOG, show)
    suspend fun setShowWelcomeScreen(show: Boolean) = savePreference(PrefKeys.SHOW_WELCOME_SCREEN, show)
    suspend fun setShowAppIconPack(show: Boolean) = savePreference(PrefKeys.SHOW_APP_ICON_PACK, show)
    suspend fun setTheme(theme: AppTheme) = savePreference(PrefKeys.APP_THEME_KEY, theme.name)
    suspend fun setLanguage(language: AppLanguage) = savePreference(PrefKeys.APP_LANGUAGE_KEY, language.code)
    suspend fun setColorSchemeMode(mode: ColorSchemeMode) = savePreference(PrefKeys.APP_COLOR_SCHEME_KEY, mode.name)
    suspend fun setKillBeforeLaunch(kill: Boolean) = savePreference(PrefKeys.KILL_BEFORE_LAUNCH, kill)
    
    suspend fun setHookStatus(packageName: String, status: Boolean) = 
        savePreference(appHookStatusKey(packageName), status, hookStatusKey(packageName))

    // Xposed Setters
    suspend fun setAppMonetEnabled(packageName: String, enabled: Boolean) =
        savePreference(
            appMonetKey(packageName),
            enabled,
            PrefKeys.Xposed.monetXposedKey(packageName)
        )

    suspend fun setAppAdsDisabled(packageName: String, disabled: Boolean) =
        savePreference(
            appAdsKey(packageName),
            disabled,
            PrefKeys.Xposed.adsXposedKey(packageName)
        )

    suspend fun setAppIconPack(packageName: String, iconPack: AppIconPack) = savePreference(
        appIconPackKey(packageName),
        iconPack.name,
        PrefKeys.Xposed.iconPackXposedKey(packageName)
    )

    suspend fun resetXposedPrefs() {
        dataStore.edit { preferences ->
            preferences.asMap().keys
                .filter { it !in excludedKeys }
                .forEach { preferences.remove(it) }
        }
        xposedPrefs.edit { clear() }
    }

    companion object {
        private const val TAG = "PreferencesRepository"
        private val excludedKeys = setOf(
            PrefKeys.APP_THEME_KEY,
            PrefKeys.APP_LANGUAGE_KEY,
            PrefKeys.APP_COLOR_SCHEME_KEY,
            PrefKeys.SHOW_NOT_INSTALLED_APPS,
            PrefKeys.SHOW_APP_ICON_PACK,
            PrefKeys.SHOW_WELCOME_SCREEN,
            PrefKeys.SHOW_WARNING_DIALOG,
            PrefKeys.KILL_BEFORE_LAUNCH
        )
    }
}
