package com.kaerushi.monetify.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kaerushi.monetify.data.repository.PreferencesRepository
import kotlinx.coroutines.launch

enum class AppTheme {
    SYSTEM,
    LIGHT,
    DARK
}

class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    private val preferencesRepository = PreferencesRepository(application)

    val theme = preferencesRepository.theme

    fun onThemeChanged(newTheme: AppTheme) {
        viewModelScope.launch {
            preferencesRepository.setTheme(newTheme)
        }
    }
}
