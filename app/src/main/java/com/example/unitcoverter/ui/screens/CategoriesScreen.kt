package com.example.unitcoverter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitcoverter.data.model.ConversionCategory
import com.example.unitcoverter.data.repository.UnitRepository
import com.example.unitcoverter.ui.theme.*

@Composable
fun CategoriesScreen(onCategoryClick: (ConversionCategory) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    val allCategories = UnitRepository.categories
    val filteredCategories = if (searchQuery.isEmpty()) {
        allCategories
    } else {
        allCategories.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        containerColor = SoftGray
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Science,
                            contentDescription = null,
                            tint = PrimaryGreen,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "UNIT_LAB",
                            style = MaterialTheme.typography.titleLarge,
                            color = PrimaryGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search",
                        tint = TextGray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Search Bar
            item {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search categories...", color = TextGray) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(28.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF1F3F4),
                        unfocusedContainerColor = Color(0xFFF1F3F4),
                        disabledContainerColor = Color(0xFFF1F3F4),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    singleLine = true
                )
            }

            // Pro Feature Banner
            item {
                ProFeatureBanner()
            }

            // Sections
            if (searchQuery.isEmpty()) {
                // Common Section
                item { SectionHeader("Common") }
                items(filteredCategories.filter { it.group == "Common" }) { category ->
                    CommonCategoryCard(category) { onCategoryClick(category) }
                }

                // Science Section
                item { SectionHeader("Science") }
                item {
                    val scienceCategories = filteredCategories.filter { it.group == "Science" }
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        for (i in scienceCategories.indices step 2) {
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                SmallCategoryCard(
                                    category = scienceCategories[i],
                                    modifier = Modifier.weight(1f),
                                    onClick = { onCategoryClick(scienceCategories[i]) }
                                )
                                if (i + 1 < scienceCategories.size) {
                                    SmallCategoryCard(
                                        category = scienceCategories[i + 1],
                                        modifier = Modifier.weight(1f),
                                        onClick = { onCategoryClick(scienceCategories[i + 1]) }
                                    )
                                } else {
                                    Box(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

                // Lifestyle Section
                item { SectionHeader("Lifestyle") }
                items(filteredCategories.filter { it.group == "Lifestyle" }) { category ->
                    if (category.name == "Currency") {
                        CurrencyBannerCard(category) { onCategoryClick(category) }
                    } else {
                        // For Cooking and Time, show them as small cards if they are the last ones
                        // In the image they are small cards at the bottom.
                        // I'll just group them in rows of 2.
                    }
                }
                
                item {
                    val remainingLifestyle = filteredCategories.filter { it.group == "Lifestyle" && it.name != "Currency" }
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        for (i in remainingLifestyle.indices step 2) {
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                SmallCategoryCard(
                                    category = remainingLifestyle[i],
                                    modifier = Modifier.weight(1f),
                                    onClick = { onCategoryClick(remainingLifestyle[i]) }
                                )
                                if (i + 1 < remainingLifestyle.size) {
                                    SmallCategoryCard(
                                        category = remainingLifestyle[i + 1],
                                        modifier = Modifier.weight(1f),
                                        onClick = { onCategoryClick(remainingLifestyle[i + 1]) }
                                    )
                                } else {
                                    Box(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            } else {
                // Search Results
                items(filteredCategories) { category ->
                    CommonCategoryCard(category) { onCategoryClick(category) }
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun ProFeatureBanner() {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFB2FEFA), Color(0xFF0ED2F7).copy(alpha = 0.5f))
    )
    Surface(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                Surface(
                    color = Color.White.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "PRO FEATURE",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = DarkGreen
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "New Scientific Units Available",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )
                Text(
                    "Explore our expanded database for quantum\nphysics and chemistry measurements.",
                    fontSize = 12.sp,
                    color = DarkGreen.copy(alpha = 0.7f),
                    lineHeight = 16.sp
                )
            }
            Icon(
                Icons.Outlined.Science,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = 20.dp),
                tint = Color.White.copy(alpha = 0.2f)
            )
        }
    }
}

@Composable
fun CommonCategoryCard(category: ConversionCategory, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MintGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(category.icon, contentDescription = null, tint = DarkGreen, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(category.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            val unitsText = category.units.take(3).joinToString(", ") { it.name.lowercase() }
            Text(unitsText, fontSize = 12.sp, color = TextGray)
        }
    }
}

@Composable
fun SmallCategoryCard(category: ConversionCategory, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(category.icon, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(category.name, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun CurrencyBannerCard(category: ConversionCategory, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = SoftBlue,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(category.icon, contentDescription = null, tint = DarkBlue)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(category.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Real-time exchange rates", fontSize = 12.sp, color = TextGray)
            }
            Icon(Icons.AutoMirrored.Filled.TrendingUp, contentDescription = null, tint = DarkBlue, modifier = Modifier.size(20.dp))
        }
    }
}
