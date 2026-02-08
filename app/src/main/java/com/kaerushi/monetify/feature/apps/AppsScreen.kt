package com.kaerushi.monetify.feature.apps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kaerushi.monetify.core.ui.components.PreferenceApp
import com.kaerushi.monetify.core.ui.components.PreferenceType
import com.kaerushi.monetify.core.ui.dialog.RadioSelectionDialog
import com.kaerushi.monetify.data.datastore.UserPreferencesRepository
import com.kaerushi.monetify.data.viewmodel.MainViewModel
import com.kaerushi.monetify.data.viewmodel.AppIconPack
import com.kaerushi.monetify.data.viewmodel.AppIconPackViewModel
import com.kaerushi.monetify.feature.apps.utils.Utils.getInstalledApps
import kotlinx.coroutines.launch

@Composable
fun AppsScreen(
    mainViewModel: MainViewModel,
    appIconPackViewModel: AppIconPackViewModel,
    repository: UserPreferencesRepository
) {
    val context = LocalContext.current
    val showNotInstalled by mainViewModel.uiState.collectAsState()
    val apps = remember(showNotInstalled) { getInstalledApps(context, showNotInstalled) }
    val showIconPack by repository.showAppIconPack.collectAsState(initial = false)
    var expandedKey by rememberSaveable { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        itemsIndexed(
            items = apps,
            key = { _, app -> app.packageName }
        ) { index, appInfo ->
            val isExpanded = expandedKey == appInfo.packageName
            val extraPadding = when {
                index == apps.size - 1 -> 16.dp
                isExpanded -> 4.dp // Add extra space when expanded
                else -> 0.dp
            }
            PreferenceApp(
                modifier = Modifier
                    .animateItem()
                    .padding(bottom = extraPadding),
                icon = appInfo.icon,
                altIcon = appInfo.altIcon,
                title = appInfo.name,
                summary = appInfo.summary,
                enabled = appInfo.enabled,
                appInfo = appInfo,
                mainViewModel = mainViewModel,
                type = when {
                    apps.size == 1 -> PreferenceType.ROUND
                    index == 0 -> PreferenceType.TOP
                    index == apps.size - 1 -> PreferenceType.BOTTOM
                    else -> PreferenceType.MID
                },
                expanded = isExpanded,
                onClick = {
                    if (!appInfo.enabled) return@PreferenceApp
                    expandedKey = if (isExpanded) null else appInfo.packageName
                }
            )
        }
    }

    if (showIconPack) {
        expandedKey?.let { pkg ->
            val selectedIconPack by repository.getAppIconPack(pkg).collectAsState(initial = AppIconPack.DEFAULT)

            RadioSelectionDialog(
                title = "Select Icon Pack",
                options = AppIconPack.entries,
                selected = selectedIconPack,
                optionText = { it -> it.toString().lowercase().replaceFirstChar { it.uppercase() } },
                onSelect = { selectedPack ->
                    appIconPackViewModel.onAppIconPackChanged(packageName = pkg, selectedPack)
                    scope.launch { repository.toggleShowAppIconPack(false) }
                },
                onDismiss = { scope.launch { repository.toggleShowAppIconPack(false) } },
                dismissText = "Close"
            )
        }
    }
}
