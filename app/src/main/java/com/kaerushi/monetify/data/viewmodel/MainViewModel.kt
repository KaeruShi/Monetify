package com.kaerushi.monetify.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kaerushi.monetify.data.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    val repository = userPreferencesRepository
    val uiState: StateFlow<Boolean> = userPreferencesRepository.showNotInstalledPref
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true // Initial value before DataStore loads
        )

    fun toggleNotInstalled(isVisible: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.toggleShowInstalledPref(isVisible)
        }
    }

    fun toggleWelcomeScreen(show: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.toggleShowWelcomeScreenPref(show)
        }
    }
}

class MainViewModelFactory(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}