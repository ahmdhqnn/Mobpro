package org.ahmad0122.mobpro1.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreferences(private val context: Context) {
    
    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        private val GRID_VIEW_KEY = booleanPreferencesKey("grid_view")
    }

    val darkModeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_MODE_KEY] ?: false
        }

    val gridViewFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[GRID_VIEW_KEY] ?: false
        }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }

    suspend fun setGridView(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[GRID_VIEW_KEY] = enabled
        }
    }
} 