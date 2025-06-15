package com.example.market.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.market.ui.screen.BenefitsScreen
import com.example.market.ui.screen.GamesScreen
import com.example.market.ui.screen.HomeScreen
import com.example.market.ui.screen.PlaceholderScreen
import com.example.market.ui.screen.SoftwareScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "首页", Icons.Default.Home)
    object Games : Screen("games", "游戏", Icons.Default.Games)
    object Benefits : Screen("benefits", "福利合集", Icons.Default.CardGiftcard)
    object Software : Screen("software", "软件", Icons.Default.Apps)
    object Profile : Screen("profile", "我的", Icons.Default.Person)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Games,
    Screen.Benefits,
    Screen.Software,
    Screen.Profile
)

@Composable
fun MarketNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Games.route) {
            GamesScreen()
        }
        composable(Screen.Benefits.route) {
            BenefitsScreen()
        }
        composable(Screen.Software.route) {
            SoftwareScreen()
        }
        composable(Screen.Profile.route) {
            PlaceholderScreen(title = "我的")
        }
    }
} 