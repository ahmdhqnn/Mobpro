package org.haris0035.mobpro1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.haris0035.mobpro1.data.preferences.UserPreferences

class SettingsViewModel(private val userPreferences: UserPreferences) : ViewModel() {
    val themeColor: Flow<Int> = userPreferences.themeColor
    val isDarkMode: Flow<Boolean> = userPreferences.isDarkMode
    val listViewType: Flow<String> = userPreferences.listViewType

    fun setThemeColor(color: Int) {
        viewModelScope.launch {
            userPreferences.setThemeColor(color)
        }
    }

    fun setDarkMode(darkMode: Boolean) {
        viewModelScope.launch {
            userPreferences.setDarkMode(darkMode)
        }
    }

    fun setListViewType(viewType: String) {
        viewModelScope.launch {
            println("Setting view type to: $viewType")
            userPreferences.setListViewType(viewType)
        }
    }
}

class SettingsViewModelFactory(private val userPreferences: UserPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 