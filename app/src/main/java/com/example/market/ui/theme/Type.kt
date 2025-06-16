package com.example.market.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * 应用字体排版主题定义文件
 * 基于Material Design 3的Typography系统
 * 
 * 功能说明：
 * - 定义应用中所有文本的样式规范
 * - 确保整个应用的字体风格一致性
 * - 支持可访问性和可读性要求
 * 
 * 扩展建议：
 * 可以根据应用需求添加更多文本样式，如：
 * - titleLarge: 大标题样式
 * - titleMedium: 中等标题样式
 * - labelSmall: 小标签样式
 * - headlineSmall: 小标题样式
 */

/**
 * Material Design 3 字体排版系统
 * 当前配置了基础的bodyLarge样式，其他样式使用系统默认值
 * 
 * bodyLarge配置说明：
 * - fontFamily: 使用系统默认字体，确保跨平台兼容性
 * - fontWeight: 正常字重，适合大部分正文内容
 * - fontSize: 16sp，符合Material Design可读性标准
 * - lineHeight: 24sp，提供良好的行间距
 * - letterSpacing: 0.5sp，轻微的字符间距增强可读性
 */
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default, // 系统默认字体
        fontWeight = FontWeight.Normal,  // 正常字重
        fontSize = 16.sp,               // 字体大小16sp
        lineHeight = 24.sp,             // 行高24sp
        letterSpacing = 0.5.sp          // 字符间距0.5sp
    )
    
    /* 其他可用的文本样式配置示例
     * 可以根据应用需求取消注释并自定义
     * 
     * titleLarge = TextStyle(
     *     fontFamily = FontFamily.Default,
     *     fontWeight = FontWeight.Normal,
     *     fontSize = 22.sp,
     *     lineHeight = 28.sp,
     *     letterSpacing = 0.sp
     * ),
     * labelSmall = TextStyle(
     *     fontFamily = FontFamily.Default,
     *     fontWeight = FontWeight.Medium,
     *     fontSize = 11.sp,
     *     lineHeight = 16.sp,
     *     letterSpacing = 0.5.sp
     * )
     */
)