package com.kaerushi.monetify.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaerushi.monetify.BuildConfig
import com.kaerushi.monetify.feature.home.components.ActionsAndSupportCard
import com.kaerushi.monetify.feature.home.components.ModuleStatusCard
import com.kaerushi.monetify.feature.home.components.SystemInfoCard
import com.kaerushi.monetify.feature.home.model.SystemInfo
import com.kaerushi.monetify.utils.isModuleActive

@Composable
fun HomeScreen() {
    val moduleTitle = if (isModuleActive()) "Module Active" else "Module Inactive"
    val moduleSubtitle = if (isModuleActive()) BuildConfig.VERSION_NAME else "Please active module in LSPosed."
    val deviceInfo = listOf(
        SystemInfo("Android Version", "12"),
        SystemInfo("Device Model", "redwood"),
        SystemInfo("Manufacturer", "samsung")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item { ModuleStatusCard(moduleTitle, moduleSubtitle, isModuleActive()) }
        item { SystemInfoCard(deviceInfo) }
        item { ActionsAndSupportCard() }
    }
}