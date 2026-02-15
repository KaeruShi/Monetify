package com.kaerushi.monetify.xposed.utils

import android.content.Context
import android.content.Intent
import com.highcapable.yukihookapi.hook.log.YLog
import com.kaerushi.monetify.data.ACTION_HOOK_STATUS
import com.kaerushi.monetify.data.EXTRA_HOOKED
import com.kaerushi.monetify.data.EXTRA_PACKAGE
import com.kaerushi.monetify.data.PACKAGE_NAME
import java.util.concurrent.ConcurrentHashMap

/**
 * Utility for managing and broadcasting hook status
 * Thread-safe implementation with deduplication
 */
object HookStatusUtil {

    private const val TAG = "HookStatusUtil"
    private val sentStatus = ConcurrentHashMap<String, Boolean>()

    /**
     * Sends a broadcast indicating hook status for a package
     * @return true if broadcast was sent successfully
     */
    fun sendHooked(context: Context, pkg: String, hooked: Boolean): Boolean =
        runCatching {
            createStatusIntent(pkg, hooked)
                .also { context.sendBroadcast(it) }
            true
        }.getOrElse { error ->
            logError(pkg, hooked, error)
            false
        }

    /**
     * Checks if status should be sent for a package
     * Prevents duplicate status broadcasts
     */
    fun shouldSend(pkg: String): Boolean =
        sentStatus.putIfAbsent(pkg, true) == null

    /**
     * Clears the sent status cache
     * Useful for testing or resetting state
     */
    fun clearCache() = sentStatus.clear()

    private fun createStatusIntent(pkg: String, hooked: Boolean) =
        Intent(ACTION_HOOK_STATUS).apply {
            setPackage(PACKAGE_NAME)
            putExtra(EXTRA_PACKAGE, pkg)
            putExtra(EXTRA_HOOKED, hooked)
        }

    private fun logError(pkg: String, hooked: Boolean, error: Throwable) {
        YLog.error(
            tag = TAG,
            msg = "Failed to send hook status: pkg=$pkg, hooked=$hooked, error=${error.message}"
        )
    }
}