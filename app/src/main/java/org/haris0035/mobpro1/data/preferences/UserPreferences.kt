package org.haris0035.mobpro1.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreferences(private val context: Context) {
    companion object {
        private val THEME_COLOR = intPreferencesKey("theme_color")
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
        private val LIST_VIEW_TYPE = stringPreferencesKey("list_view_type")
    }

    val themeColor: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[THEME_COLOR] ?: 0
    }

    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DARK_MODE] ?: false
    }

    val listViewType: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[LIST_VIEW_TYPE] ?: "list"
    }

    suspend fun setThemeColor(color: Int) {
        context.dataStore.edit { preferences ->
            preferences[THEME_COLOR] = color
        }
    }

    suspend fun setDarkMode(darkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE] = darkMode
        }
    }

    suspend fun setListViewType(viewType: String) {
        context.dataStore.edit { preferences ->
            preferences[LIST_VIEW_TYPE] = viewType
        }
    }
} 