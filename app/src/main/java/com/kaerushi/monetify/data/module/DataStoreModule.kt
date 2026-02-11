package com.kaerushi.monetify.data.module

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.kaerushi.monetify.data.SHARED_PREFS_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun providePreferenceDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(SHARED_PREFS_NAME) }
        )
    }

    @SuppressLint("WorldReadableFiles")
    @Singleton
    @Provides
    fun provideXposedSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
        return try {
            context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_WORLD_READABLE)
        } catch (_: SecurityException) {
            context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        }
    }
}