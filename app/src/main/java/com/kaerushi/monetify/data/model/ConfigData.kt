package com.kaerushi.monetify.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing the exported/imported configuration
 */
data class ConfigData(
    @SerializedName("name")
    val name: String,

    @SerializedName("timestamp")
    val timestamp: Long,

    @SerializedName("version")
    val version: Int,

    @SerializedName("preferences")
    val preferences: Map<String, String>
)
