package com.kaerushi.monetify.feature.apps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        itemsIndexed(
            items = apps,
            key = { _, app -> app.packageName } // Important for stable keys
        ) { index, appInfo ->
            PreferenceApp(
                modifier = Modifier.animateItem(),
                icon = appInfo.icon,
                altIcon = appInfo.altIcon,
                title = appInfo.name,
                summary = appInfo.summary,
                enabled = appInfo.enabled,
                type = when {
                    apps.size == 1 -> PreferenceType.ROUND
                    index == 0 -> PreferenceType.TOP
                    index == apps.size - 1 -> PreferenceType.BOTTOM
                    else -> PreferenceType.MID
                },
                onClick = { }
            )
        }
    }
}
