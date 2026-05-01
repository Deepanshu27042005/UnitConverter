package com.example.unitcoverter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.unitcoverter.data.model.ConversionCategory
import com.example.unitcoverter.data.repository.UnitRepository
import com.example.unitcoverter.ui.theme.*

@Composable
fun CategoriesScreen(onCategoryClick: (ConversionCategory) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    val categories = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            UnitRepository.categories
        } else {
            UnitRepository.categories.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Explore Units",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Friendly precision at your fingertips. Choose a category to start converting.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search categories...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(categories, span = { category ->
                // If it's Length (first item) or specific ones, make them large
                if (category.name == "Length" || category.name == "Time" || category.name == "Speed") {
                    GridItemSpan(2)
                } else {
                    GridItemSpan(1)
                }
            }) { category ->
                if (category.name == "Length" || category.name == "Time" || category.name == "Speed") {
                    CategoryLargeCard(
                        category = category,
                        tag = if (category.name == "Length") "UNIVERSAL" else "POPULAR",
                        onClick = { onCategoryClick(category) }
                    )
                } else {
                    CategorySmallCard(
                        category = category,
                        onClick = { onCategoryClick(category) }
                    )
                }
            }
            
            item(span = { GridItemSpan(2) }) {
                ProToolsBanner()
            }
            
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun CategoryLargeCard(category: ConversionCategory, tag: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(tag, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00796B))
                Text(category.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                val subtitle = category.units.take(3).joinToString(", ") { it.abbreviation } + " & more"
                Text(subtitle, fontSize = 12.sp, color = TextGray)
            }
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MintGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(category.icon, contentDescription = null, tint = DarkGreen, modifier = Modifier.size(32.dp))
            }
        }
    }
}

@Composable
fun CategorySmallCard(category: ConversionCategory, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.height(160.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(LightMint),
                contentAlignment = Alignment.Center
            ) {
                Icon(category.icon, contentDescription = null, tint = DarkGreen)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(category.name, fontWeight = FontWeight.Bold)
            val subtitle = category.units.take(2).joinToString(", ") { it.abbreviation }
            Text(subtitle, fontSize = 10.sp, color = TextGray)
        }
    }
}

@Composable
fun ProToolsBanner() {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFF263238),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.BottomStart)
            ) {
                Text("Pro Tools", color = White, fontWeight = FontWeight.Bold)
                Text("Access advanced scientific constants", color = White.copy(alpha = 0.7f), fontSize = 12.sp)
            }
        }
    }
}
