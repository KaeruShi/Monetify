package com.kaerushi.monetify.core.manager

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.kaerushi.monetify.BuildConfig
import com.topjohnwu.superuser.Shell

data class PermissionState(
    val rootGranted: State<Boolean>,
    val storageGranted: State<Boolean>,
    val notificationGranted: State<Boolean>
)

fun isRootGranted(): Boolean = Shell.getShell().isRoot
fun isStorageGranted(): Boolean = Environment.isExternalStorageManager()
fun isNotificationGranted(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}

fun requestStorage(context: Context) {
    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
        data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
    }
    context.startActivity(intent)
}

fun requestNotification(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
        !isNotificationGranted(context)
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            1001
        )
    }
}

fun allPermissionsGranted(context: Context): Boolean {
    return isRootGranted() && isStorageGranted() && isNotificationGranted(context)
}

@Composable
fun rememberPermissionState(context: Context): PermissionState {
    val lifecycleOwner = LocalLifecycleOwner.current
    val rootGranted = remember { mutableStateOf(isRootGranted()) }
    val storageGranted = remember { mutableStateOf(isStorageGranted()) }
    val notificationGranted = remember { mutableStateOf(isNotificationGranted(context)) }

    fun refresh() {
        rootGranted.value = isRootGranted()
        storageGranted.value = isStorageGranted()
        notificationGranted.value = isNotificationGranted(context)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver {_, event ->
            if (event == Lifecycle.Event.ON_RESUME) refresh()
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        refresh()
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    return PermissionState(rootGranted, storageGranted, notificationGranted)
}
