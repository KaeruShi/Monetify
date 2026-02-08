package com.kaerushi.monetify.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String, val label: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector
) {
    object Home : BottomNavItem(
        Screen.Home.route, "Home", Icons.Filled.Home, Icons.Outlined.Home
    )

    object Apps : BottomNavItem(
        Screen.Apps.route, "Apps", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder
    )

    object Settings : BottomNavItem(
        Screen.Settings.route, "Settings", Icons.Filled.Settings, Icons.Outlined.Settings
    )
}

fun getNavItems() = listOf(
    BottomNavItem.Home, BottomNavItem.Apps, BottomNavItem.Settings
)