package com.kaerushi.monetify.feature.apps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kaerushi.monetify.R
import com.kaerushi.monetify.core.ui.components.PreferenceApp
import com.kaerushi.monetify.core.ui.components.PreferenceType
import com.kaerushi.monetify.core.ui.dialog.RadioSelectionDialog
import com.kaerushi.monetify.data.viewmodel.AppIconPack
import com.kaerushi.monetify.data.viewmodel.AppsViewModel
import com.kaerushi.monetify.feature.apps.utils.Utils.getInstalledApps
import com.topjohnwu.superuser.Shell

@Composable
fun AppsScreen(viewModel: AppsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val showNotInstalled by viewModel.notInstalledState.collectAsState()
    val showIconPack by viewModel.showIconPackState.collectAsState()
    val apps = remember(showNotInstalled) { getInstalledApps(context.applicationContext, showNotInstalled) }
    var expandedKey by rememberSaveable { mutableStateOf<String?>(null) }

    if (apps.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(86.dp),
                painter = painterResource(R.drawable.app_not_found),
                contentDescription = "App not found",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text("No apps found :(", fontSize = 20.sp)
        }
    } else {
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
                    type = when {
                        apps.size == 1 -> PreferenceType.ROUND
                        index == 0 -> PreferenceType.TOP
                        index == apps.size - 1 -> PreferenceType.BOTTOM
                        else -> PreferenceType.MID
                    },
                    expanded = isExpanded,
                    onClickItem = {
                        if (!appInfo.enabled) return@PreferenceApp
                        expandedKey = if (isExpanded) null else appInfo.packageName
                    },
                    onClickLaunch = {
                        Shell.cmd("am force-stop ${appInfo.packageName} && monkey -p ${appInfo.packageName} -c android.intent.category.LAUNCHER 1").exec()
                    }
                )
            }
        }
    }

    if (showIconPack) {
        expandedKey?.let { pkg ->
            val selectedIconPack by viewModel.iconPackState(pkg).collectAsState()

            RadioSelectionDialog(
                title = "Select Icon Pack",
                options = AppIconPack.entries,
                selected = selectedIconPack,
                optionText = { it -> it.toString().lowercase().replaceFirstChar { it.uppercase() } },
                onSelect = { selectedPack ->
                    viewModel.setIconPack(pkg, selectedPack)
                    viewModel.toggleShowIconPack(false)
                },
                onDismiss = { viewModel.toggleShowIconPack(false) },
                dismissText = "Close"
            )
        }
    }
}
