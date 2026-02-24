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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kaerushi.monetify.BuildConfig
import com.kaerushi.monetify.R
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
                    title = stringResource(R.string.theme_title),
                    summary = themeLabel,
                    type = PreferenceType.TOP
                )
                PreferenceItem(onClick = { showColorPreference = true }, title = stringResource(R.string.color_scheme_title), summary = colorLabel)
                PreferenceItem(
                    onClick = { showLanguagePreference = true },
                    title = stringResource(R.string.language_title),
                    summary = languageLabel,
                    type = PreferenceType.BOTTOM
                )

                PreferenceCategory(stringResource(R.string.about_title))
                PreferenceItem(
                    onClick = {},
                    title = stringResource(R.string.app_name),
                    summary = BuildConfig.VERSION_NAME,
                    type = PreferenceType.TOP
                )
                PreferenceItem(onClick = {}, title = stringResource(R.string.credits_title), summary = stringResource(R.string.credits_subtitle))
                PreferenceItem(
                    onClick = {
                        uriHandler.openUri("https://ko-fi.com/kaerushi")
                    },
                    title = stringResource(R.string.support_title),
                    summary = stringResource(R.string.support_subtitle),
                    type = PreferenceType.BOTTOM
                )
            }
        }
    }

    if (showThemePreference) {
        RadioSelectionDialog(
            title = stringResource(R.string.theme_title),
            options = AppTheme.entries,
            selected = selectedTheme,
            optionText = { it.name.lowercase().replaceFirstChar { c -> c.uppercase() } },
            onSelect = { appTheme -> viewModel.setAppTheme(appTheme) },
            onDismiss = { showThemePreference = false },
            dismissText = stringResource(R.string.close_title)
        )
    }
    if (showColorPreference) {
        RadioSelectionDialog(
            title = stringResource(R.string.color_scheme_title),
            options = ColorSchemeMode.entries,
            selected = selectedColorScheme,
            optionText = { it.name.lowercase().replaceFirstChar { c -> c.uppercase() } },
            onSelect = { viewModel.setAppColorScheme(it) },
            onDismiss = { showColorPreference = false },
            dismissText = stringResource(R.string.close_title)
        )
    }
    if (showLanguagePreference) {
        RadioSelectionDialog(
            title = stringResource(R.string.language_title),
            options = AppLanguage.entries,
            selected = selectedLanguage,
            optionText = { it.name.lowercase().replaceFirstChar { c -> c.uppercase() } },
            onSelect = { selectedLanguage = it },
            onDismiss = { showLanguagePreference = false },
            dismissText = stringResource(R.string.close_title)
        )
    }
}

enum class AppLanguage {
    ENGLISH, INDONESIA, CHINESE
}
