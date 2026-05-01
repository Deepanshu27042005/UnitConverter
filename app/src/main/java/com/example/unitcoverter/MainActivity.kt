package com.example.unitcoverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.unitcoverter.ui.navigation.NavGraph
import com.example.unitcoverter.ui.navigation.Screen
import com.example.unitcoverter.ui.theme.UnitCoverterTheme
import com.example.unitcoverter.ui.viewmodel.ConvertViewModel
import com.example.unitcoverter.ui.viewmodel.SettingsViewModel
import androidx.compose.foundation.isSystemInDarkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val isDarkTheme = when (settingsViewModel.theme) {
                "Dark" -> true
                "Light" -> false
                else -> isSystemInDarkTheme()
            }
            UnitCoverterTheme(darkTheme = isDarkTheme) {
                MainApp(settingsViewModel)
            }
        }
    }
}

@Composable
fun MainApp(settingsViewModel: SettingsViewModel) {
    val navController = rememberNavController()
    val convertViewModel: ConvertViewModel = viewModel()
    val items = listOf(
        BottomNavItem("CONVERT", Screen.Convert.route, Icons.Default.SwapHoriz),
        BottomNavItem("CATEGORIES", Screen.Categories.route, Icons.Default.GridView),
        BottomNavItem("HISTORY", Screen.History.route, Icons.Default.History),
        BottomNavItem("SETTINGS", Screen.Settings.route, Icons.Default.Settings)
    )

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .navigationBarsPadding(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .shadow(12.dp, RoundedCornerShape(36.dp)),
                    shape = RoundedCornerShape(36.dp),
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        items.forEach { item ->
                            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                            
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .clickable(
                                        interactionSource = null,
                                        indication = null
                                    ) {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = if (selected) {
                                        Modifier
                                            .clip(RoundedCornerShape(30.dp))
                                            .background(Color(0xFFA7FFEB).copy(alpha = 0.8f))
                                            .padding(horizontal = 16.dp, vertical = 8.dp)
                                    } else Modifier
                                ) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.label,
                                        tint = if (selected) Color(0xFF004D40) else Color.LightGray,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = item.label,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (selected) Color(0xFF004D40) else Color.LightGray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
            NavGraph(
                navController = navController, 
                settingsViewModel = settingsViewModel,
                convertViewModel = convertViewModel
            )
        }
    }
}

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)
