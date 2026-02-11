package com.kaerushi.monetify.feature.welcome

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kaerushi.monetify.R
import com.kaerushi.monetify.core.manager.rememberPermissionState
import com.kaerushi.monetify.core.manager.requestNotification
import com.kaerushi.monetify.core.manager.requestStorage
import com.kaerushi.monetify.data.viewmodel.HomeViewModel

@Composable
fun WelcomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val (rootGranted, storageGranted, notificationGranted) = rememberPermissionState(context)
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(), verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    modifier = Modifier
                        .size(128.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Welcome to Monetify!",
                    fontSize = 32.sp,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = stringResource(R.string.monetify_slogan),
                    fontSize = 14.sp,
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    )
                )
                Spacer(modifier = Modifier.height(72.dp))
                Text("Permissions")
                // List permissions required by the app
                Spacer(modifier = Modifier.height(8.dp))
                PermissionItem(
                    icon = R.drawable.supersu,
                    permissionName = "Root Access",
                    permissionDescription = "Required for applying themes to apps",
                    onClick = {
                        if (!rootGranted.value) {
                            Toast.makeText(context, "Root access denied", Toast.LENGTH_SHORT).show()
                        }
                    },
                    isGranted = rootGranted.value
                )
                Spacer(modifier = Modifier.height(6.dp))
                PermissionItem(
                    icon = R.drawable.storage,
                    permissionName = "Storage Access",
                    permissionDescription = "Needed to read and apply custom themes",
                    onClick = {
                        if (!storageGranted.value) requestStorage(context)
                    },
                    isGranted = storageGranted.value
                )
                Spacer(modifier = Modifier.height(6.dp))
                PermissionItem(
                    icon = R.drawable.notification,
                    permissionName = "Notification Access",
                    permissionDescription = "To customize notification colors and styles",
                    onClick = {
                        if (!notificationGranted.value) requestNotification(context)
                    },
                    isGranted = notificationGranted.value
                )
            }
            Button(
                onClick = { viewModel.toggleShowWelcomeScreen(false) },
                enabled = rootGranted.value && storageGranted.value && notificationGranted.value,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 32.dp)
            ) {
                Text("Continue")
            }
        }
    }
}

@Composable
fun PermissionItem(
    icon: Int,
    permissionName: String,
    permissionDescription: String,
    onClick: () -> Unit = {},
    isGranted: Boolean = false,
) {
    val containerColor = if (isGranted) MaterialTheme.colorScheme.primaryContainer
    else MaterialTheme.colorScheme.surfaceContainer

    val textColor = if (isGranted) MaterialTheme.colorScheme.onPrimaryContainer
    else MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .background(color = containerColor, shape = RoundedCornerShape(16.dp))
            .padding(vertical = 12.dp, horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isGranted) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(99.dp)
                    )
                    .padding(6.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(99.dp)
                    )
                    .padding(6.dp),
                tint = textColor
            )
        }
        Spacer(modifier = Modifier.padding(6.dp))
        Column {
            Text(
                text = permissionName, fontSize = 16.sp, style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                color = textColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = permissionDescription, fontSize = 14.sp, style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                color = textColor
            )
        }
    }
}