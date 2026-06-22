package com.kaerushi.monetify.core.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaerushi.monetify.core.manager.allPermissionsGranted
import com.kaerushi.monetify.core.navigation.MainContent
import com.kaerushi.monetify.data.viewmodel.HomeViewModel

@Composable
fun MainScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val showWelcomeScreen by viewModel.welcomeScreenState.collectAsStateWithLifecycle()
    val shouldShowWelcome = showWelcomeScreen ?: false || !allPermissionsGranted(LocalContext.current)

    Surface(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = shouldShowWelcome,
            transitionSpec = {
                val animationSpec = tween<Float>(durationMillis = 400)
                val slideOffset = 150

                fadeIn(animationSpec = animationSpec) + slideInVertically(
                    animationSpec = tween(durationMillis = 400),
                    initialOffsetY = { slideOffset }
                ) togetherWith(
                    fadeOut(animationSpec = animationSpec) + slideOutVertically(
                        animationSpec = tween(durationMillis = 400),
                        targetOffsetY = { slideOffset }
                    )
                )
            },
            label = "MainScreenTransition"
        ) { isWelcome ->
            if (isWelcome) WelcomeScreen() else MainContent()
        }
    }
}
