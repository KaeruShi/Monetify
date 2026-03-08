package com.kaerushi.monetify.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.kaerushi.monetify.BuildConfig
import com.kaerushi.monetify.data.model.ChangelogInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

class ChangelogViewModel : ViewModel() {

    private val _release = MutableStateFlow<ChangelogInfo?>(null)
    val release: StateFlow<ChangelogInfo?> = _release

    private val _updateAvailable = MutableStateFlow(false)
    val updateAvailable: StateFlow<Boolean> = _updateAvailable

    private val _event = MutableSharedFlow<UpdateEvent>()
    val event = _event.asSharedFlow()

    private fun requestRelease(): ChangelogInfo? {
        return try {
            val url = URL("https://api.github.com/repos/kaerushi/monetify/releases/latest")
            val connection = url.openConnection() as HttpURLConnection

            connection.apply {
                requestMethod = "GET"
                setRequestProperty("User-Agent", "Monetify-App")
                setRequestProperty("Accept", "application/vnd.github.v3+json")
                connectTimeout = 10000
                readTimeout = 10000
            }

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val json = connection.inputStream.bufferedReader().use { it.readText() }
                Gson().fromJson(json, ChangelogInfo::class.java)
            } else {
                val error = connection.errorStream?.bufferedReader()?.use { it.readText() }
                Log.e("Changelog", "Error: ${connection.responseCode} - $error")
                null
            }.also {
                connection.disconnect()
            }

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun fetchChangelog() {
        viewModelScope.launch(Dispatchers.IO) {
            val release = requestRelease()
            release?.let {
                _release.value = it
            }
        }
    }

    fun checkForUpdates() {
        viewModelScope.launch(Dispatchers.IO) {
            val release = requestRelease() ?: return@launch

            _release.value = release

            val latestVersion = release.tagName
            val currentVersion = BuildConfig.VERSION_NAME.removeSuffix("-debug")

            Log.d("Changelog", "latest: $latestVersion, current: $currentVersion")

            val update = latestVersion != currentVersion
            _updateAvailable.value = update

            if (update) {
                _event.emit(UpdateEvent.UpdateAvailable)
            } else {
                _event.emit(UpdateEvent.LatestVersion)
            }
        }
    }
}

sealed class UpdateEvent {
    data object UpdateAvailable : UpdateEvent()
    data object LatestVersion : UpdateEvent()
}