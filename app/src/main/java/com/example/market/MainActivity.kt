package com.example.market

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.market.navigation.MarketNavigation
import com.example.market.navigation.bottomNavItems
import com.example.market.ui.theme.MarketTheme

/**
 * 主活动类
 * Android应用商店的入口Activity，负责初始化应用和设置UI内容
 * 
 * 主要功能：
 * - 启用边到边显示模式（Edge-to-Edge）
 * - 设置应用主题
 * - 初始化Compose UI内容
 * - 作为整个应用的容器
 */
class MainActivity : ComponentActivity() {
    
    /**
     * Activity创建时的回调方法
     * 在此方法中完成Activity的初始化工作
     * 
     * @param savedInstanceState 保存的实例状态，用于恢复Activity状态
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 启用边到边显示模式，让内容可以延伸到状态栏和导航栏区域
        enableEdgeToEdge()
        
        // 设置Compose UI内容
        setContent {
            // 应用主题包装器
            MarketTheme {
                // 主应用组件
                MarketApp()
            }
        }
    }
}

/**
 * 应用主组件
 * 构建整个应用的UI结构，包括底部导航栏和主要内容区域
 * 
 * 功能特点：
 * - 使用Scaffold布局结构
 * - 集成底部导航栏
 * - 管理导航状态
 * - 处理页面切换逻辑
 * - 提供统一的UI容器
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketApp() {
    // 创建导航控制器，用于管理页面间的导航
    val navController = rememberNavController()
    
    // 监听导航栈变化，获取当前页面信息
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    
    // 获取当前路由，用于判断哪个底部导航项被选中
    val currentRoute = navBackStackEntry?.destination?.route
    
    // 使用Scaffold提供标准的Material Design布局结构
    Scaffold(
        modifier = Modifier.fillMaxSize(), // 填满整个屏幕
        bottomBar = {
            // 底部导航栏组件
            NavigationBar(
                containerColor = Color.White, // 白色背景
                tonalElevation = 8.dp // 8dp的阴影高度
            ) {
                // 遍历底部导航项列表，创建导航按钮
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        // 导航项图标
                        icon = { 
                            Icon(
                                screen.icon, 
                                contentDescription = screen.title // 无障碍描述
                            ) 
                        },
                        // 导航项标签文字
                        label = { 
                            Text(
                                screen.title,
                                fontSize = 10.sp, // 小字体
                                // 选中状态使用粗体，未选中使用普通字体
                                fontWeight = if (currentRoute == screen.route) FontWeight.Bold else FontWeight.Normal
                            ) 
                        },
                        // 是否为当前选中项
                        selected = currentRoute == screen.route,
                        // 点击事件处理
                        onClick = {
                            // 导航到对应页面
                            navController.navigate(screen.route) {
                                // 弹出到起始页面，避免导航栈过深
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true // 保存页面状态
                                }
                                launchSingleTop = true // 避免重复创建相同页面
                                restoreState = true // 恢复页面状态
                            }
                        },
                        // 导航项颜色配置
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary, // 选中图标颜色
                            selectedTextColor = MaterialTheme.colorScheme.primary, // 选中文字颜色
                            unselectedIconColor = Color.Gray, // 未选中图标颜色
                            unselectedTextColor = Color.Gray, // 未选中文字颜色
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) // 选中指示器颜色
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        // 主要内容区域，使用导航组件管理页面切换
        MarketNavigation(
            navController = navController, // 传入导航控制器
            modifier = Modifier.padding(innerPadding) // 应用内边距，避免被底部导航栏遮挡
        )
    }
}