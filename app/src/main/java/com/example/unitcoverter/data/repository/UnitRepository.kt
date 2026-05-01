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
            ),
            group = "Common"
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
            ),
            group = "Common"
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
            ),
            group = "Common"
        ),
        ConversionCategory(
            "Temperature",
            Icons.Default.Thermostat,
            listOf(
                UnitModel("Celsius", "°C", 1.0),
                UnitModel("Fahrenheit", "°F", 1.0),
                UnitModel("Kelvin", "K", 1.0)
            ),
            group = "Science"
        ),
        ConversionCategory(
            "Pressure",
            Icons.Default.Compress,
            listOf(
                UnitModel("Pascal", "Pa", 1.0),
                UnitModel("Bar", "bar", 100000.0),
                UnitModel("Atmosphere", "atm", 101325.0),
                UnitModel("PSI", "psi", 6894.76)
            ),
            group = "Science"
        ),
        ConversionCategory(
            "Energy",
            Icons.Default.Bolt,
            listOf(
                UnitModel("Joules", "J", 1.0),
                UnitModel("Calories", "cal", 4.184),
                UnitModel("Kilowatt-hour", "kWh", 3600000.0)
            ),
            group = "Science"
        ),
        ConversionCategory(
            "Frequency",
            Icons.Default.Waves,
            listOf(
                UnitModel("Hertz", "Hz", 1.0),
                UnitModel("Kilohertz", "kHz", 1000.0),
                UnitModel("Megahertz", "MHz", 1000000.0)
            ),
            group = "Science"
        ),
        ConversionCategory(
            "Currency",
            Icons.Default.Payments,
            listOf(
                UnitModel("US Dollar", "USD", 1.0),
                UnitModel("Euro", "EUR", 0.92),
                UnitModel("British Pound", "GBP", 0.79),
                UnitModel("Japanese Yen", "JPY", 150.0),
                UnitModel("Indian Rupee", "INR", 83.0),
                UnitModel("Australian Dollar", "AUD", 1.52),
                UnitModel("Canadian Dollar", "CAD", 1.35),
                UnitModel("Swiss Franc", "CHF", 0.88),
                UnitModel("Chinese Yuan", "CNY", 7.19),
                UnitModel("Bitcoin", "BTC", 0.000015)
            ),
            group = "Lifestyle"
        ),
        ConversionCategory(
            "Cooking",
            Icons.Default.Restaurant,
            listOf(
                UnitModel("Cups", "cup", 1.0),
                UnitModel("Tablespoons", "tbsp", 0.0625),
                UnitModel("Teaspoons", "tsp", 0.0208333),
                UnitModel("Fluid Ounces", "fl oz", 0.125)
            ),
            group = "Lifestyle"
        ),
        ConversionCategory(
            "Time",
            Icons.Default.Schedule,
            listOf(
                UnitModel("Years", "yr", 31536000.0),
                UnitModel("Days", "d", 86400.0),
                UnitModel("Hours", "h", 3600.0),
                UnitModel("Minutes", "min", 60.0),
                UnitModel("Seconds", "s", 1.0)
            ),
            group = "Lifestyle"
        )
    )
}
