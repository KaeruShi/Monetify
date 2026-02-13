package com.kaerushi.monetify.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kaerushi.monetify.data.ACTION_HOOK_STATUS
import com.kaerushi.monetify.data.EXTRA_HOOKED
import com.kaerushi.monetify.data.EXTRA_PACKAGE
import com.kaerushi.monetify.data.repository.PreferencesRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HookStatusReceiver : BroadcastReceiver() {
    @Inject lateinit var repository: PreferencesRepository
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ACTION_HOOK_STATUS) return
        val pkg = intent.getStringExtra(EXTRA_PACKAGE) ?: return
        val hooked = intent.getBooleanExtra(EXTRA_HOOKED, false)
        HookedAppState.setHooked(pkg, hooked)

        val pending = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.setAppHooked(pkg, hooked)
            } finally {
                pending.finish()
            }
        }
    }
}