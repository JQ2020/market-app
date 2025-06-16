package com.example.market.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.market.ui.screen.BenefitsScreen
import com.example.market.ui.screen.GamesScreen
import com.example.market.ui.screen.HomeScreen
import com.example.market.ui.screen.PlaceholderScreen
import com.example.market.ui.screen.SearchScreen
import com.example.market.ui.screen.SearchResultScreen
import com.example.market.ui.screen.SoftwareScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "首页", Icons.Default.Home)
    object Games : Screen("games", "游戏", Icons.Default.SportsEsports)
    object Benefits : Screen("benefits", "福利合集", Icons.Default.CardGiftcard)
    object Software : Screen("software", "软件", Icons.Default.Apps)
    object Profile : Screen("profile", "我的", Icons.Default.Person)
    
    // 搜索相关页面
    object Search : Screen("search", "搜索", Icons.Default.Search)
    object SearchResult : Screen("search_result/{query}", "搜索结果", Icons.Default.Search) {
        fun createRoute(query: String) = "search_result/$query"
    }
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
            HomeScreen(
                onNavigateToSearch = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }
        composable(Screen.Games.route) {
            GamesScreen(
                onNavigateToSearch = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }
        composable(Screen.Benefits.route) {
            BenefitsScreen()
        }
        composable(Screen.Software.route) {
            SoftwareScreen(
                onNavigateToSearch = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }
        composable(Screen.Profile.route) {
            PlaceholderScreen(title = "我的")
        }
        
        // 搜索页面
        composable(Screen.Search.route) {
            SearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onSearch = { query ->
                    navController.navigate(Screen.SearchResult.createRoute(query))
                }
            )
        }
        
        // 搜索结果页面
        composable(
            route = Screen.SearchResult.route,
            arguments = listOf(navArgument("query") { type = NavType.StringType })
        ) { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query") ?: ""
            SearchResultScreen(
                query = query,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
} 