package org.panjirai0110.mobpro1.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreferences(private val context: Context) {
    companion object {
        private val THEME_COLOR = intPreferencesKey("theme_color")
        private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        private val VIEW_TYPE = stringPreferencesKey("view_type") // "list" or "grid"
    }

    val themeColor: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[THEME_COLOR] ?: 0xFF6200EE.toInt() // Default color
    }

    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_DARK_MODE] == true
    }

    val viewType: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[VIEW_TYPE] ?: "list"
    }

    suspend fun setThemeColor(color: Int) {
        context.dataStore.edit { preferences ->
            preferences[THEME_COLOR] = color
        }
    }

    suspend fun setDarkMode(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDark
        }
    }

    suspend fun setViewType(type: String) {
        context.dataStore.edit { preferences ->
            preferences[VIEW_TYPE] = type
        }
    }
} 