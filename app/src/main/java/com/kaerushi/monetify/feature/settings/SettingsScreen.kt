package com.kaerushi.monetify.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kaerushi.monetify.BuildConfig
import com.kaerushi.monetify.core.ui.components.PreferenceCategory
import com.kaerushi.monetify.core.ui.components.PreferenceItem
import com.kaerushi.monetify.core.ui.components.PreferenceType
import com.kaerushi.monetify.core.ui.dialog.RadioSelectionDialog
import com.kaerushi.monetify.data.viewmodel.AppTheme
import com.kaerushi.monetify.data.viewmodel.ColorSchemeMode
import com.kaerushi.monetify.data.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val selectedTheme by viewModel.themeState.collectAsState()
    val selectedColorScheme by viewModel.colorSchemeState.collectAsState()
    var selectedLanguage by rememberSaveable { mutableStateOf(AppLanguage.ENGLISH) }
    var showThemePreference by rememberSaveable { mutableStateOf(false) }
    var showColorPreference by rememberSaveable { mutableStateOf(false) }
    var showLanguagePreference by rememberSaveable { mutableStateOf(false) }
    val themeLabel = selectedTheme.name.lowercase().replaceFirstChar { it.uppercase() }
    val colorLabel = selectedColorScheme.name.lowercase().replaceFirstChar { it.uppercase() }
    val languageLabel = selectedLanguage.name.lowercase().replaceFirstChar { it.uppercase() }
    val uriHandler = LocalUriHandler.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                PreferenceItem(
                    onClick = { showThemePreference = true },
                    title = "Theme",
                    summary = themeLabel,
                    type = PreferenceType.TOP
                )
                PreferenceItem(onClick = { showColorPreference = true }, title = "Color Scheme", summary = colorLabel)
                PreferenceItem(
                    onClick = { showLanguagePreference = true },
                    title = "Language",
                    summary = languageLabel,
                    type = PreferenceType.BOTTOM
                )

                PreferenceCategory("About")
                PreferenceItem(
                    onClick = {},
                    title = "Monetify",
                    summary = BuildConfig.VERSION_NAME,
                    type = PreferenceType.TOP
                )
                PreferenceItem(onClick = {}, title = "Credits", summary = "Thanks to all contributors")
                PreferenceItem(
                    onClick = {
                        uriHandler.openUri("https://ko-fi.com/kaerushi")
                    },
                    title = "Support",
                    summary = "Buy me a coffee",
                    type = PreferenceType.BOTTOM
                )
            }
        }
    }

    if (showThemePreference) {
        RadioSelectionDialog(
            title = "Theme",
            options = AppTheme.entries,
            selected = selectedTheme,
            optionText = { it.name.lowercase().replaceFirstChar { c -> c.uppercase() } },
            onSelect = { appTheme -> viewModel.setAppTheme(appTheme) },
            onDismiss = { showThemePreference = false },
            dismissText = "Close"
        )
    }
    if (showColorPreference) {
        RadioSelectionDialog(
            title = "Color Scheme",
            options = ColorSchemeMode.entries,
            selected = selectedColorScheme,
            optionText = { it.name.lowercase().replaceFirstChar { c -> c.uppercase() } },
            onSelect = { viewModel.setAppColorScheme(it) },
            onDismiss = { showColorPreference = false },
            dismissText = "Close"
        )
    }
    if (showLanguagePreference) {
        RadioSelectionDialog(
            title = "Language",
            options = AppLanguage.entries,
            selected = selectedLanguage,
            optionText = { it.name.lowercase().replaceFirstChar { c -> c.uppercase() } },
            onSelect = { selectedLanguage = it },
            onDismiss = { showLanguagePreference = false },
            dismissText = "Close"
        )
    }
}

enum class AppLanguage {
    ENGLISH, INDONESIA, CHINESE
}
