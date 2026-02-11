package com.kaerushi.monetify.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaerushi.monetify.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repo: PreferencesRepository) : ViewModel() {
    val themeState = repo.theme
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppTheme.SYSTEM)
    val colorSchemeState = repo.colorSchemeMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ColorSchemeMode.DYNAMIC)

    fun setAppTheme(theme: AppTheme) {
        viewModelScope.launch { repo.setTheme(theme) }
    }
    fun setAppColorScheme(mode: ColorSchemeMode) {
        viewModelScope.launch { repo.setColorSchemeMode(mode) }
    }
}
enum class AppTheme {
    SYSTEM, LIGHT, DARK
}
enum class ColorSchemeMode {
    DYNAMIC, RED, GREEN, BLUE
}