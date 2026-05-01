package com.example.unitcoverter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unitcoverter.ui.theme.*
import com.example.unitcoverter.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column {
            Text(text = "Settings", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(text = "Adjust your precision and interface preferences.", color = TextGray, fontSize = 14.sp)
        }

        SettingsSection(title = "General Precision", icon = Icons.Default.Tune) {
            PrecisionSettings(viewModel)
        }

        SettingsSection(title = "Appearance", icon = Icons.Default.Palette) {
            AppearanceSettings(viewModel)
        }

        Surface(shape = RoundedCornerShape(24.dp), color = MaterialTheme.colorScheme.surface) {
            Column {
                SettingsListItem("Sync History", Icons.Default.CloudSync, MaterialTheme.colorScheme.onSurface)
                SettingsListItem("Privacy & Security", Icons.Default.Security, Color.DarkGray)
                SettingsListItem("Clear Local Cache", Icons.Default.DeleteSweep, Color.Red) {
                    viewModel.clearCache()
                }
            }
        }

        Text(
            text = "UNIT_LAB V2.4.1 (STABLE BUILD)",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.LightGray,
            fontSize = 10.sp
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun SettingsSection(title: String, icon: ImageVector, content: @Composable () -> Unit) {
    Surface(shape = RoundedCornerShape(24.dp), color = MaterialTheme.colorScheme.surface) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = Color(0xFF00796B), modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(20.dp))
            content()
        }
    }
}

@Composable
fun PrecisionSettings(viewModel: SettingsViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("DECIMAL PLACES", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                Text(viewModel.decimalPlaces.toString(), color = Color(0xFF00796B), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(SoftGray),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                (0..4).forEach { i ->
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(if (viewModel.decimalPlaces == i) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
                            .clickable { viewModel.updateDecimalPlaces(i) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(i.toString(), fontSize = 14.sp, fontWeight = if (viewModel.decimalPlaces == i) FontWeight.Bold else FontWeight.Normal)
                    }
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("Scientific Notation", fontWeight = FontWeight.Medium)
                Text("Automatically switch for large values", fontSize = 10.sp, color = TextGray)
            }
            Switch(
                checked = viewModel.isScientificNotationEnabled,
                onCheckedChange = { viewModel.isScientificNotationEnabled = it },
                colors = SwitchDefaults.colors(checkedThumbColor = White, checkedTrackColor = Color(0xFF00796B))
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("Haptic Feedback", fontWeight = FontWeight.Medium)
                Text("Vibrate on conversion results", fontSize = 10.sp, color = TextGray)
            }
            Switch(
                checked = viewModel.isHapticFeedbackEnabled,
                onCheckedChange = { viewModel.isHapticFeedbackEnabled = it }
            )
        }
    }
}

@Composable
fun AppearanceSettings(viewModel: SettingsViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Column {
            Text("INTERFACE THEME", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(SoftGray),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ThemeOption("Light", Icons.Default.LightMode, viewModel.theme == "Light") { viewModel.theme = "Light" }
                ThemeOption("Dark", Icons.Default.DarkMode, viewModel.theme == "Dark") { viewModel.theme = "Dark" }
                ThemeOption("Auto", Icons.Default.SettingsSuggest, viewModel.theme == "Auto") { viewModel.theme = "Auto" }
            }
        }

        Column {
            Text("DEFAULT UNIT SYSTEM", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                UnitSystemOption("Metric", viewModel.unitSystem == "Metric") { viewModel.unitSystem = "Metric" }
                UnitSystemOption("Imperial", viewModel.unitSystem == "Imperial") { viewModel.unitSystem = "Imperial" }
            }
            Spacer(modifier = Modifier.height(8.dp))
            UnitSystemOption("UK Customary", viewModel.unitSystem == "UK") { viewModel.unitSystem = "UK" }
        }
    }
}

@Composable
fun ThemeOption(label: String, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
        modifier = Modifier.height(32.dp).width(80.dp)
    ) {
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(14.dp), tint = if (isSelected) Color(0xFF00796B) else TextGray)
            Spacer(modifier = Modifier.width(4.dp))
            Text(label, fontSize = 12.sp, color = if (isSelected) Color(0xFF00796B) else TextGray)
        }
    }
}

@Composable
fun UnitSystemOption(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) MintGreen else SoftGray,
        modifier = Modifier.height(36.dp)
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp), contentAlignment = Alignment.Center) {
            Text(label, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal, color = if (isSelected) DarkGreen else TextGray)
        }
    }
}

@Composable
fun SettingsListItem(label: String, icon: ImageVector, tint: Color, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp), tint = tint)
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, modifier = Modifier.weight(1f), color = tint)
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray)
    }
}
