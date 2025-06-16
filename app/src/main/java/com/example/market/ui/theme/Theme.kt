package com.example.market.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * 深色主题配色方案
 * 适用于深色模式下的UI显示
 * 
 * 配色特点：
 * - 使用浅色调（80系列）确保在深色背景下的可读性
 * - primary: 主要操作和重要元素的颜色
 * - secondary: 次要操作和辅助元素的颜色
 * - tertiary: 强调色，用于特殊状态或操作
 */
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,      // 主色调 - 浅紫色
    secondary = PurpleGrey80, // 次要色调 - 浅紫灰色
    tertiary = Pink80        // 强调色调 - 浅粉色
)

/**
 * 浅色主题配色方案
 * 适用于浅色模式下的UI显示
 * 
 * 配色特点：
 * - 使用深色调（40系列）确保在浅色背景下的可读性
 * - 与深色主题形成对比，提供一致的视觉体验
 * - 符合Material Design 3的颜色系统规范
 */
private val LightColorScheme = lightColorScheme(
    primary = Purple40,      // 主色调 - 深紫色
    secondary = PurpleGrey40, // 次要色调 - 深紫灰色
    tertiary = Pink40        // 强调色调 - 深粉色

    /* 其他可自定义的颜色配置示例
     * 可以根据应用品牌需求进行自定义
     * 
     * background = Color(0xFFFFFBFE),    // 背景色
     * surface = Color(0xFFFFFBFE),       // 表面色
     * onPrimary = Color.White,           // 主色调上的文字颜色
     * onSecondary = Color.White,         // 次要色调上的文字颜色
     * onTertiary = Color.White,          // 强调色调上的文字颜色
     * onBackground = Color(0xFF1C1B1F),  // 背景上的文字颜色
     * onSurface = Color(0xFF1C1B1F),     // 表面上的文字颜色
     */
)

/**
 * 应用主题组合函数
 * 整合颜色、字体、形状等主题元素，为整个应用提供统一的视觉风格
 * 
 * 主要功能：
 * - 自动检测系统主题模式（深色/浅色）
 * - 支持Android 12+的动态颜色系统
 * - 提供主题切换的灵活性
 * - 确保主题的一致性和可访问性
 * 
 * 技术特性：
 * - 响应式主题：根据系统设置自动切换
 * - 动态颜色：Android 12+设备可使用壁纸颜色
 * - 向后兼容：低版本设备使用预定义颜色
 * 
 * @param darkTheme 是否使用深色主题，默认跟随系统设置
 * @param dynamicColor 是否启用动态颜色，默认启用（需Android 12+）
 * @param content 应用的UI内容，将应用此主题
 */
@Composable
fun MarketTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // 默认跟随系统深色模式设置
    dynamicColor: Boolean = true,               // 默认启用动态颜色（Android 12+）
    content: @Composable () -> Unit
) {
    // 根据条件选择合适的配色方案
    val colorScheme = when {
        // 优先级1：动态颜色（Android 12+）
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                dynamicDarkColorScheme(context)  // 动态深色主题
            } else {
                dynamicLightColorScheme(context) // 动态浅色主题
            }
        }
        
        // 优先级2：预定义深色主题
        darkTheme -> DarkColorScheme
        
        // 优先级3：预定义浅色主题（默认）
        else -> LightColorScheme
    }

    // 应用Material Design 3主题
    MaterialTheme(
        colorScheme = colorScheme, // 应用选定的配色方案
        typography = Typography,   // 应用字体排版系统
        content = content         // 渲染应用内容
    )
}