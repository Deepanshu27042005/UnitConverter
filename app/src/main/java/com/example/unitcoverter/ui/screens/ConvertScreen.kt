package com.example.unitcoverter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unitcoverter.data.model.ConversionCategory
import com.example.unitcoverter.data.model.UnitModel
import com.example.unitcoverter.ui.components.NumberPad
import com.example.unitcoverter.ui.theme.*
import com.example.unitcoverter.ui.viewmodel.ConvertViewModel
import kotlinx.coroutines.launch

@Composable
fun ConvertScreen(viewModel: ConvertViewModel = viewModel()) {
    val clipboardManager = LocalClipboardManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            // Top Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "UNIT_LAB",
                    style = MaterialTheme.typography.titleLarge,
                    color = DarkGreen,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.HelpOutline,
                    contentDescription = "Help",
                    tint = DarkGreen,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(24.dp)
                )
            }

            // Category Tabs (Pills)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                items(viewModel.categories) { category ->
                    CategoryTab(
                        category = category,
                        isSelected = viewModel.currentCategory == category,
                        onClick = { viewModel.onCategorySelected(category) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Conversion Cards
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ConversionCard(
                        label = "FROM",
                        value = viewModel.fromValue,
                        unit = viewModel.fromUnit.abbreviation,
                        unitName = viewModel.fromUnit.name,
                        isFrom = true,
                        units = viewModel.currentCategory.units,
                        onUnitSelected = { viewModel.onFromUnitSelected(it) }
                    )
                    ConversionCard(
                        label = "TO",
                        value = viewModel.toValue,
                        unit = viewModel.toUnit.abbreviation,
                        unitName = viewModel.toUnit.name,
                        isFrom = false,
                        units = viewModel.currentCategory.units,
                        onUnitSelected = { viewModel.onToUnitSelected(it) }
                    )
                }

                // Yellow Swap Button
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(HighlightYellow)
                        .clickable { viewModel.swapUnits() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapVert,
                        contentDescription = "Swap",
                        tint = DarkGreen,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Number Pad
            NumberPad(
                onNumberClick = { viewModel.onNumberClick(it) },
                onDeleteClick = { viewModel.onDeleteClick() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons: Save Result and Copy
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { 
                        viewModel.saveToHistory() 
                        scope.launch {
                            snackbarHostState.showSnackbar("Result saved to history")
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MintGreen),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = DarkGreen)
                ) {
                    Icon(Icons.Default.BookmarkBorder, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save Result", fontSize = 14.sp)
                }
                OutlinedButton(
                    onClick = { 
                        clipboardManager.setText(AnnotatedString(viewModel.toValue))
                        scope.launch {
                            snackbarHostState.showSnackbar("Copied to clipboard")
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextGray)
                ) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Copy", fontSize = 14.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CategoryTab(
    category: ConversionCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = if (isSelected) MintGreen else LightMint.copy(alpha = 0.5f),
        modifier = Modifier.height(40.dp)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.name,
                color = DarkGreen,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ConversionCard(
    label: String,
    value: String,
    unit: String,
    unitName: String,
    isFrom: Boolean,
    units: List<UnitModel>,
    onUnitSelected: (UnitModel) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(32.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextGray
                )
                Box {
                    Surface(
                        onClick = { expanded = true },
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFE3F2FD),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            Text(
                                text = unitName,
                                fontSize = 12.sp,
                                color = Color(0xFF1976D2),
                                fontWeight = FontWeight.Medium
                            )
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color(0xFF1976D2),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        units.forEach { u ->
                            DropdownMenuItem(
                                text = { Text(u.name) },
                                onClick = {
                                    onUnitSelected(u)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (value.isEmpty()) "0" else value,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = unit,
                    fontSize = 18.sp,
                    color = TextGray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
