package com.kaerushi.monetify.data.model

import com.google.gson.annotations.SerializedName

data class ChangelogInfo(
    @SerializedName("tag_name") val tagName: String,
    @SerializedName("body") val body: String,
    @SerializedName("name") val name: String,
    @SerializedName("html_url") val htmlUrl: String
)