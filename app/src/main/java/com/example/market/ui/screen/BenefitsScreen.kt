package com.example.market.ui.screen

import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.accompanist.web.*

/**
 * 福利合集页面组件
 * 使用WebView展示OPPO商城的福利活动页面
 * 
 * 主要功能：
 * - 全屏沉浸式WebView体验
 * - 自动处理页面加载状态
 * - 网络错误处理和重试机制
 * - 优化的WebView配置
 * 
 * 技术特点：
 * - 沉浸式状态栏：内容延伸到状态栏区域
 * - 硬件加速：提升页面渲染性能
 * - 响应式设计：适配不同屏幕尺寸
 * - 用户体验优化：加载提示和错误处理
 * 
 * 使用场景：
 * - 展示第三方活动页面
 * - 福利商品推荐
 * - 优惠券发放页面
 * - 营销活动展示
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BenefitsScreen() {
    // WebView状态管理 - 加载OPPO商城页面
    val state = rememberWebViewState("https://www.opposhop.cn/cn/web/")
    
    // WebView导航控制器 - 处理页面导航操作
    val navigator = rememberWebViewNavigator()
    
    // 获取当前视图引用 - 用于窗口配置
    val view = LocalView.current
    
    /**
     * 设置沉浸式状态栏效果
     * 让WebView内容可以延伸到状态栏和导航栏区域
     * 
     * 配置说明：
     * - setDecorFitsSystemWindows(false): 允许内容绘制到系统栏区域
     * - statusBarColor: 设置状态栏为透明
     * - navigationBarColor: 设置导航栏为透明
     */
    LaunchedEffect(Unit) {
        val window = (view.context as androidx.activity.ComponentActivity).window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = Color.Transparent.toArgb()
    }
    
    // 主容器 - 使用Box布局支持层叠效果
    Box(
        modifier = Modifier
            .fillMaxSize() // 填满整个屏幕
            .windowInsetsPadding(WindowInsets.systemBars) // 处理系统栏内边距
    ) {
        /**
         * WebView组件 - 核心内容展示区域
         * 
         * 配置特点：
         * - 全屏显示：提供沉浸式浏览体验
         * - JavaScript支持：确保现代网页功能正常
         * - 缓存优化：提升页面加载速度
         * - 缩放支持：用户可以手势缩放页面
         * - 硬件加速：提升渲染性能
         */
        WebView(
            state = state,
            navigator = navigator,
            modifier = Modifier.fillMaxSize(),
            onCreated = { webView ->
                // WebView详细配置
                webView.settings.apply {
                    javaScriptEnabled = true        // 启用JavaScript
                    domStorageEnabled = true        // 启用DOM存储
                    loadWithOverviewMode = true     // 概览模式加载
                    useWideViewPort = true          // 使用宽视口
                    setSupportZoom(true)            // 支持缩放
                    builtInZoomControls = true      // 内置缩放控件
                    displayZoomControls = false     // 隐藏缩放控件UI
                    // 启用硬件加速提升性能
                    setRenderPriority(android.webkit.WebSettings.RenderPriority.HIGH)
                    cacheMode = android.webkit.WebSettings.LOAD_DEFAULT // 默认缓存策略
                }
                
                // 设置WebView背景透明，与应用主题融合
                webView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            }
        )
        
        /**
         * 加载状态指示器
         * 在页面加载过程中显示，提供用户反馈
         * 
         * 设计特点：
         * - 居中显示：不干扰用户浏览
         * - 半透明背景：保持视觉层次
         * - 圆角卡片：符合Material Design规范
         * - 阴影效果：增强视觉深度
         */
        if (state.isLoading) {
            Surface(
                modifier = Modifier
                    .align(Alignment.Center) // 居中对齐
                    .padding(24.dp),        // 外边距
                shape = MaterialTheme.shapes.medium,           // 圆角形状
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f), // 半透明背景
                shadowElevation = 8.dp                         // 阴影高度
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // 圆形进度指示器
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                    // 加载提示文字
                    Text(
                        text = "正在加载福利内容...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        
        /**
         * 错误处理界面
         * 当页面加载失败时显示，提供重试选项
         * 
         * 用户体验考虑：
         * - 清晰的错误提示：告知用户发生了什么
         * - 解决方案建议：提示检查网络连接
         * - 重试按钮：允许用户快速重新加载
         * - 错误主题色：使用系统错误色彩方案
         */
        state.errorsForCurrentRequest.firstOrNull()?.let { error ->
            Surface(
                modifier = Modifier
                    .align(Alignment.Center) // 居中显示
                    .padding(24.dp),        // 外边距
                shape = MaterialTheme.shapes.medium,                              // 圆角形状
                color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.9f), // 错误背景色
                shadowElevation = 8.dp                                            // 阴影效果
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // 错误标题
                    Text(
                        text = "页面加载失败",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    // 错误描述和建议
                    Text(
                        text = "请检查网络连接后重试",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    // 重试按钮
                    Button(
                        onClick = { navigator.reload() }, // 重新加载页面
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("重新加载")
                    }
                }
            }
        }
    }
} 