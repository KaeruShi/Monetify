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
class HomeViewModel @Inject constructor(private val repo: PreferencesRepository) : ViewModel() {
    val welcomeScreenState = repo.showWelcomeScreen
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
    val hookedAppsState = repo.hookedApps
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    fun toggleShowWelcomeScreen(show: Boolean) {
        viewModelScope.launch {
            repo.toggleShowWelcomeScreenPref(show)
        }
    }
}