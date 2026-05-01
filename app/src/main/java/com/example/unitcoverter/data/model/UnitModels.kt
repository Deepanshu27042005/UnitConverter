package com.example.unitcoverter.data.model

import androidx.compose.ui.graphics.vector.ImageVector

data class ConversionCategory(
    val name: String,
    val icon: ImageVector,
    val units: List<UnitModel>,
    val group: String = "Common"
)

data class UnitModel(
    val name: String,
    val abbreviation: String,
    val conversionToBase: Double // Factor to convert to a base unit (e.g., meters for length)
)
