package com.kaerushi.monetify.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kaerushi.monetify.data.datastore.UserPreferencesRepository
import kotlinx.coroutines.launch

enum class ColorSchemeMode {
    DYNAMIC, RED, GREEN, BLUE
}

class ColorSchemeViewModel(application: Application) : AndroidViewModel(application) {

    val userPreferencesRepository = UserPreferencesRepository(application)
    val colorSchemeMode = userPreferencesRepository.colorSchemeMode

    fun onColorSchemeModeChanged(newMode: ColorSchemeMode) {
        viewModelScope.launch {
            userPreferencesRepository.setColorSchemeMode(newMode)
        }
    }
}