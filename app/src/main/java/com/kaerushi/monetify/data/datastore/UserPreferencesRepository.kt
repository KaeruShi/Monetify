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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreferencesRepository(private val context: Context) {

    companion object {
        val SHOW_NOT_INSTALLED_APPS = booleanPreferencesKey("show_not_installed_apps")
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
}