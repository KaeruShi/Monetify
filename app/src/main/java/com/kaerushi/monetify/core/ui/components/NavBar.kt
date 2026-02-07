package com.kaerushi.monetify.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kaerushi.monetify.core.navigation.Screen
import com.kaerushi.monetify.data.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    onShowChangelog: () -> Unit,
    mainViewModel: MainViewModel
) {
    val showNotInstalled by mainViewModel.uiState.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val title = when (currentRoute) {
        Screen.Home.route -> Screen.Home.title
        Screen.Apps.route -> Screen.Apps.title
        Screen.Settings.route -> Screen.Settings.title
        else -> "Weeabooify"
    }
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (currentRoute == Screen.Home.route) {
                IconButton(onClick = {/* DO NOTHING*/ }) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    offset = DpOffset(x = (-12).dp, y = 0.dp),
                    modifier = Modifier.width(180.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    if (currentRoute == Screen.Apps.route) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    if (!showNotInstalled) "Show Not Installed" else "Hide Not Installed",
                                    modifier = Modifier.padding(start = 6.dp)
                                )
                            },
                            onClick = {
                                showMenu = false
                                mainViewModel.toggleNotInstalled(!showNotInstalled)
                            }
                        )
                    }
                    DropdownMenuItem(
                        text = { Text("Changelog", modifier = Modifier.padding(start = 6.dp)) },
                        onClick = {
                            showMenu = false
                            onShowChangelog()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Check For Updates", modifier = Modifier.padding(start = 6.dp)) },
                        onClick = {
                            showMenu = false
                        }
                    )
                }
            }
        },
        title = {
            Text(
                text = title,
                modifier = Modifier.padding(start = if (currentRoute != Screen.Home.route) 6.dp else 0.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    )
}