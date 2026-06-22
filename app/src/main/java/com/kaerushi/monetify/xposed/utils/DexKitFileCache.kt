package com.kaerushi.monetify.xposed.utils

import com.highcapable.yukihookapi.hook.log.YLog
import org.json.JSONArray
import org.json.JSONObject
import org.luckypray.dexkit.DexKitCacheBridge
import org.luckypray.dexkit.annotations.DexKitExperimentalApi
import java.io.File
import java.util.concurrent.ConcurrentHashMap

@OptIn(DexKitExperimentalApi::class)
class DexKitFileCache private constructor(
    private val cacheFile: File
): DexKitCacheBridge.Cache {
    private val values = ConcurrentHashMap<String, String>()
    private val lists = ConcurrentHashMap<String, List<String>>()

    init {
        runCatching { load() }.onFailure {
            YLog.error("DexKitFileCache: failed to load existing cache, starting empty: $it")
        }
    }

    private fun load() {
        if (!cacheFile.exists()) return
        val json = JSONObject(cacheFile.readText())
        json.optJSONObject(KEY_VALUES)?.let { obj ->
            obj.keys().forEach { k -> values[k] = obj.getString(k) }
        }
        json.optJSONObject(KEY_LISTS)?.let { obj ->
            obj.keys().forEach { k ->
                val arr = obj.getJSONArray(k)
                lists[k] = (0 until arr.length()).map { i -> arr.getString(i) }
            }
        }
    }

    @Synchronized
    private fun persist() {
        runCatching {
            val valuesObj = JSONObject()
            values.forEach { (k, v) -> valuesObj.put(k, v) }
            val listsObj = JSONObject()
            lists.forEach { (k, v) -> listsObj.put(k, JSONArray(v)) }
            val root = JSONObject().apply {
                put(KEY_VALUES, valuesObj)
                put(KEY_LISTS, listsObj)
            }
            cacheFile.parentFile?.mkdirs()
            cacheFile.writeText(root.toString())
        }.onFailure {
            YLog.error("DexKitFileCache: failed to persist cache: $it")
        }
    }

    override fun getString(key: String, default: String?): String? =
        values[key] ?: default

    override fun putString(key: String, value: String) {
        values[key] = value
        persist()
    }

    override fun getStringList(key: String, default: List<String>?): List<String>? =
        lists[key] ?: default

    override fun putStringList(key: String, value: List<String>) {
        lists[key] = value
        persist()
    }

    override fun remove(key: String) {
        val removedValue = values.remove(key) != null
        val removedList = lists.remove(key) != null
        if (removedValue || removedList) persist()
    }

    override fun getAllKeys(): Collection<String> = values.keys + lists.keys

    override fun clearAll() {
        values.clear()
        lists.clear()
        persist()
    }

    companion object {
        private const val KEY_VALUES = "values"
        private const val KEY_LISTS = "lists"

        /**
         * @param dataDir the *target app's* [android.content.pm.ApplicationInfo.dataDir]
         * (e.g. `appInfo.dataDir` from inside a [com.highcapable.yukihookapi.hook.entity.YukiBaseHooker]).
         * We write under `<dataDir>/files/...` to mirror the conventional
         * `Context.getFilesDir()` location without needing a live Context.
         */
        fun create(dataDir: String): DexKitFileCache {
            val dir = File(File(dataDir, "files"), "monetify_dexkit_cache")
            return DexKitFileCache(File(dir, "cache.json"))
        }
    }
}