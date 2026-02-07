package com.kaerushi.monetify.core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.kaerushi.monetify.core.ui.theme.color.BlueDarkColorScheme
import com.kaerushi.monetify.core.ui.theme.color.BlueLightColorScheme
import com.kaerushi.monetify.core.ui.theme.color.GreenDarkColorScheme
import com.kaerushi.monetify.core.ui.theme.color.GreenLightColorScheme
import com.kaerushi.monetify.core.ui.theme.color.RedDarkColorScheme
import com.kaerushi.monetify.core.ui.theme.color.RedLightColorScheme
import com.kaerushi.monetify.data.viewmodel.ColorSchemeMode

@Composable
fun MonetifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    colorMode: ColorSchemeMode = ColorSchemeMode.DYNAMIC,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when (colorMode) {
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun ApplySystemBars(darkIcons: Boolean) {
    val view = LocalView.current
    DisposableEffect(darkIcons) {
        val window = (view.context as Activity).window

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowInsetsControllerCompat(window, view)
        controller.isAppearanceLightStatusBars = darkIcons
        controller.isAppearanceLightNavigationBars = darkIcons

        onDispose { }
    }
}