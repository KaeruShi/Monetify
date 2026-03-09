@file:OptIn(ExperimentalMaterial3Api::class)

package com.kaerushi.monetify.core.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kaerushi.monetify.R
import com.kaerushi.monetify.core.ui.AboutScreen
import com.kaerushi.monetify.core.ui.components.BottomNavBar
import com.kaerushi.monetify.core.ui.components.ChangelogBottomSheet
import com.kaerushi.monetify.core.ui.components.NavBar
import com.kaerushi.monetify.core.ui.dialog.AlertDialog
import com.kaerushi.monetify.data.viewmodel.ChangelogViewModel
import com.kaerushi.monetify.data.viewmodel.UpdateEvent
import com.kaerushi.monetify.feature.apps.AppsScreen
import com.kaerushi.monetify.feature.home.HomeScreen
import com.kaerushi.monetify.feature.settings.SettingsScreen

@Composable
fun AppNav() {
    val cnViewModel: ChangelogViewModel = viewModel()
    val release by cnViewModel.release.collectAsState()
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Separate scroll behaviors for each top-level destination to preserve their scroll state independently
    val homeScrollBehavior = topAppBarScrollBehavior()
    val appsScrollBehavior = topAppBarScrollBehavior()
    val settingsScrollBehavior = topAppBarScrollBehavior()
    val aboutScrollBehavior = topAppBarScrollBehavior()

    val scrollBehavior = when (currentRoute) {
        Screen.Apps.route -> appsScrollBehavior
        Screen.Settings.route -> settingsScrollBehavior
        Screen.About.route -> aboutScrollBehavior
        else -> homeScrollBehavior
    }

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    val uriHandler = LocalUriHandler.current
    val latestMsg = stringResource(R.string.already_on_the_latest_version)

    LaunchedEffect(Unit) {
        cnViewModel.event.collect { event ->
            when (event) {
                UpdateEvent.UpdateAvailable -> showUpdateDialog = true
                UpdateEvent.LatestVersion -> snackBarHostState.showSnackbar(latestMsg)
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NavBar(
                navController = navController,
                scrollBehavior = scrollBehavior,
                onShowChangelog = { isSheetOpen = true }
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
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
            composable(route = Screen.Apps.route) { AppsScreen() }
            composable(route = Screen.Settings.route) {
                SettingsScreen(
                    onNavigateToAbout = { navController.navigate(Screen.About.route) }
                )
            }
            composable(route = Screen.About.route) { AboutScreen() }
        }
        if (isSheetOpen) {
            ChangelogBottomSheet(
                onDismiss = { isSheetOpen = false },
                sheetState = sheetState
            )
        }

        if (showUpdateDialog) {
            AlertDialog(
                title = stringResource(R.string.update_available),
                content = {
                    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
                        Text(stringResource(R.string.version, release?.tagName ?: "is null"),
                            fontWeight = FontWeight.Bold)
                        Text(
                            stringResource(R.string.changelog, release?.body ?: "Failed to retrieve changelog"),
                            fontSize = 14.sp
                        )
                    }
                },
                onDismiss = { showUpdateDialog = false },
                onConfirm = {
                    showUpdateDialog = false
                    uriHandler.openUri(release!!.htmlUrl)
                },
                confirmText = stringResource(R.string.update)
            )
        }
    }
}

@Composable
private fun topAppBarScrollBehavior(): TopAppBarScrollBehavior {
    return TopAppBarDefaults.pinnedScrollBehavior(rememberSaveable(saver = TopAppBarState.Saver) {
        TopAppBarState(-Float.MAX_VALUE, 0f, 0f)
    })
}

// Helper function to determine navigation direction
private fun isForwardNavigation(from: String?, to: String?): Boolean {
    val order = listOf(Screen.Home.route, Screen.Apps.route, Screen.Settings.route, Screen.About.route)
    val fromIndex = order.indexOf(from)
    val toIndex = order.indexOf(to)
    return toIndex > fromIndex
}
