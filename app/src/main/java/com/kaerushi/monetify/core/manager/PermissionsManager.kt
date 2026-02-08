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

fun isRootGranted(): Boolean {
    return Shell.getShell().isRoot
}

fun isStorageGranted(): Boolean {
    return Environment.isExternalStorageManager()
}
fun requestStorage(context: Context) {
    val intent = Intent()
    intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
    intent.data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
    (context as Activity).startActivityForResult(intent, 0)
    ActivityCompat.requestPermissions(
        context, arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE
        ), 0
    )
}

fun isNotificationGranted(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}
fun requestNotification(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            // Permission already granted
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
    }
}

fun allPermissionsGranted(context: Context): Boolean {
    return isRootGranted() && isStorageGranted() && isNotificationGranted(context)
}

@Composable
fun rememberPermissionState(context: Context): Triple<State<Boolean>, State<Boolean>, State<Boolean>> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val rootGranted = remember { mutableStateOf(false) }
    val storageGranted = remember { mutableStateOf(false) }
    val notificationGranted = remember { mutableStateOf(false) }

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

    return Triple(rootGranted, storageGranted, notificationGranted)
}
