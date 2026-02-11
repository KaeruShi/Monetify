package com.kaerushi.monetify.core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kaerushi.monetify.core.ui.theme.color.BlueDarkColorScheme
import com.kaerushi.monetify.core.ui.theme.color.BlueLightColorScheme
import com.kaerushi.monetify.core.ui.theme.color.GreenDarkColorScheme
import com.kaerushi.monetify.core.ui.theme.color.GreenLightColorScheme
import com.kaerushi.monetify.core.ui.theme.color.RedDarkColorScheme
import com.kaerushi.monetify.core.ui.theme.color.RedLightColorScheme
import com.kaerushi.monetify.data.viewmodel.AppTheme
import com.kaerushi.monetify.data.viewmodel.ColorSchemeMode
import com.kaerushi.monetify.data.viewmodel.SettingsViewModel

@Composable
fun MonetifyTheme(
    viewModel: SettingsViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val appTheme by viewModel.themeState.collectAsState()
    val appColorScheme by viewModel.colorSchemeState.collectAsState()
    val darkTheme = when(appTheme) {
        AppTheme.SYSTEM -> isSystemInDarkTheme()
        AppTheme.LIGHT -> false
        AppTheme.DARK -> true
    }
    val colorSchemeMode = when (appColorScheme) {
        ColorSchemeMode.DYNAMIC -> ColorSchemeMode.DYNAMIC
        ColorSchemeMode.RED -> ColorSchemeMode.RED
        ColorSchemeMode.GREEN -> ColorSchemeMode.GREEN
        ColorSchemeMode.BLUE -> ColorSchemeMode.BLUE
    }
    val colorScheme = when (colorSchemeMode) {
        ColorSchemeMode.DYNAMIC -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        ColorSchemeMode.RED -> {
            if (darkTheme) RedDarkColorScheme else RedLightColorScheme
        }
        ColorSchemeMode.GREEN -> {
            if (darkTheme) GreenDarkColorScheme else GreenLightColorScheme
        }
        ColorSchemeMode.BLUE -> {
            if (darkTheme) BlueDarkColorScheme else BlueLightColorScheme
        }
    }
    ApplySystemBars(darkTheme = darkTheme)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun ApplySystemBars(darkTheme: Boolean) {
    val view = LocalView.current
    DisposableEffect(darkTheme) {
        val window = (view.context as Activity).window

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowInsetsControllerCompat(window, view)
        controller.isAppearanceLightStatusBars = !darkTheme
        controller.isAppearanceLightNavigationBars = !darkTheme

        onDispose { }
    }
}
