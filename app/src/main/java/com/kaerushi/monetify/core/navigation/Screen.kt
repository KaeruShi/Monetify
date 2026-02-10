package com.kaerushi.monetify.core.navigation

import androidx.annotation.StringRes
import com.kaerushi.monetify.R

sealed class Screen(val route: String, @param:StringRes val titleResId: Int) {
    object Home : Screen("home", R.string.app_name)
    object Apps : Screen("apps", R.string.apps_title)
    object Settings : Screen("settings", R.string.settings_title)
}