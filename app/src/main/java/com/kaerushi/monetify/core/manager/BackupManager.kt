package com.kaerushi.monetify.core.manager

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.kaerushi.monetify.data.model.ConfigData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupManager @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val dataStore: DataStore<Preferences>,
    private val xposedPrefs: SharedPreferences,
    private val gson: Gson
) {
    private val tag = "BackupManager"

    /**
     * Export all preferences to a JSON file
     * @param uri The URI where the configuration should be saved
     * @param configName Optional name for the configuration
     * @return Result indicating success or failure
     */
    suspend fun exportConfig(uri: Uri, configName: String = "Monetify Config"): Result<Unit> {
        return try {
            val preferences = dataStore.data.first()
            val configData = ConfigData(
                name = configName,
                timestamp = System.currentTimeMillis(),
                version = 1,
                preferences = preferences.asMap().mapKeys { it.key.name }
                    .mapValues { (_, value) -> value.toString() }
            )

            val json = gson.toJson(configData)

            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                outputStream.write(json.toByteArray())
                outputStream.flush()
            } ?: return Result.failure(IOException("Failed to open output stream"))

            Log.d(tag, "Configuration exported successfully to $uri")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(tag, "Error exporting configuration", e)
            Result.failure(e)
        }
    }

    /**
     * Import preferences from a JSON file
     * @param uri The URI of the configuration file to import
     * @return Result containing the imported ConfigData or an error
     */
    suspend fun importConfig(uri: Uri): Result<ConfigData> {
        return try {
            val json = context.contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.bufferedReader().use { it.readText() }
            } ?: return Result.failure(IOException("Failed to open input stream"))

            val configData = gson.fromJson(json, ConfigData::class.java)
                ?: return Result.failure(JsonSyntaxException("Invalid configuration format"))

            // Validate configuration version
            if (configData.version > 1) {
                return Result.failure(IllegalArgumentException("Unsupported configuration version: ${configData.version}"))
            }

            // Apply preferences to DataStore
            dataStore.edit { preferences ->
                preferences.clear()
                configData.preferences.forEach { (key, value) ->
                    when {
                        key.contains("_hooked") || key.contains("_monet_enabled") ||
                        key.contains("_ads_disabled") || key == "show_not_installed_apps" ||
                        key == "show_app_icon_pack" || key == "show_welcome_screen" -> {
                            preferences[androidx.datastore.preferences.core.booleanPreferencesKey(key)] =
                                value.toBoolean()
                        }
                        key == "app_theme" || key == "app_color_scheme" || key.contains("_icon_pack") -> {
                            preferences[androidx.datastore.preferences.core.stringPreferencesKey(key)] = value
                        }
                    }
                }
            }

            // Apply preferences to SharedPreferences for Xposed
            val editor = xposedPrefs.edit()
            editor.clear()
            configData.preferences.forEach { (key, value) ->
                // Only write Xposed-relevant preferences (those with package names)
                if (key.startsWith("app_") && key.contains(".")) {
                    when {
                        key.contains("_monet_enabled") || key.contains("_ads_disabled") -> {
                            editor.putBoolean(key, value.toBoolean())
                        }
                        key.contains("_icon_pack") -> {
                            editor.putString(key, value)
                        }
                    }
                }
            }
            editor.apply()

            Log.d(tag, "Configuration imported successfully from $uri")
            Result.success(configData)
        } catch (e: JsonSyntaxException) {
            Log.e(tag, "Invalid JSON format", e)
            Result.failure(IllegalArgumentException("Invalid configuration file format", e))
        } catch (e: Exception) {
            Log.e(tag, "Error importing configuration", e)
            Result.failure(e)
        }
    }

    /**
     * Validate a configuration file without importing it
     * @param uri The URI of the configuration file to validate
     * @return Result containing the ConfigData if valid, or an error
     */
    fun validateConfig(uri: Uri): Result<ConfigData> {
        return try {
            val json = context.contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.bufferedReader().use { it.readText() }
            } ?: return Result.failure(IOException("Failed to open input stream"))

            val configData = gson.fromJson(json, ConfigData::class.java)
                ?: return Result.failure(JsonSyntaxException("Invalid configuration format"))

            if (configData.version > 1) {
                return Result.failure(IllegalArgumentException("Unsupported configuration version: ${configData.version}"))
            }

            Result.success(configData)
        } catch (e: Exception) {
            Log.e(tag, "Error validating configuration", e)
            Result.failure(e)
        }
    }
}