package com.kaerushi.monetify.feature.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kaerushi.monetify.data.model.SystemInfo
import com.kaerushi.monetify.data.viewmodel.HomeViewModel
import com.kaerushi.monetify.feature.apps.utils.Utils.getInstalledApps

@Composable
fun SystemInfoCard(systemInfo: List<SystemInfo>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column {
            SystemInfoList(systemInfo)
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 0.5.dp)
            HookedAppList()
        }
    }
}

@Composable
private fun SystemInfoList(systemInfo: List<SystemInfo>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        systemInfo.forEach { info ->
            TextInfo(info)
        }
    }
}

@Composable
private fun HookedAppList(viewModel: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val hooked by viewModel.hookedAppsState.collectAsState()
    var showHookedApps by remember { mutableStateOf(false) }
    val apps = remember { getInstalledApps(context, false) }
    val rotateIcon by animateFloatAsState(
        targetValue = if (showHookedApps) -180f else 0f,
        animationSpec = tween(300),
        label = "rotation"
    )

    Card(
        shape = RoundedCornerShape(0.dp),
        onClick = { showHookedApps = !showHookedApps },
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Apps Hooked")
                    Text(if (!showHookedApps) "Tap to show" else "Tap to hide", fontSize = 14.sp)
                }
                Icon(
                    modifier = Modifier.padding(end = 6.dp).rotate(rotateIcon),
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (showHookedApps) "Collapse" else "Expand",
                )
            }
            AnimatedVisibility(
                visible = showHookedApps,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, end = 16.dp)) {
                    apps.forEachIndexed { _, info ->
                        val hookedApp = hooked[info.packageName] == true
                        PackageStatus(info.packageName, hookedApp)
                    }
                }
            }
        }
    }
}

@Composable
private fun PackageStatus(pkgName: String, hooked: Boolean) {
    val itemColor = if (!hooked) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.tertiary
    val itemIcon = if (!hooked) Icons.Default.Clear else Icons.Default.Check
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Icon(imageVector = itemIcon,contentDescription = null, modifier = Modifier.size(14.dp),
            tint = itemColor
        )
        Text(pkgName, fontSize = 14.sp, color = itemColor)
    }
}
@Composable
private fun TextInfo(deviceInfo: SystemInfo) {
    Box {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(deviceInfo.title)
            Text(deviceInfo.summary, fontSize = 14.sp)
        }
    }
}