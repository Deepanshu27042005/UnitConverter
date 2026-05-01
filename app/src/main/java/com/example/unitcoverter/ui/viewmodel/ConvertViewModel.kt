package com.example.unitcoverter.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unitcoverter.data.model.ConversionCategory
import com.example.unitcoverter.data.model.UnitModel
import com.example.unitcoverter.data.repository.UnitRepository
import java.math.BigDecimal
import java.math.RoundingMode

import com.example.unitcoverter.data.model.HistoryItem
import androidx.compose.runtime.mutableStateListOf

class ConvertViewModel : ViewModel() {
    var categories = UnitRepository.categories
    var currentCategory by mutableStateOf(categories[0])
    
    var fromUnit by mutableStateOf(currentCategory.units[0])
    var toUnit by mutableStateOf(currentCategory.units[1])
    
    var fromValue by mutableStateOf("12.5")
    var toValue by mutableStateOf("")
    
    private var decimalPlaces = 2
    
    val historyList = mutableStateListOf<HistoryItem>()

    init {
        convert()
    }

    fun setUnitSystem(system: String) {
        // Automatically switch units if they match the selected system
        // This is a simplified implementation
        if (system == "Imperial" && currentCategory.name == "Length") {
            fromUnit = currentCategory.units.find { it.abbreviation == "mi" } ?: fromUnit
            toUnit = currentCategory.units.find { it.abbreviation == "km" } ?: toUnit
        } else if (system == "Metric" && currentCategory.name == "Length") {
            fromUnit = currentCategory.units.find { it.abbreviation == "km" } ?: fromUnit
            toUnit = currentCategory.units.find { it.abbreviation == "mi" } ?: toUnit
        }
        convert()
    }

    fun saveToHistory() {
        if (fromValue.isNotEmpty() && toValue.isNotEmpty()) {
            historyList.add(0, HistoryItem(
                fromValue = fromValue,
                fromUnit = fromUnit.abbreviation,
                toValue = toValue,
                toUnit = toUnit.abbreviation,
                categoryName = currentCategory.name
            ))
        }
    }

    fun toggleFavorite(itemId: String) {
        val index = historyList.indexOfFirst { it.id == itemId }
        if (index != -1) {
            val item = historyList[index]
            historyList[index] = item.copy(isFavorite = !item.isFavorite)
        }
    }

    fun deleteHistoryItem(itemId: String) {
        historyList.removeIf { it.id == itemId }
    }

    fun setDecimalPlaces(places: Int) {
        decimalPlaces = places
        convert()
    }

    fun onCategorySelected(category: ConversionCategory) {
        currentCategory = category
        fromUnit = category.units[0]
        toUnit = category.units[1]
        convert()
    }

    fun onNumberClick(char: String) {
        if (char == "." && fromValue.contains(".")) return
        if (fromValue == "0" && char != ".") {
            fromValue = char
        } else {
            fromValue += char
        }
        convert()
    }

    fun onDeleteClick() {
        if (fromValue.isNotEmpty()) {
            fromValue = fromValue.dropLast(1)
            if (fromValue.isEmpty()) fromValue = "0"
            convert()
        }
    }

    fun swapUnits() {
        val temp = fromUnit
        fromUnit = toUnit
        toUnit = temp
        convert()
    }

    fun onFromUnitSelected(unit: UnitModel) {
        fromUnit = unit
        convert()
    }

    fun onToUnitSelected(unit: UnitModel) {
        toUnit = unit
        convert()
    }

    private fun convert() {
        val input = fromValue.toDoubleOrNull() ?: 0.0
        
        // Handling Temperature separately
        val result = if (currentCategory.name == "Temperature") {
            convertTemperature(input, fromUnit.name, toUnit.name)
        } else {
            val baseValue = input * fromUnit.conversionToBase
            baseValue / toUnit.conversionToBase
        }

        toValue = if (result == 0.0) "0" else {
            BigDecimal(result).setScale(decimalPlaces, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()
        }
    }

    private fun convertTemperature(value: Double, from: String, to: String): Double {
        val celsius = when (from) {
            "Celsius" -> value
            "Fahrenheit" -> (value - 32) * 5 / 9
            "Kelvin" -> value - 273.15
            else -> value
        }
        return when (to) {
            "Celsius" -> celsius
            "Fahrenheit" -> (celsius * 9 / 5) + 32
            "Kelvin" -> celsius + 273.15
            else -> celsius
        }
    }
    
    fun getConversionDescription(): String {
        if (currentCategory.name == "Temperature") return "Temperature conversion"
        val factor = fromUnit.conversionToBase / toUnit.conversionToBase
        return "Multiply the ${currentCategory.name.lowercase()} value by ${BigDecimal(factor).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()}"
    }
}
