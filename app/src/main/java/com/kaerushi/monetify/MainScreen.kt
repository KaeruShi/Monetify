package com.kaerushi.monetify

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kaerushi.monetify.core.navigation.NavGraph
import com.kaerushi.monetify.core.ui.components.BottomNavBar
import com.kaerushi.monetify.core.ui.components.ChangelogBottomSheet
import com.kaerushi.monetify.core.ui.components.NavBar
import com.kaerushi.monetify.data.datastore.UserPreferencesRepository
import com.kaerushi.monetify.data.viewmodel.AppIconPackViewModel
import com.kaerushi.monetify.data.viewmodel.ColorSchemeViewModel
import com.kaerushi.monetify.data.viewmodel.ThemeViewModel
import com.kaerushi.monetify.data.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    themeViewModel: ThemeViewModel,
    colorSchemeViewModel: ColorSchemeViewModel,
    mainViewModel: MainViewModel,
    appIconPackViewModel: AppIconPackViewModel,
    repository: UserPreferencesRepository
) {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(currentRoute) {
        scrollBehavior.state.contentOffset = 0f
        scrollBehavior.state.heightOffset = 0f
    }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NavBar(
                navController = navController,
                scrollBehavior = scrollBehavior,
                onShowChangelog = { isSheetOpen = true },
                mainViewModel = mainViewModel
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            themeViewModel = themeViewModel,
            colorSchemeViewModel = colorSchemeViewModel,
            mainViewModel = mainViewModel,
            appIconPackViewModel = appIconPackViewModel,
            repository = repository
        )
        if (isSheetOpen) {
            ChangelogBottomSheet(
                onDismiss = { isSheetOpen = false },
                sheetState = sheetState
            )
        }
    }
}
