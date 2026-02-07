package com.kaerushi.monetify.core.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kaerushi.monetify.core.navigation.getNavItems

@Composable
fun BottomNavBar(navController: NavController) {
    val items = getNavItems()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        items.forEach { routes ->
            val selected = currentDestination?.hierarchy?.any {
                it.route == routes.route
            } == true
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (selected) routes.selectedIcon else routes.unselectedIcon,
                        contentDescription = routes.label
                    )
                },
                label = { Text(routes.label) },
                selected = selected,
                onClick = {
                    navController.navigate(routes.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}