package com.kaerushi.monetify.feature.apps

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kaerushi.monetify.R
import com.kaerushi.monetify.core.ui.components.PreferenceItem
import com.kaerushi.monetify.core.ui.components.PreferenceSwitch
import com.kaerushi.monetify.core.ui.components.PreferenceType
import com.kaerushi.monetify.data.viewmodel.AppsViewModel

@Composable
fun AppDetails(packageName: String, viewModel: AppsViewModel = hiltViewModel()) {
    val enableMonet by viewModel.enableMonetState(packageName).collectAsState()
    val disableAds by viewModel.disableAdsState(packageName).collectAsState()
    val iconPack by viewModel.iconPackState(packageName).collectAsState()

    Column {
        PreferenceSwitch(
            checked = enableMonet,
            onCheckedChange = {viewModel.setMonetEnabled(packageName, it) },
            title = stringResource(R.string.enable_monet_title),
            summary = stringResource(R.string.enable_monet_subtitle),
            type = PreferenceType.TOP,
            true
        )
        PreferenceSwitch(
            checked = disableAds,
            onCheckedChange = { viewModel.setAdsDisabled(packageName, it) },
            title = stringResource(R.string.disable_ads_title),
            summary = stringResource(R.string.disable_ads_subtitle),
            type = PreferenceType.MID,
            isChild = true
        )
        PreferenceItem(
            onClick = { viewModel.toggleShowIconPack(true) },
            title = stringResource(R.string.icon_pack_tile),
            summary = iconPack.toString().lowercase().replaceFirstChar { it.uppercase() },
            type = PreferenceType.BOTTOM,
            isChild = true
        )
    }
}