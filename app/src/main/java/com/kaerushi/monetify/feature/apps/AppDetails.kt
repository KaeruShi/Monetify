package com.kaerushi.monetify.feature.apps

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.kaerushi.monetify.core.ui.components.PreferenceItem
import com.kaerushi.monetify.core.ui.components.PreferenceSwitch
import com.kaerushi.monetify.core.ui.components.PreferenceType

@Composable
fun AppDetailScreen() {
    Column {
        PreferenceSwitch(
            checked = false,
            onCheckedChange = {},
            title = "Enable Monet",
            summary = "Apply system dynamic colors",
            type = PreferenceType.TOP,
            true
        )
        PreferenceSwitch(
            checked = false,
            onCheckedChange = {},
            title = "Disable Ads (BETA)",
            summary = "Remove ads services",
            type = PreferenceType.MID,
            isChild = true
        )
        PreferenceItem(
            onClick = {},
            title = "Icon Pack",
            summary = "Default",
            type = PreferenceType.BOTTOM,
            isChild = true
        )
    }
}