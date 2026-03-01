package com.kaerushi.monetify.core.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.kaerushi.monetify.R

sealed class BottomNavItem(
    val route: String, @param:StringRes val labelResId: Int, val selectedIcon: ImageVector, val unselectedIcon: ImageVector
) {
    object Home : BottomNavItem(
        Screen.Home.route, R.string.home_title, Icons.Filled.Home, Icons.Outlined.Home
    )

    object Apps : BottomNavItem(
        Screen.Apps.route, R.string.apps_title, Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder
    )

    object Settings : BottomNavItem(
        Screen.Settings.route, R.string.settings_title, Icons.Filled.Settings, Icons.Outlined.Settings
    )
}

fun getNavItems() = listOf(
    BottomNavItem.Home, BottomNavItem.Apps, BottomNavItem.Settings
)