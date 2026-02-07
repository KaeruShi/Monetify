package com.kaerushi.monetify.core.navigation

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Weeabooify")
    object Apps : Screen("apps", "Apps")
    object Settings : Screen("settings", "Settings")
}