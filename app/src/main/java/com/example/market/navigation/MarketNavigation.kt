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

/**
 * 应用页面路由密封类
 * 定义应用中所有页面的路由信息，包括路由路径、页面标题和图标
 * 
 * 使用密封类的优势：
 * - 类型安全：编译时检查所有可能的页面类型
 * - 易于维护：集中管理所有路由信息
 * - 防止错误：避免字符串路由的拼写错误
 * 
 * @param route 路由路径，用于导航系统识别页面
 * @param title 页面标题，显示在底部导航栏
 * @param icon 页面图标，显示在底部导航栏
 */
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    
    /** 首页 - 应用商店主页，展示推荐应用和轮播内容 */
    object Home : Screen("home", "首页", Icons.Default.Home)
    
    /** 游戏页面 - 专门展示游戏应用的页面 */
    object Games : Screen("games", "游戏", Icons.Default.SportsEsports)
    
    /** 福利合集页面 - 展示优惠活动和福利内容的WebView页面 */
    object Benefits : Screen("benefits", "福利合集", Icons.Default.CardGiftcard)
    
    /** 软件页面 - 展示各类应用软件的页面 */
    object Software : Screen("software", "软件", Icons.Default.Apps)
    
    /** 个人中心页面 - 用户信息和设置管理页面 */
    object Profile : Screen("profile", "我的", Icons.Default.Person)
    
    // 搜索相关页面（不在底部导航栏显示）
    
    /** 搜索页面 - 提供搜索输入和历史记录功能 */
    object Search : Screen("search", "搜索", Icons.Default.Search)
    
    /** 
     * 搜索结果页面 - 显示搜索结果列表
     * 使用参数化路由，支持传递搜索关键词
     */
    object SearchResult : Screen("search_result/{query}", "搜索结果", Icons.Default.Search) {
        /**
         * 创建带参数的搜索结果路由
         * @param query 搜索关键词
         * @return 完整的路由路径
         */
        fun createRoute(query: String) = "search_result/$query"
    }
}

/**
 * 底部导航栏显示的页面列表
 * 只包含主要的5个页面，搜索页面通过其他方式访问
 * 
 * 页面顺序：首页 -> 游戏 -> 福利合集 -> 软件 -> 我的
 */
val bottomNavItems = listOf(
    Screen.Home,
    Screen.Games,
    Screen.Benefits,
    Screen.Software,
    Screen.Profile
)

/**
 * 应用导航组件
 * 使用Jetpack Navigation Compose管理应用内的页面导航
 * 
 * 主要功能：
 * - 配置所有页面的路由和组件映射
 * - 处理页面间的导航逻辑
 * - 管理导航参数传递
 * - 提供统一的导航入口
 * 
 * 导航结构：
 * - 主页面：通过底部导航栏切换
 * - 搜索流程：主页面 -> 搜索页面 -> 搜索结果页面
 * - 返回逻辑：支持系统返回键和自定义返回按钮
 * 
 * @param navController 导航控制器，用于管理页面跳转
 * @param modifier 修饰符，用于自定义组件样式
 */
@Composable
fun MarketNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // NavHost是导航的容器，定义所有可导航的页面
    NavHost(
        navController = navController, // 导航控制器
        startDestination = Screen.Home.route, // 应用启动时的默认页面
        modifier = modifier
    ) {
        // 首页路由配置
        composable(Screen.Home.route) {
            HomeScreen(
                // 传递搜索导航回调，点击搜索框时跳转到搜索页面
                onNavigateToSearch = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }
        
        // 游戏页面路由配置
        composable(Screen.Games.route) {
            GamesScreen(
                // 传递搜索导航回调
                onNavigateToSearch = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }
        
        // 福利合集页面路由配置
        composable(Screen.Benefits.route) {
            BenefitsScreen() // 无需搜索功能，直接显示WebView内容
        }
        
        // 软件页面路由配置
        composable(Screen.Software.route) {
            SoftwareScreen(
                // 传递搜索导航回调
                onNavigateToSearch = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }
        
        // 个人中心页面路由配置
        composable(Screen.Profile.route) {
            PlaceholderScreen(title = "我的") // 使用占位符屏幕展示个人中心功能
        }
        
        // 搜索页面路由配置
        composable(Screen.Search.route) {
            SearchScreen(
                // 返回上一页的回调
                onNavigateBack = { 
                    navController.popBackStack() // 从导航栈中弹出当前页面
                },
                // 执行搜索的回调，跳转到搜索结果页面
                onSearch = { query ->
                    navController.navigate(Screen.SearchResult.createRoute(query))
                }
            )
        }
        
        // 搜索结果页面路由配置（带参数）
        composable(
            route = Screen.SearchResult.route, // 参数化路由模式
            arguments = listOf(
                // 定义路由参数：query为字符串类型
                navArgument("query") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // 从导航参数中获取搜索关键词
            val query = backStackEntry.arguments?.getString("query") ?: ""
            
            SearchResultScreen(
                query = query, // 传递搜索关键词给搜索结果页面
                // 返回上一页的回调
                onNavigateBack = { 
                    navController.popBackStack() // 返回到搜索页面
                }
            )
        }
    }
} 