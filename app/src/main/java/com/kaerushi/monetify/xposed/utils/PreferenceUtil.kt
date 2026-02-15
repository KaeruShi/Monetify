package com.kaerushi.monetify.xposed.utils

import com.google.gson.Gson
import com.highcapable.yukihookapi.hook.log.YLog
import com.kaerushi.monetify.data.PACKAGE_NAME
import com.kaerushi.monetify.data.SHARED_PREFS_NAME
import de.robv.android.xposed.XSharedPreferences

object PreferenceUtil {
    private const val TAG = "PreferenceUtil"

    private val preferences: XSharedPreferences =
        XSharedPreferences(PACKAGE_NAME, SHARED_PREFS_NAME).apply {
            makeWorldReadable()
            reload()
    }

    fun getAppMonetEnabled(packageName: String): Boolean {
        val key = "app_${packageName}_monet_enabled"
        return getPreference<Boolean>(key) ?: false
    }
    fun getAppIconPack(packageName: String): String? {
        val key = "app_${packageName}_icon_pack"
        return getPreference<String>(key)
    }

    private inline fun <reified T> getPreference(key: String): T? {
        preferences.reload()
        return when (T::class) {
            Boolean::class -> preferences.getBoolean(key, false) as? T
            Int::class -> preferences.getInt(key, 0) as? T
            Float::class -> preferences.getFloat(key, 0f) as? T
            Long::class -> preferences.getLong(key, 0L) as? T
            String::class -> preferences.getString(key, null) as? T
            else -> {
                val json = preferences.getString(key, null)
                if (json != null) {
                    try {
                        Gson().fromJson(json, T::class.java).also {
                            YLog.debug(tag = TAG, msg =  "Deserialized preference for key $key: $it")
                        }
                    } catch (e: Exception) {
                        YLog.error(tag = TAG, msg =  "Error deserializing preference for key $key: ${e.message}")
                        null
                    }
                } else {
                    YLog.error(tag = TAG, msg = "No JSON found for key $key")
                    null
                }
            }
        }
    }
}