package com.kaerushi.monetify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaerushi.monetify.data.viewmodel.AppTheme
import com.kaerushi.monetify.core.ui.theme.ApplySystemBars
import com.kaerushi.monetify.data.viewmodel.ColorSchemeMode
import com.kaerushi.monetify.data.viewmodel.ColorSchemeViewModel
import com.kaerushi.monetify.core.ui.theme.MonetifyTheme
import com.kaerushi.monetify.data.viewmodel.ThemeViewModel
import com.kaerushi.monetify.data.datastore.UserPreferencesRepository
import com.kaerushi.monetify.data.viewmodel.MainViewModel
import com.kaerushi.monetify.data.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val colorSchemeViewModel: ColorSchemeViewModel = viewModel()
            val userPreferencesRepository = UserPreferencesRepository(applicationContext)
            val mainViewModel: MainViewModel = viewModel(
                factory = MainViewModelFactory(userPreferencesRepository)
            )
            val appTheme by themeViewModel.theme.collectAsState(initial = AppTheme.SYSTEM)
            val colorSchemeMode by colorSchemeViewModel.colorSchemeMode.collectAsState(initial = ColorSchemeMode.DYNAMIC)

            MonetifyTheme(
                darkTheme = when (appTheme) {
                    AppTheme.SYSTEM -> isSystemInDarkTheme()
                    AppTheme.LIGHT -> false
                    AppTheme.DARK -> true
                },
                colorMode = when (colorSchemeMode) {
                    ColorSchemeMode.DYNAMIC -> ColorSchemeMode.DYNAMIC
                    ColorSchemeMode.RED -> ColorSchemeMode.RED
                    ColorSchemeMode.GREEN -> ColorSchemeMode.GREEN
                    ColorSchemeMode.BLUE -> ColorSchemeMode.BLUE
                }
            ) {
                val isDark = when (appTheme) {
                    AppTheme.SYSTEM -> isSystemInDarkTheme()
                    AppTheme.LIGHT -> false
                    AppTheme.DARK -> true
                }

                ApplySystemBars(darkIcons = !isDark)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(themeViewModel, colorSchemeViewModel, mainViewModel)
                }
            }
        }
    }
}