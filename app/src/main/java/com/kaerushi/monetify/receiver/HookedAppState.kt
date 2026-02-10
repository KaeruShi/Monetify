package com.kaerushi.monetify.receiver

import kotlinx.coroutines.flow.MutableStateFlow

object HookedAppState {
    private val _hooked = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val hooked = _hooked

    fun setHooked(pkg: String, hooked: Boolean) {
        _hooked.value += (pkg to hooked)
    }

    fun setAll(map: Map<String, Boolean>) {
        _hooked.value = map
    }
}