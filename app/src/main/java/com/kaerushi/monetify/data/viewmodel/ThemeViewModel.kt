package com.kaerushi.monetify.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kaerushi.monetify.data.datastore.UserPreferencesRepository
import kotlinx.coroutines.launch

enum class AppTheme {
    SYSTEM,
    LIGHT,
    DARK
}

class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    private val userPreferencesRepository = UserPreferencesRepository(application)

    val theme = userPreferencesRepository.theme

    fun onThemeChanged(newTheme: AppTheme) {
        viewModelScope.launch {
            userPreferencesRepository.setTheme(newTheme)
        }
    }
}
