package com.kaerushi.monetify.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kaerushi.monetify.data.repository.PreferencesRepository
import kotlinx.coroutines.launch

class AppIconPackViewModel(application: Application): AndroidViewModel(application) {
    val preferencesRepository = PreferencesRepository(application)

    fun onAppIconPackChanged(packageName: String, newPack: AppIconPack) {
        viewModelScope.launch {
            preferencesRepository.setAppIconPack(packageName, newPack)
        }
    }
}

enum class AppIconPack {
    DEFAULT, DUOLTONE, FILLED, OUTLINED
}