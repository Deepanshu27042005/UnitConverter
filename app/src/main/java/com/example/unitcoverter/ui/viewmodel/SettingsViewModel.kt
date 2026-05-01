package com.example.unitcoverter.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    var decimalPlaces by mutableStateOf(2)
    var isScientificNotationEnabled by mutableStateOf(false)
    var isHapticFeedbackEnabled by mutableStateOf(true)
    var theme by mutableStateOf("Light")
    var unitSystem by mutableStateOf("Metric")
    
    fun updateDecimalPlaces(places: Int) {
        decimalPlaces = places
    }

    fun clearCache() {
        // Mock clear cache
        decimalPlaces = 2
        isScientificNotationEnabled = false
        isHapticFeedbackEnabled = true
        theme = "Light"
        unitSystem = "Metric"
    }
}
