package com.kaerushi.monetify.core.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kaerushi.monetify.data.datastore.UserPreferencesRepository
import com.kaerushi.monetify.data.viewmodel.AppIconPackViewModel
import com.kaerushi.monetify.data.viewmodel.ColorSchemeViewModel
import com.kaerushi.monetify.data.viewmodel.ThemeViewModel
import com.kaerushi.monetify.data.viewmodel.MainViewModel
import com.kaerushi.monetify.feature.apps.AppsScreen
import com.kaerushi.monetify.feature.home.HomeScreen
import com.kaerushi.monetify.feature.settings.SettingsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel,
    colorSchemeViewModel: ColorSchemeViewModel,
    mainViewModel: MainViewModel,
    appIconPackViewModel: AppIconPackViewModel,
    repository: UserPreferencesRepository
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
        enterTransition = {
            val isForward = isForwardNavigation(initialState.destination.route, targetState.destination.route)
            fadeIn(
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + slideInHorizontally(
                initialOffsetX = { if (isForward) it / 2 else -it / 2 },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + scaleIn(
                initialScale = 0.85f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            )
        },
        exitTransition = {
            val isForward = isForwardNavigation(initialState.destination.route, targetState.destination.route)
            fadeOut(
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + slideOutHorizontally(
                targetOffsetX = { if (isForward) -it / 2 else it / 2 },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + scaleOut(
                targetScale = 0.85f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            )
        },
        popEnterTransition = {
            fadeIn(
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + slideInHorizontally(
                initialOffsetX = { -it / 2 },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + scaleIn(
                initialScale = 0.85f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + slideOutHorizontally(
                targetOffsetX = { it / 2 },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + scaleOut(
                targetScale = 0.85f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            )
        }
    ) {
        composable(route = Screen.Home.route) { HomeScreen() }
        composable(route = Screen.Apps.route) { AppsScreen(mainViewModel, appIconPackViewModel, repository) }
        composable(route = Screen.Settings.route) {
            SettingsScreen(
                themeViewModel = themeViewModel,
                colorSchemeViewModel = colorSchemeViewModel
            )
        }
    }
}

// Helper function to determine navigation direction
private fun isForwardNavigation(from: String?, to: String?): Boolean {
    val order = listOf(Screen.Home.route, Screen.Apps.route, Screen.Settings.route)
    val fromIndex = order.indexOf(from)
    val toIndex = order.indexOf(to)
    return toIndex > fromIndex
}