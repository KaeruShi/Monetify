package com.kaerushi.monetify.core.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.kaerushi.monetify.data.model.AppInfo
import com.kaerushi.monetify.data.viewmodel.MainViewModel
import com.kaerushi.monetify.feature.apps.AppDetails

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
            containerColor = if (isChild) MaterialTheme.colorScheme.surfaceContainerHigh
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
            containerColor = if (isChild) MaterialTheme.colorScheme.surfaceContainerHigh
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
                    .align(Alignment.CenterVertically)
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
    onClick: () -> Unit,
    appInfo: AppInfo,
    mainViewModel: MainViewModel
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
        onClick = { onClick() },
        modifier = modifier.alpha(contentAlpha)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
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
                        .fillMaxWidth()
                ) {
                    Text(text = title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Text(text = summary, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                }
            }
            AnimatedVisibility(visible = expanded) {
                HorizontalDivider(modifier = Modifier.height(1.dp))
                AppDetails(appInfo.packageName, mainViewModel.repository)
            }
        }
    }
}

enum class PreferenceType {
    MID, TOP, BOTTOM, ROUND
}

