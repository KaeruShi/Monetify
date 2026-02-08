package com.kaerushi.monetify.feature.apps

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.kaerushi.monetify.core.ui.components.PreferenceItem
import com.kaerushi.monetify.core.ui.components.PreferenceSwitch
import com.kaerushi.monetify.core.ui.components.PreferenceType
import com.kaerushi.monetify.data.repository.PreferencesRepository
import com.kaerushi.monetify.data.viewmodel.AppIconPack
import kotlinx.coroutines.launch

@Composable
fun AppDetails(packageName: String, repository: PreferencesRepository) {
    val enableMonet by repository.getAppMonetEnabled(packageName).collectAsState(initial = false)
    val disableAds by repository.getAppAdsDisabled(packageName).collectAsState(initial = false)
    val iconPack by repository.getAppIconPack(packageName).collectAsState(initial = AppIconPack.DEFAULT)
    val scope = rememberCoroutineScope()

    Column {
        PreferenceSwitch(
            checked = enableMonet,
            onCheckedChange = {
                scope.launch { repository.setAppMonetEnabled(packageName, it) }
            },
            title = "Enable Monet",
            summary = "Apply system dynamic colors",
            type = PreferenceType.TOP,
            true
        )
        PreferenceSwitch(
            checked = disableAds,
            onCheckedChange = {
                scope.launch { repository.setAppAdsDisabled(packageName, it) }
            },
            title = "Disable Ads (BETA)",
            summary = "Remove ads services",
            type = PreferenceType.MID,
            isChild = true
        )
        PreferenceItem(
            onClick = {
                scope.launch {
                    repository.toggleShowAppIconPack(true)
                }
            },
            title = "Icon Pack",
            summary = iconPack.toString().lowercase().replaceFirstChar { it.uppercase() },
            type = PreferenceType.BOTTOM,
            isChild = true
        )
    }
}