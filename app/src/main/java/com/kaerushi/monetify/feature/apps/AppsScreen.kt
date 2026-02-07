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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kaerushi.monetify.core.ui.components.PreferenceApp
import com.kaerushi.monetify.core.ui.components.PreferenceType
import com.kaerushi.monetify.data.viewmodel.MainViewModel
import com.kaerushi.monetify.feature.apps.utils.Utils.getInstalledApps

@Composable
fun AppsScreen(mainViewModel: MainViewModel) {
    val showNotInstalled by mainViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val apps = remember(showNotInstalled) { getInstalledApps(context, showNotInstalled) }
    var expandedKey by rememberSaveable { mutableStateOf<String?>(null) }

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
            PreferenceApp(
                modifier = Modifier.animateItem().padding(bottom = if (index == apps.size - 1) 16.dp else 0.dp),
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
}
