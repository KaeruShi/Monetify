package com.kaerushi.monetify

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaerushi.monetify.core.manager.allPermissionsGranted
import com.kaerushi.monetify.core.ui.theme.ApplySystemBars
import com.kaerushi.monetify.core.ui.theme.MonetifyTheme
import com.kaerushi.monetify.data.ACTION_HOOK_STATUS
import com.kaerushi.monetify.data.repository.PreferencesRepository
import com.kaerushi.monetify.data.viewmodel.AppIconPackViewModel
import com.kaerushi.monetify.data.viewmodel.AppTheme
import com.kaerushi.monetify.data.viewmodel.ColorSchemeMode
import com.kaerushi.monetify.data.viewmodel.ColorSchemeViewModel
import com.kaerushi.monetify.data.viewmodel.MainViewModel
import com.kaerushi.monetify.data.viewmodel.MainViewModelFactory
import com.kaerushi.monetify.data.viewmodel.ThemeViewModel
import com.kaerushi.monetify.receiver.HookStatusReceiver
import com.kaerushi.monetify.receiver.HookedAppState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val receiver = HookStatusReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            PreferencesRepository(applicationContext).hookedApps.collectLatest { map ->
                HookedAppState.setAll(map)
            }
        }
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val colorSchemeViewModel: ColorSchemeViewModel = viewModel()
            val appIconPackViewModel: AppIconPackViewModel = viewModel()
            val preferencesRepository = PreferencesRepository(applicationContext)
            val mainViewModel: MainViewModel = viewModel(
                factory = MainViewModelFactory(preferencesRepository)
            )
            val appTheme by themeViewModel.theme.collectAsState(initial = AppTheme.SYSTEM)
            val colorSchemeMode by colorSchemeViewModel.colorSchemeMode.collectAsState(initial = ColorSchemeMode.DYNAMIC)
            val showWelcome by preferencesRepository.showWelcomeScreen.collectAsState(initial = true)
            val shouldShowWelcome = showWelcome && !allPermissionsGranted(LocalContext.current)

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
                    AnimatedContent(
                        targetState = shouldShowWelcome,
                        transitionSpec = {
                            ContentTransform(
                                targetContentEnter = fadeIn(tween(220)) + scaleIn(
                                    initialScale = 0.95f,
                                    animationSpec = tween(220)
                                ),
                                initialContentExit = fadeOut(tween(180)) + scaleOut(
                                    targetScale = 0.98f,
                                    animationSpec = tween(180)
                                )
                            )
                        },
                        label = "WelcomeScreenTransition"
                    ) { isWelcome ->
                        if (isWelcome) {
                            WelcomeScreen(mainViewModel = mainViewModel)
                        } else {
                            MainScreen(
                                themeViewModel,
                                colorSchemeViewModel,
                                mainViewModel,
                                appIconPackViewModel,
                                preferencesRepository
                            )
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ACTION_HOOK_STATUS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, filter, RECEIVER_EXPORTED)
        } else {
            registerReceiver(receiver, filter)
        }
    }

    override fun onStop() {
        unregisterReceiver(receiver)
        super.onStop()
    }
}