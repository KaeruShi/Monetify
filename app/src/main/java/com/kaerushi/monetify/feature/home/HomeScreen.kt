package com.kaerushi.monetify.feature.home

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaerushi.monetify.BuildConfig
import com.kaerushi.monetify.R
import com.kaerushi.monetify.core.ui.dialog.TextInputDialog
import com.kaerushi.monetify.core.utils.Utils.isModuleActive
import com.kaerushi.monetify.data.model.SystemInfo
import com.kaerushi.monetify.data.viewmodel.BackupEvent
import com.kaerushi.monetify.data.viewmodel.BackupStatus
import com.kaerushi.monetify.data.viewmodel.HomeViewModel
import com.kaerushi.monetify.feature.home.components.ActionsAndSupportCard
import com.kaerushi.monetify.feature.home.components.ModuleStatusCard
import com.kaerushi.monetify.feature.home.components.SystemInfoCard

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState
) {
    val moduleTitle = if (isModuleActive) stringResource(R.string.module_active_title) else stringResource(R.string.module_inactive_title)
    val moduleSubtitle = if (isModuleActive) BuildConfig.VERSION_NAME else stringResource(R.string.module_inactive_subtitle)
    var showExportDialog by rememberSaveable { mutableStateOf(false) }
    var exportName by rememberSaveable { mutableStateOf("") }
    var showImportConfirmDialog by rememberSaveable { mutableStateOf(false) }
    var importConfigName by rememberSaveable { mutableStateOf("") }
    var pendingImportUri by rememberSaveable { mutableStateOf<String?>(null) }

    val backupStatus by viewModel.backupStatus.collectAsStateWithLifecycle()

    val deviceInfo = listOf(
        SystemInfo(stringResource(R.string.android_version_title), Build.VERSION.SDK_INT.toString()),
        SystemInfo(stringResource(R.string.device_model_title), Build.BOARD),
        SystemInfo(stringResource(R.string.manufacturer_title), Build.MANUFACTURER)
    )

    // Export launcher
    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        uri?.let {
            viewModel.exportConfig(it, exportName.ifEmpty { "Monetify Config" })
        }
    }

    // Import launcher
    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            // Validate config first
            viewModel.validateConfig(it) { config ->
                importConfigName = config.name
                pendingImportUri = it.toString()
                showImportConfirmDialog = true
            }
        }
    }

    // Handle backup state changes
    LaunchedEffect(Unit) {
        viewModel.backupEvents.collect { event ->
            when (event) {
                is BackupEvent.Success -> {
                    snackBarHostState.showSnackbar(event.message)
                    viewModel.resetBackupState()
                }

                is BackupEvent.Error -> {
                    snackBarHostState.showSnackbar(event.message)
                    viewModel.resetBackupState()
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item { ModuleStatusCard(moduleTitle, moduleSubtitle, isModuleActive) }
        item { SystemInfoCard(deviceInfo) }
        item {
            ActionsAndSupportCard(
                onImport = {
                    importLauncher.launch(arrayOf("application/json"))
                },
                onExport = {
                    showExportDialog = true
                }
            )
        }
    }

    // Export dialog
    if (showExportDialog) {
        TextInputDialog(
            title = stringResource(R.string.export_config_title),
            label = stringResource(R.string.config_name),
            initialText = exportName,
            onConfirm = {
                showExportDialog = false
                val fileName = "$it.json"
                exportName = it
                exportLauncher.launch(fileName)
            },
            onDismiss = { showExportDialog = false },
            confirmText = stringResource(R.string.export),
            dismissText = stringResource(R.string.cancel)
        )
    }

    // Import confirmation dialog
    if (showImportConfirmDialog) {
        AlertDialog(
            onDismissRequest = {
                showImportConfirmDialog = false
                pendingImportUri = null
            },
            title = { Text(stringResource(R.string.import_configuration_title)) },
            text = {
                Text(stringResource(R.string.import_config_subtitle, importConfigName))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        pendingImportUri?.let { uriString ->
                            uriString.toUri().let { uri ->
                                viewModel.importConfig(uri)
                            }
                        }
                        showImportConfirmDialog = false
                        pendingImportUri = null
                    }
                ) {
                    Text(stringResource(R.string.import_title))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showImportConfirmDialog = false
                        pendingImportUri = null
                    }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    // Loading dialog
    if (backupStatus is BackupStatus.Loading) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(stringResource(R.string.please_wait)) },
            text = {
                CircularProgressIndicator()
            },
            confirmButton = { }
        )
    }
}