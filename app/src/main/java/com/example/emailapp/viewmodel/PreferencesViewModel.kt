package com.example.emailapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emailapp.model.DevicePreferences
import com.example.emailapp.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PreferencesViewModel(private val repository: Repository) : ViewModel() {

    private val _preferences = MutableStateFlow<DevicePreferences?>(null)
    val preferences = _preferences.asStateFlow()

    fun fetchPreferences(userId: Long) {
        viewModelScope.launch {
            val fetchedPreferences = repository.getPreferences(userId)
            if (fetchedPreferences != null) {
                _preferences.value = fetchedPreferences
            }
        }
    }

    fun savePreferences(preferences: DevicePreferences) {
        viewModelScope.launch {
            repository.savePreferences(preferences)?.let {
                _preferences.value = it
            }
        }
    }
}
