package com.kaerushi.monetify.core.manager

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.kaerushi.monetify.data.model.preferences.AppLanguage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocaleManager @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    fun applyLocale(language: AppLanguage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(language.code)
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language.code))
        }
    }
}