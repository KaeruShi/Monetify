package com.kaerushi.monetify.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kaerushi.monetify.data.repository.PreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {
    val repository = preferencesRepository
    val uiState: StateFlow<Boolean> = preferencesRepository.showNotInstalledPref
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true // Initial value before DataStore loads
        )

    fun toggleNotInstalled(isVisible: Boolean) {
        viewModelScope.launch {
            preferencesRepository.toggleShowInstalledPref(isVisible)
        }
    }

    fun toggleWelcomeScreen(show: Boolean) {
        viewModelScope.launch {
            preferencesRepository.toggleShowWelcomeScreenPref(show)
        }
    }
}

class MainViewModelFactory(
    private val preferencesRepository: PreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(preferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}