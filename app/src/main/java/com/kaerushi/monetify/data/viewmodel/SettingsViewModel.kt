package com.kaerushi.monetify.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaerushi.monetify.core.manager.LocaleManager
import com.kaerushi.monetify.data.model.preferences.AppLanguage
import com.kaerushi.monetify.data.model.preferences.AppTheme
import com.kaerushi.monetify.data.model.preferences.ColorSchemeMode
import com.kaerushi.monetify.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: PreferencesRepository,
    private val localeManager: LocaleManager
) : ViewModel() {
    val themeState = repo.theme
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppTheme.SYSTEM)
    val colorSchemeState = repo.colorSchemeMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ColorSchemeMode.DYNAMIC)
    val languageState = repo.language
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppLanguage.ENGLISH)
    val killBeforeLaunchState = repo.killBeforeLaunch
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun setAppTheme(theme: AppTheme) {
        viewModelScope.launch { repo.setTheme(theme) }
    }

    fun setAppColorScheme(mode: ColorSchemeMode) {
        viewModelScope.launch { repo.setColorSchemeMode(mode) }
    }

    fun setAppLanguage(language: AppLanguage) {
        viewModelScope.launch {
            repo.setLanguage(language)
            localeManager.applyLocale(language)
        }
    }

    fun setKillBeforeLaunch(kill: Boolean) {
        viewModelScope.launch { repo.setKillBeforeLaunch(kill) }
    }

    fun resetToDefaults() {
        viewModelScope.launch {
            repo.resetXposedPrefs()
        }
    }
}

