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
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Menu
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
        containerColor = SoftGray
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            // Top Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "Menu",
                    tint = PrimaryGreen,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "UNIT_LAB",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryGreen,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.HelpOutline,
                    contentDescription = "Help",
                    tint = PrimaryGreen,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Category Tabs (Pills)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                items(viewModel.categories) { category ->
                    CategoryTab(
                        category = category,
                        isSelected = viewModel.currentCategory == category,
                        onClick = { viewModel.onCategorySelected(category) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Conversion Cards
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
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
                Surface(
                    onClick = { viewModel.swapUnits() },
                    shape = CircleShape,
                    color = HighlightYellow,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(38.dp),
                    shadowElevation = 4.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.SwapVert,
                            contentDescription = "Swap",
                            tint = DarkGreen,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Number Pad
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                NumberPad(
                    onNumberClick = { viewModel.onNumberClick(it) },
                    onDeleteClick = { viewModel.onDeleteClick() }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Action Buttons: Save Result and Copy
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
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
                    Text("Save Result", fontSize = 14.sp, maxLines = 1)
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
                    Text("Copy", fontSize = 14.sp, maxLines = 1)
                }
            }
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
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) MintGreen else SoftBlue.copy(alpha = 0.5f),
        modifier = Modifier.height(36.dp)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.name,
                color = if (isSelected) DarkGreen else DarkBlue.copy(alpha = 0.7f),
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
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
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
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
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color(0xFF1976D2),
                                modifier = Modifier.size(16.dp)
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
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = if (value.isEmpty()) "0" else value,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isFrom) DarkGreen else DarkBlue,
                    modifier = Modifier.alignByBaseline()
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = unit,
                    fontSize = 16.sp,
                    color = TextGray,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.alignByBaseline()
                )
            }
        }
    }
}
