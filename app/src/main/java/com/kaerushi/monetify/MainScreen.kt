package com.kaerushi.monetify

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kaerushi.monetify.core.manager.allPermissionsGranted
import com.kaerushi.monetify.core.ui.AppNav
import com.kaerushi.monetify.data.viewmodel.HomeViewModel
import com.kaerushi.monetify.feature.welcome.WelcomeScreen

@Composable
fun MainScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val welcomeScreenState by viewModel.welcomeScreenState.collectAsState()
    val shouldShowWelcome = welcomeScreenState && !allPermissionsGranted(LocalContext.current)

    Surface(modifier = Modifier.fillMaxSize()) {
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
            if (isWelcome) WelcomeScreen() else AppNav()
        }
    }

}