package com.kaerushi.monetify.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaerushi.monetify.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppsViewModel @Inject constructor(
    private val repo: PreferencesRepository
) : ViewModel() {
    val notInstalledState = repo.showNotInstalledPref
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
    val showIconPackState = repo.showAppIconPack
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val enableMonetStates = mutableMapOf<String, StateFlow<Boolean>>()
    fun enableMonetState(pkgName: String) = enableMonetStates.getOrPut(pkgName) {
        repo.getAppMonetEnabled(pkgName)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    }

    private val disableAdsStates = mutableMapOf<String, StateFlow<Boolean>>()
    fun disableAdsState(pkgName: String) = disableAdsStates.getOrPut(pkgName) {
        repo.getAppAdsDisabled(pkgName)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    }

    private val iconPackStates = mutableMapOf<String, StateFlow<AppIconPack>>()
    fun iconPackState(pkgName: String) = iconPackStates.getOrPut(pkgName) {
        repo.getAppIconPack(pkgName)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppIconPack.DEFAULT)
    }

    fun toggleNotInstalled(isVisible: Boolean) {
        viewModelScope.launch { repo.toggleShowInstalledPref(isVisible) }
    }
    fun toggleShowIconPack(show: Boolean) {
        viewModelScope.launch { repo.toggleShowAppIconPack(show) }
    }
    fun setMonetEnabled(pkgName: String, enabled: Boolean) {
        viewModelScope.launch { repo.setAppMonetEnabled(pkgName, enabled) }
    }
    fun setAdsDisabled(pkgName: String, disabled: Boolean) {
        viewModelScope.launch { repo.setAppAdsDisabled(pkgName, disabled) }
    }
    fun setIconPack(pkgName: String, iconPack: AppIconPack) {
        viewModelScope.launch { repo.setAppIconPack(pkgName, iconPack) }
    }
}

enum class AppIconPack {
    DEFAULT, DUOTONE, FILLED, OUTLINED
}