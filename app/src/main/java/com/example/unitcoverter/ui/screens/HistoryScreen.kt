package com.example.unitcoverter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unitcoverter.data.model.HistoryItem
import com.example.unitcoverter.ui.theme.*
import com.example.unitcoverter.ui.viewmodel.ConvertViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryScreen(viewModel: ConvertViewModel = viewModel()) {
    val history = viewModel.historyList
    val favorites = history.filter { it.isFavorite }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            UsageInsightsCard(history.size)
        }

        if (favorites.isNotEmpty()) {
            item {
                FavoritesSection(favorites)
            }
        }

        item {
            RecentHistorySection(history, viewModel)
        }
        
        item {
            ProTipCard()
        }
        
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun UsageInsightsCard(totalCount: Int) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Usage Insights", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text("You've performed $totalCount conversions this month.", color = TextGray, fontSize = 14.sp)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Badge(text = "+15% Growth", icon = Icons.AutoMirrored.Filled.TrendingUp, color = SoftBlue)
                Badge(text = "Power User", icon = Icons.Default.Bolt, color = HighlightYellow)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressWithLabel(totalCount, "TOTAL", "Conversions")
            }
        }
    }
}

@Composable
fun Badge(text: String, icon: ImageVector, color: Color) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = color,
        modifier = Modifier.height(32.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.DarkGray)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.DarkGray)
        }
    }
}

@Composable
fun CircularProgressWithLabel(value: Int, label: String, sublabel: String) {
    val progress = (value / 100f).coerceIn(0f, 1f)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(100.dp)) {
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxSize(),
                strokeWidth = 8.dp,
                color = PrimaryGreen,
                trackColor = LightMint
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = value.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = label, fontSize = 10.sp, color = TextGray)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = sublabel, fontSize = 12.sp, color = TextGray)
    }
}

@Composable
fun FavoritesSection(favorites: List<HistoryItem>) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Favorites", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text("View All", color = PrimaryGreen, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(favorites) { item ->
                FavoriteCard(item)
            }
        }
    }
}

@Composable
fun FavoriteCard(item: HistoryItem) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        modifier = Modifier.width(160.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(LightMint),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(18.dp), tint = PrimaryGreen)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(item.categoryName, fontSize = 10.sp, color = TextGray)
            Text("${item.fromUnit} to ${item.toUnit}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun RecentHistorySection(history: List<HistoryItem>, viewModel: ConvertViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Recent History", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            if (history.isNotEmpty()) {
                TextButton(onClick = { viewModel.clearHistory() }) {
                    Text("Clear All", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            }
        }
        if (history.isEmpty()) {
            Text("No history yet.", color = TextGray, modifier = Modifier.padding(8.dp))
        } else {
            history.forEach { item ->
                HistoryItemRow(item, 
                    onFavoriteToggle = { viewModel.toggleFavorite(item.id) },
                    onDelete = { viewModel.deleteHistoryItem(item.id) }
                )
            }
        }
    }
}

@Composable
fun HistoryItemRow(item: HistoryItem, onFavoriteToggle: () -> Unit, onDelete: () -> Unit) {
    val sdf = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
    val dateString = sdf.format(Date(item.timestamp))

    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.SwapHoriz, contentDescription = null, tint = TextGray)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(item.fromValue + " " + item.fromUnit, fontWeight = FontWeight.Medium)
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(12.dp).padding(horizontal = 4.dp), tint = PrimaryGreen)
                    Text(item.toValue + " " + item.toUnit, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                }
                Text("$dateString • ${item.categoryName}", fontSize = 12.sp, color = TextGray)
            }
            IconButton(onClick = onFavoriteToggle) {
                Icon(
                    if (item.isFavorite) Icons.Default.Star else Icons.Default.StarOutline,
                    contentDescription = null,
                    tint = if (item.isFavorite) PrimaryGreen else Color.LightGray
                )
            }
        }
    }
}

@Composable
fun ProTipCard() {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = HighlightYellow.copy(alpha = 0.5f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Color(0xFFFBC02D))
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("Pro Tip", fontWeight = FontWeight.Bold, color = Color.DarkGray)
                Text(
                    "Conversions are saved when you tap 'Save' on the converter screen.",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}
