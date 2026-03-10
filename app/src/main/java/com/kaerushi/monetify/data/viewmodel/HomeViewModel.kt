package com.kaerushi.monetify.data.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.highcapable.yukihookapi.hook.factory.dataChannel
import com.kaerushi.monetify.core.manager.BackupManager
import com.kaerushi.monetify.data.model.ConfigData
import com.kaerushi.monetify.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class BackupState {
    data object Idle : BackupState()
    data object Loading : BackupState()
    data class Success(val message: String) : BackupState()
    data class Error(val message: String) : BackupState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: PreferencesRepository,
    private val backupManager: BackupManager,
    @param:ApplicationContext private val context: Context
) : ViewModel() {

    val welcomeScreenState = repo.showWelcomeScreen
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    private val _hookedAppsState = MutableStateFlow<Set<String>>(emptySet())
    val hookedAppsState: StateFlow<Set<String>> = _hookedAppsState.asStateFlow()

    private val _backupState = MutableStateFlow<BackupState>(BackupState.Idle)
    val backupState: StateFlow<BackupState> = _backupState.asStateFlow()

    fun toggleShowWelcomeScreen(show: Boolean) {
        viewModelScope.launch {
            repo.toggleShowWelcomeScreenPref(show)
        }
    }

    fun registerHookStatus(packageNames: List<String>) {
        packageNames.forEach { pkg ->
            context.dataChannel(packageName = pkg)
                .wait<Boolean>(key = "hook_status_${pkg}") { isHooked ->
                    if (isHooked) {
                        _hookedAppsState.update { it + pkg }
                    } else {
                        _hookedAppsState.update { it - pkg }
                    }
                }
        }
    }

    /**
     * Export configuration to the specified URI
     */
    fun exportConfig(uri: Uri, configName: String) {
        viewModelScope.launch {
            _backupState.value = BackupState.Loading
            val result = backupManager.exportConfig(uri, configName)
            _backupState.value = result.fold(
                onSuccess = { BackupState.Success("Configuration exported successfully") },
                onFailure = { BackupState.Error("Export failed: ${it.message}") }
            )
        }
    }

    /**
     * Import configuration from the specified URI
     */
    fun importConfig(uri: Uri) {
        viewModelScope.launch {
            _backupState.value = BackupState.Loading
            val result = backupManager.importConfig(uri)
            _backupState.value = result.fold(
                onSuccess = { config ->
                    BackupState.Success("Configuration '${config.name}' imported successfully")
                },
                onFailure = { BackupState.Error("Import failed: ${it.message}") }
            )
        }
    }

    /**
     * Validate configuration file before importing
     */
    fun validateConfig(uri: Uri, onValidated: (ConfigData) -> Unit) {
        viewModelScope.launch {
            val result = backupManager.validateConfig(uri)
            result.onSuccess { config ->
                onValidated(config)
            }.onFailure {
                _backupState.value = BackupState.Error("Invalid configuration: ${it.message}")
            }
        }
    }

    /**
     * Reset backup state to idle
     */
    fun resetBackupState() {
        _backupState.value = BackupState.Idle
    }
}