package com.example.unitcoverter.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.unitcoverter.data.model.ConversionCategory
import com.example.unitcoverter.data.model.UnitModel

object UnitRepository {
    val categories = listOf(
        ConversionCategory(
            "Length",
            Icons.Default.Straighten,
            listOf(
                UnitModel("Kilometers", "km", 1000.0),
                UnitModel("Meters", "m", 1.0),
                UnitModel("Centimeters", "cm", 0.01),
                UnitModel("Millimeters", "mm", 0.001),
                UnitModel("Miles", "mi", 1609.34),
                UnitModel("Yards", "yd", 0.9144),
                UnitModel("Feet", "ft", 0.3048),
                UnitModel("Inches", "in", 0.0254)
            )
        ),
        ConversionCategory(
            "Mass",
            Icons.Default.Scale,
            listOf(
                UnitModel("Kilograms", "kg", 1000.0),
                UnitModel("Grams", "g", 1.0),
                UnitModel("Milligrams", "mg", 0.001),
                UnitModel("Pounds", "lb", 453.592),
                UnitModel("Ounces", "oz", 28.3495)
            )
        ),
        ConversionCategory(
            "Volume",
            Icons.Default.Opacity,
            listOf(
                UnitModel("Liters", "L", 1.0),
                UnitModel("Milliliters", "ml", 0.001),
                UnitModel("Gallons", "gal", 3.78541),
                UnitModel("Quarts", "qt", 0.946353),
                UnitModel("Pints", "pt", 0.473176),
                UnitModel("Cups", "cup", 0.24)
            )
        ),
        ConversionCategory(
            "Temperature",
            Icons.Default.Thermostat,
            listOf(
                UnitModel("Celsius", "°C", 1.0),
                UnitModel("Fahrenheit", "°F", 1.0),
                UnitModel("Kelvin", "K", 1.0)
            )
        ),
        ConversionCategory(
            "Time",
            Icons.Default.Schedule,
            listOf(
                UnitModel("Years", "yr", 31536000.0),
                UnitModel("Months", "mo", 2628000.0),
                UnitModel("Weeks", "wk", 604800.0),
                UnitModel("Days", "d", 86400.0),
                UnitModel("Hours", "h", 3600.0),
                UnitModel("Minutes", "min", 60.0),
                UnitModel("Seconds", "s", 1.0),
                UnitModel("Milliseconds", "ms", 0.001)
            )
        ),
        ConversionCategory(
            "Speed",
            Icons.Default.Speed,
            listOf(
                UnitModel("Km/h", "km/h", 1.0),
                UnitModel("Miles/h", "mph", 1.60934),
                UnitModel("Meters/s", "m/s", 3.6),
                UnitModel("Knots", "kn", 1.852)
            )
        ),
        ConversionCategory(
            "Cooking",
            Icons.Default.Restaurant,
            listOf(
                UnitModel("Cups", "cup", 1.0),
                UnitModel("Tablespoons", "tbsp", 0.0625),
                UnitModel("Teaspoons", "tsp", 0.0208333),
                UnitModel("Fluid Ounces", "fl oz", 0.125)
            )
        )
    )
}
