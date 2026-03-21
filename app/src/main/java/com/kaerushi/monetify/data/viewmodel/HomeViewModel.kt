package com.kaerushi.monetify.data.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.highcapable.yukihookapi.hook.factory.dataChannel
import com.kaerushi.monetify.R
import com.kaerushi.monetify.core.manager.BackupManager
import com.kaerushi.monetify.data.model.ConfigData
import com.kaerushi.monetify.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class BackupStatus {
    data object Idle : BackupStatus()
    data object Loading : BackupStatus()
}

sealed class BackupEvent {
    data class Success(val message: String) : BackupEvent()
    data class Error(val message: String) : BackupEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: PreferencesRepository,
    private val backupManager: BackupManager,
    @param:ApplicationContext private val context: Context
) : ViewModel() {

    val welcomeScreenState = repo.showWelcomeScreen
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun toggleShowWelcomeScreen(show: Boolean) {
        viewModelScope.launch {
            repo.setShowWelcomeScreen(show)
        }
    }

    fun hookedAppsState(packageName: List<String>) = repo.hookStatuses(packageName)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())
    fun setHookStatus(packageName: String, status: Boolean) = viewModelScope.launch { repo.setHookStatus(packageName, status) }

    private val registeredApps = mutableSetOf<String>()

    fun registerHookStatus(packageNames: List<String>) {
        packageNames
            .filterNot { it in registeredApps }
            .forEach { pkg ->
                registeredApps += pkg
                context.dataChannel(packageName = pkg)
                    .wait<Boolean>(key = "hook_status_${pkg}") { isHooked ->
                        setHookStatus(pkg, isHooked)
                    }
            }
    }

    private val _backupStatus = MutableStateFlow<BackupStatus>(BackupStatus.Idle)
    val backupStatus: StateFlow<BackupStatus> = _backupStatus.asStateFlow()

    private val _backupEvents = Channel<BackupEvent>(Channel.BUFFERED)
    val backupEvents = _backupEvents.receiveAsFlow()

    fun exportConfig(uri: Uri, configName: String) {
        viewModelScope.launch {
            _backupStatus.value = BackupStatus.Loading
            val result = backupManager.exportConfig(uri, configName)
            _backupStatus.value = BackupStatus.Idle
            _backupEvents.send(
                result.fold(
                    onSuccess = { BackupEvent.Success(context.getString(R.string.config_export_success)) },
                    onFailure = { BackupEvent.Error(context.getString(R.string.config_export_failed, it.message)) }
                )
            )
        }
    }

    fun importConfig(uri: Uri) {
        viewModelScope.launch {
            _backupStatus.value = BackupStatus.Loading
            val result = backupManager.importConfig(uri)
            _backupStatus.value = BackupStatus.Idle
            _backupEvents.send(
                result.fold(
                    onSuccess = { config ->
                        BackupEvent.Success(context.getString(R.string.config_import_success, config.name))
                    },
                    onFailure = { BackupEvent.Error(context.getString(R.string.config_import_failed, it.message)) }
                )
            )
        }
    }

    fun validateConfig(uri: Uri, onValidated: (ConfigData) -> Unit) {
        viewModelScope.launch {
            backupManager.validateConfig(uri)
                .onSuccess { onValidated(it) }
                .onFailure {
                    _backupEvents.send(BackupEvent.Error(context.getString(R.string.invalid_config, it.message)))
                }
        }
    }

    fun resetBackupState() {
        _backupStatus.value = BackupStatus.Idle
    }
}