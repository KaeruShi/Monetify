package com.kaerushi.monetify.xposed.utils

import android.content.Context
import android.content.res.Configuration

object Utils {
    fun isNightMode(context: Context): Boolean {
        return context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}