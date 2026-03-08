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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kaerushi.monetify.BuildConfig
import com.kaerushi.monetify.R
import com.kaerushi.monetify.core.ui.components.PreferenceCategory
import com.kaerushi.monetify.core.ui.components.PreferenceItem
import com.kaerushi.monetify.core.ui.components.PreferenceSwitch
import com.kaerushi.monetify.core.ui.components.PreferenceType
import com.kaerushi.monetify.core.ui.dialog.AlertDialog
import com.kaerushi.monetify.core.ui.dialog.RadioSelectionDialog
import com.kaerushi.monetify.data.viewmodel.AppTheme
import com.kaerushi.monetify.data.viewmodel.ColorSchemeMode
import com.kaerushi.monetify.data.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    onNavigateToAbout: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val selectedTheme by viewModel.themeState.collectAsState()
    val selectedColorScheme by viewModel.colorSchemeState.collectAsState()
    val selectedLanguage by viewModel.languageState.collectAsState()
    val killBeforeLaunch by viewModel.killBeforeLaunchState.collectAsState()
    var showThemePreference by rememberSaveable { mutableStateOf(false) }
    var showColorPreference by rememberSaveable { mutableStateOf(false) }
    var showLanguagePreference by rememberSaveable { mutableStateOf(false) }
    var showResetDefaultDialog by rememberSaveable { mutableStateOf(false) }
    val themeLabel = selectedTheme.name.lowercase().replaceFirstChar { it.uppercase() }
    val colorLabel = selectedColorScheme.name.lowercase().replaceFirstChar { it.uppercase() }
    val languageLabel = selectedLanguage.name.lowercase().replaceFirstChar { it.uppercase() }
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
//                PreferenceCategory("Appearance")
                PreferenceItem(
                    onClick = { showThemePreference = true },
                    title = stringResource(R.string.theme_title),
                    summary = themeLabel,
                    icon = R.drawable.dark_theme,
                    type = PreferenceType.TOP
                )
                PreferenceItem(
                    onClick = { showColorPreference = true },
                    title = stringResource(R.string.color_scheme_title),
                    summary = colorLabel,
                    icon = R.drawable.color_scheme
                )
                PreferenceItem(
                    onClick = { showLanguagePreference = true },
                    title = stringResource(R.string.language_title),
                    summary = languageLabel,
                    icon = R.drawable.language_outline,
                    type = PreferenceType.BOTTOM
                )

                PreferenceCategory(stringResource(R.string.advanced))
                PreferenceItem(
                    onClick = { showResetDefaultDialog = true },
                    title = stringResource(R.string.reset_default_title),
                    summary = stringResource(R.string.reset_default_subtitle),
                    icon = R.drawable.reset_wrench_rounded,
                    type = PreferenceType.TOP
                )
                PreferenceSwitch(
                    title = stringResource(R.string.kill_before_launch_title),
                    summary = stringResource(R.string.kill_before_launch_subtitle),
                    icon = R.drawable.info_square,
                    checked = killBeforeLaunch,
                    onCheckedChange = { viewModel.setKillBeforeLaunch(it) },
                    type = PreferenceType.BOTTOM
                )

                PreferenceCategory(stringResource(R.string.about_title))
                PreferenceItem(
                    onClick = onNavigateToAbout,
                    title = stringResource(R.string.app_name),
                    summary = BuildConfig.VERSION_NAME,
                    type = PreferenceType.TOP,
                    icon = R.drawable.monetify_icon_mono
                )
                PreferenceItem(
                    onClick = {
                        uriHandler.openUri("https://ko-fi.com/kaerushi")
                    },
                    title = stringResource(R.string.support_title),
                    summary = stringResource(R.string.support_subtitle),
                    icon = R.drawable.star_fall_linear,
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
            optionText = { it.name.lowercase().replaceFirstChar { it.uppercase() } },
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
            optionText = { it -> it.name.lowercase().replaceFirstChar { it.uppercase() } },
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
            optionText = { language -> language.name.lowercase().replaceFirstChar { it.uppercase() } },
            onSelect = {
                viewModel.setAppLanguage(context, it)
            },
            onDismiss = { showLanguagePreference = false },
            dismissText = stringResource(R.string.close_title)
        )
    }
    if (showResetDefaultDialog) {
        AlertDialog(
            title = stringResource(R.string.are_you_sure),
            desc = stringResource(R.string.reset_xposed_desc),
            onConfirm = {
                viewModel.resetToDefaults()
                showResetDefaultDialog = false
            },
            onDismiss = { showResetDefaultDialog = false },
            confirmText = stringResource(R.string.yes),
            dismissText = stringResource(R.string.cancel)
        )
    }
}

enum class AppLanguage(val code: String) {
    ENGLISH("en"), INDONESIA("in"), CHINESE("zh-CN"), PORTUGUESE("pt"), RUSSIAN("ru")
}
