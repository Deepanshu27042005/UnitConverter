package com.example.unitcoverter.data.model

import java.time.LocalDateTime
import java.util.UUID

data class HistoryItem(
    val id: String = UUID.randomUUID().toString(),
    val fromValue: String,
    val fromUnit: String,
    val toValue: String,
    val toUnit: String,
    val categoryName: String,
    val timestamp: Long = System.currentTimeMillis(),
    var isFavorite: Boolean = false
)
