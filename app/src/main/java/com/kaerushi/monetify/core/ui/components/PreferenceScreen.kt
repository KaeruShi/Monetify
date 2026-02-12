package com.kaerushi.monetify.core.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kaerushi.monetify.data.model.AppInfo
import com.kaerushi.monetify.data.viewmodel.AppTheme
import com.kaerushi.monetify.data.viewmodel.SettingsViewModel
import com.kaerushi.monetify.feature.apps.AppDetails

@Composable
fun childColor() =
    if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surfaceContainer
    else lerp(MaterialTheme.colorScheme.surfaceVariant, Color.Black, 0.03f)

@Composable
fun getChildColor(viewModel: SettingsViewModel = hiltViewModel()): Color {
    val appTheme by viewModel.themeState.collectAsState()
    return when (appTheme) {
        AppTheme.SYSTEM -> childColor()
        AppTheme.LIGHT -> lerp(MaterialTheme.colorScheme.surfaceVariant, Color.Black, 0.03f)
        AppTheme.DARK -> MaterialTheme.colorScheme.surfaceContainer
    }
}


@Composable
fun PreferenceCategory(title: String) {
    Text(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        text = title,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 8.dp)
    )
}

@Composable
fun PreferenceItem(
    onClick: () -> Unit, title: String, summary: String, type: PreferenceType = PreferenceType.MID,
    isChild: Boolean = false
) {
    val shape = when (type) {
        PreferenceType.MID -> RoundedCornerShape(4.dp)
        PreferenceType.TOP -> RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 4.dp, bottomEnd = 4.dp)
        PreferenceType.BOTTOM -> RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 4.dp,
            bottomStart = 24.dp,
            bottomEnd = 24.dp
        )

        PreferenceType.ROUND -> RoundedCornerShape(24.dp)
    }
    Card(
        onClick = onClick,
        shape = if (!isChild) shape else RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isChild) getChildColor()
            else MaterialTheme.colorScheme.surfaceContainerHighest
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Text(text = summary, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun PreferenceSwitch(
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    title: String,
    summary: String,
    type: PreferenceType = PreferenceType.MID,
    isChild: Boolean = false
) {
    val shape = when (type) {
        PreferenceType.MID -> RoundedCornerShape(4.dp)
        PreferenceType.TOP -> RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 4.dp, bottomEnd = 4.dp)
        PreferenceType.BOTTOM -> RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 4.dp,
            bottomStart = 24.dp,
            bottomEnd = 24.dp
        )

        PreferenceType.ROUND -> RoundedCornerShape(24.dp)
    }
    Card(
        onClick = { onCheckedChange(!checked) },
        shape = if (!isChild) shape else RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isChild) getChildColor()
            else MaterialTheme.colorScheme.surfaceContainerHighest
        )
    ) {
        Row() {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(text = title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Text(text = summary, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.CenterVertically),
                thumbContent = {
                    val thumbIcon = if (checked) Icons.Rounded.Check else Icons.Rounded.Close
                    Icon(
                        imageVector = thumbIcon,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize)
                    )
                }
            )
        }
    }
}

@Composable
fun PreferenceApp(
    modifier: Modifier = Modifier,
    icon: Drawable?,
    altIcon: Int,
    title: String,
    summary: String,
    enabled: Boolean = true,
    type: PreferenceType = PreferenceType.MID,
    expanded: Boolean,
    onClickItem: () -> Unit,
    onClickLaunch: () -> Unit,
    appInfo: AppInfo
) {
    val shape = when (type) {
        PreferenceType.MID -> RoundedCornerShape(4.dp)
        PreferenceType.TOP -> RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 4.dp, bottomEnd = 4.dp)
        PreferenceType.BOTTOM -> RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 4.dp,
            bottomStart = 24.dp,
            bottomEnd = 24.dp
        )

        PreferenceType.ROUND -> RoundedCornerShape(24.dp)
    }
    val contentAlpha = if (enabled) 1f else 0.3f
    Card(
        shape = shape,
        onClick = { onClickItem() },
        modifier = modifier.alpha(contentAlpha)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                if (icon != null) {
                    Image(
                        bitmap = icon.toBitmap().asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(42.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(altIcon),
                        contentDescription = null,
                        modifier = Modifier.size(42.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                ) {
                    Text(text = title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Text(text = summary, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                }
                AnimatedVisibility(visible = expanded,
                    enter = scaleIn(
                        initialScale = 0.6f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    ) + fadeIn(),
                    exit = scaleOut(
                        targetScale = 0.6f,
                        animationSpec = tween(250)
                    ) + fadeOut(tween(200))) {
                    IconButton(onClick = { onClickLaunch() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = CircleShape
                            )
                                .size(40.dp)
                                .padding(8.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                }
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    HorizontalDivider(modifier = Modifier.height(0.8.dp))
                    AppDetails(appInfo.packageName)
                }
            }
        }
    }
}

enum class PreferenceType {
    MID, TOP, BOTTOM, ROUND
}

