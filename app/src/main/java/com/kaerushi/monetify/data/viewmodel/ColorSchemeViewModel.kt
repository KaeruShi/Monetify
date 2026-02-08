package com.kaerushi.monetify.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kaerushi.monetify.data.repository.PreferencesRepository
import kotlinx.coroutines.launch

enum class ColorSchemeMode {
    DYNAMIC, RED, GREEN, BLUE
}

class ColorSchemeViewModel(application: Application) : AndroidViewModel(application) {

    val preferencesRepository = PreferencesRepository(application)
    val colorSchemeMode = preferencesRepository.colorSchemeMode

    fun onColorSchemeModeChanged(newMode: ColorSchemeMode) {
        viewModelScope.launch {
            preferencesRepository.setColorSchemeMode(newMode)
        }
    }
}