package com.example.unitcoverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitcoverter.ui.theme.UnitCoverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitCoverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {

    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }

    var inputUnit by remember { mutableStateOf("Centimeters") }
    var outputUnit by remember { mutableStateOf("Meters") }

    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }

    val conversionFactor = remember { mutableStateOf(1.0) }
    val oCF = remember { mutableStateOf(1.0) }

    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result =
            ((inputValueDouble * conversionFactor.value * 100.0) / oCF.value)
                .roundToInt() / 100.0
        outputValue = result.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F8E9))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Unit Converter",
            style = TextStyle(fontSize = 32.sp, color = Color(0xFF1B5E20))
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertUnits()
            },
            label = { Text("Enter Value") },
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(fontSize = 20.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            // INPUT UNIT
            Box {
                Button(onClick = { iExpanded = true }) {
                    Text(inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }

                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {

                    DropdownMenuItem(text = { Text("Millimeters") }, onClick = {
                        iExpanded = false
                        inputUnit = "Millimeters"
                        conversionFactor.value = 0.001
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = {
                        iExpanded = false
                        inputUnit = "Centimeters"
                        conversionFactor.value = 0.01
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Decimeters") }, onClick = {
                        iExpanded = false
                        inputUnit = "Decimeters"
                        conversionFactor.value = 0.1
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Meters") }, onClick = {
                        iExpanded = false
                        inputUnit = "Meters"
                        conversionFactor.value = 1.0
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Kilometers") }, onClick = {
                        iExpanded = false
                        inputUnit = "Kilometers"
                        conversionFactor.value = 1000.0
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Inches") }, onClick = {
                        iExpanded = false
                        inputUnit = "Inches"
                        conversionFactor.value = 0.0254
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Feet") }, onClick = {
                        iExpanded = false
                        inputUnit = "Feet"
                        conversionFactor.value = 0.3048
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Yards") }, onClick = {
                        iExpanded = false
                        inputUnit = "Yards"
                        conversionFactor.value = 0.9144
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Miles") }, onClick = {
                        iExpanded = false
                        inputUnit = "Miles"
                        conversionFactor.value = 1609.34
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Micrometers") }, onClick = {
                        iExpanded = false
                        inputUnit = "Micrometers"
                        conversionFactor.value = 0.000001
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Nanometers") }, onClick = {
                        iExpanded = false
                        inputUnit = "Nanometers"
                        conversionFactor.value = 0.000000001
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Nautical Miles") }, onClick = {
                        iExpanded = false
                        inputUnit = "Nautical Miles"
                        conversionFactor.value = 1852.0
                        convertUnits()
                    })
                }
            }

            // OUTPUT UNIT
            Box {
                Button(onClick = { oExpanded = true }) {
                    Text(outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }

                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {

                    DropdownMenuItem(text = { Text("Millimeters") }, onClick = {
                        oExpanded = false
                        outputUnit = "Millimeters"
                        oCF.value = 0.001
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = {
                        oExpanded = false
                        outputUnit = "Centimeters"
                        oCF.value = 0.01
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Decimeters") }, onClick = {
                        oExpanded = false
                        outputUnit = "Decimeters"
                        oCF.value = 0.1
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Meters") }, onClick = {
                        oExpanded = false
                        outputUnit = "Meters"
                        oCF.value = 1.0
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Kilometers") }, onClick = {
                        oExpanded = false
                        outputUnit = "Kilometers"
                        oCF.value = 1000.0
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Inches") }, onClick = {
                        oExpanded = false
                        outputUnit = "Inches"
                        oCF.value = 0.0254
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Feet") }, onClick = {
                        oExpanded = false
                        outputUnit = "Feet"
                        oCF.value = 0.3048
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Yards") }, onClick = {
                        oExpanded = false
                        outputUnit = "Yards"
                        oCF.value = 0.9144
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Miles") }, onClick = {
                        oExpanded = false
                        outputUnit = "Miles"
                        oCF.value = 1609.34
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Micrometers") }, onClick = {
                        oExpanded = false
                        outputUnit = "Micrometers"
                        oCF.value = 0.000001
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Nanometers") }, onClick = {
                        oExpanded = false
                        outputUnit = "Nanometers"
                        oCF.value = 0.000000001
                        convertUnits()
                    })

                    DropdownMenuItem(text = { Text("Nautical Miles") }, onClick = {
                        oExpanded = false
                        outputUnit = "Nautical Miles"
                        oCF.value = 1852.0
                        convertUnits()
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFF9800)),
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Result: $outputValue $outputUnit",
                modifier = Modifier.padding(24.dp),
                style = TextStyle(fontSize = 24.sp, color = Color.White)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}
