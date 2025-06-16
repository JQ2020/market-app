package com.example.market.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import com.example.market.R

/**
 * 应用管理项数据类
 * 用于定义应用管理功能的各项属性
 * 
 * @param title 功能标题（如"应用更新"）
 * @param subtitle 功能副标题（如"23个可更新"）
 * @param count 数量标识（如"23"，用于显示徽章）
 * @param icon 功能图标
 * @param iconColor 图标颜色
 * @param backgroundColor 背景颜色
 */
data class ManagementItem(
    val title: String,
    val subtitle: String,
    val count: String,
    val icon: ImageVector,
    val iconColor: Color,
    val backgroundColor: Color
)

/**
 * 系统使用数据类
 * 用于展示系统各项使用统计信息
 * 
 * @param appName 统计项名称（如"应用时长"）
 * @param iconColor 指示器颜色
 * @param usageValue 使用数值（如"6.5h"）
 * @param status 状态描述（如"今日"）
 */
data class SystemUsageData(
    val appName: String,
    val iconColor: Color,
    val usageValue: String,
    val status: String
)

/**
 * 占位符屏幕组件
 * 用于"我的"页面，展示用户个人信息和系统管理功能
 * 
 * 主要功能模块：
 * - 用户信息卡片：显示头像、用户名、VIP状态等
 * - 应用管理：应用更新、备份、应用锁等功能
 * - 系统存储：存储空间使用情况和清理建议
 * - 系统健康：系统运行状态评分
 * - 使用统计：各项系统资源使用情况
 * - 系统工具：常用系统工具快捷入口
 * - 设置选项：账户、隐私、通知等设置入口
 * 
 * @param title 页面标题（当前未使用，保留参数）
 */
@Composable
fun PlaceholderScreen(title: String) {
    // 使用LazyColumn创建可滚动的垂直列表
    LazyColumn(
        modifier = Modifier
            .fillMaxSize() // 填满整个屏幕
            .background(Color(0xFFF5F5F5)), // 设置浅灰色背景
        contentPadding = PaddingValues(16.dp), // 内容边距
        verticalArrangement = Arrangement.spacedBy(16.dp) // 项目间距
    ) {
        // 用户信息卡片 - 显示用户头像、姓名、VIP状态等
        item {
            ProfileUserCard()
        }
        
        // 应用管理功能区 - 应用更新、备份、应用锁
        item {
            AppManagementSection()
        }
        
        // 系统存储管理 - 存储空间使用情况和清理建议
        item {
            SystemStorageSection()
        }
        
        // 系统健康状态 - 系统运行状态评分
        item {
            SystemHealthSection()
        }
        
        // 系统使用统计 - 应用时长、流量、电池等使用情况
        item {
            SystemUsageSection()
        }
        
        // 系统工具箱 - 文件管理、网络测试等工具
        item {
            SystemToolsSection()
        }
        
        // 设置选项 - 账户安全、隐私设置等
        item {
            SettingsSection()
        }
    }
}

/**
 * 用户信息卡片组件
 * 显示用户头像、姓名、VIP状态和个人设置按钮
 * 
 * 功能特点：
 * - 圆形头像显示
 * - VIP和认证标签
 * - 个人设置按钮
 * - 现代化卡片设计
 */
@Composable
fun ProfileUserCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp), // 圆角卡片
        colors = CardDefaults.cardColors(containerColor = Color.White) // 白色背景
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 左侧：头像和用户信息
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 用户头像容器
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape) // 圆形裁剪
                        .background(Color(0xFF9C27B0)), // 紫色背景（备用）
                    contentAlignment = Alignment.Center
                ) {
                    // 用户头像图片
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "用户头像",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop // 裁剪适配
                    )
                }
                
                // 用户信息列
                Column {
                    // 用户名
                    Text(
                        text = "Kratos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    // 用户标签行（VIP、认证）
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // VIP标签
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFD700) // 金色背景
                            )
                        ) {
                            Text(
                                text = "VIP",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        // 认证标签
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF4CAF50) // 绿色背景
                            )
                        ) {
                            Text(
                                text = "认证",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            
            // 右侧：个人设置按钮
            OutlinedButton(
                onClick = { /* TODO: 跳转到个人设置页面 */ },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFF2196F3), // 蓝色背景
                    contentColor = Color.White
                ),
                border = null, // 无边框
                shape = RoundedCornerShape(20.dp), // 圆角按钮
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier
                    .height(40.dp)
                    .widthIn(min = 80.dp)
            ) {
                Text(
                    text = "个人设置",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/**
 * 应用管理功能区组件
 * 提供应用更新、备份、应用锁等管理功能
 * 
 * 功能特点：
 * - 三个主要管理功能
 * - 图标化展示
 * - 数量徽章提示
 * - 网格布局排列
 */
@Composable
fun AppManagementSection() {
    // 定义应用管理功能项列表
    val managementItems = listOf(
        ManagementItem(
            "应用更新",
            "23个可更新",
            "23", // 显示更新数量徽章
            Icons.Default.Update,
            Color.White,
            Color(0xFFE91E63) // 粉红色背景
        ),
        ManagementItem(
            "应用备份",
            "云端同步",
            "", // 无徽章
            Icons.Default.CloudUpload,
            Color.White,
            Color(0xFF2196F3) // 蓝色背景
        ),
        ManagementItem(
            "应用锁",
            "隐私保护",
            "5", // 显示锁定应用数量
            Icons.Default.Lock,
            Color.White,
            Color(0xFF4CAF50) // 绿色背景
        )
    )
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // 区块标题
            Text(
                text = "应用管理",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // 功能项网格布局
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly // 均匀分布
            ) {
                managementItems.forEach { item ->
                    // 单个功能项列
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.weight(1f) // 等权重分布
                    ) {
                        // 功能图标容器
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .background(item.backgroundColor, RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            // 数量徽章（如果有）
                            if (item.count.isNotEmpty()) {
                                Badge(
                                    modifier = Modifier.offset(x = 20.dp, y = (-20).dp) // 右上角位置
                                ) {
                                    Text(item.count, fontSize = 10.sp)
                                }
                            }
                            // 功能图标
                            Icon(
                                item.icon,
                                contentDescription = item.title,
                                tint = item.iconColor,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        
                        // 功能文字说明
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = item.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = item.subtitle,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 系统存储管理组件
 * 显示存储空间使用情况和清理建议
 * 
 * 功能特点：
 * - 存储使用进度条
 * - 清理建议提示
 * - 存储容量显示
 * - 可点击查看详情
 */
@Composable
fun SystemStorageSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左侧：存储信息
            Column(modifier = Modifier.weight(1f)) {
                // 标题
                Text(
                    text = "存储空间管理",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                // 清理建议
                Text(
                    text = "建议清理 35 个缓存文件",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // 存储使用进度条
                LinearProgressIndicator(
                    progress = 0.72f, // 72% 使用率
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = Color(0xFFFF9800), // 橙色进度
                    trackColor = Color(0xFFE0E0E0) // 灰色轨道
                )
                
                // 存储容量文字
                Text(
                    text = "360.2 GB/500 GB",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            // 右侧：查看详情箭头
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = "查看详情",
                tint = Color.Gray
            )
        }
    }
}

/**
 * 系统健康状态组件
 * 显示系统运行状态评分和健康提示
 * 
 * 功能特点：
 * - 系统健康评分（0-100分）
 * - 状态描述文字
 * - 大数字突出显示
 * - 可点击查看详情
 */
@Composable
fun SystemHealthSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左侧：健康状态信息
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "系统健康状态",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "状态良好，运行稳定",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            // 中间：健康评分
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "88", // 健康评分
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50) // 绿色表示良好
                )
                Text(
                    text = "分",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            // 右侧：查看详情箭头
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = "查看详情",
                tint = Color.Gray
            )
        }
    }
}

/**
 * 系统使用统计组件
 * 显示各项系统资源的使用情况统计
 * 
 * 功能特点：
 * - 多项使用统计（应用时长、流量、电池等）
 * - 彩色指示器区分不同类型
 * - 数值和状态双重显示
 * - 列表形式展示
 */
@Composable
fun SystemUsageSection() {
    // 定义系统使用统计数据
    val usageData = listOf(
        SystemUsageData("应用时长", Color(0xFF2196F3), "6.5h", "今日"),
        SystemUsageData("流量使用", Color(0xFF4CAF50), "2.1GB", "今日"),
        SystemUsageData("电池消耗", Color(0xFFFF9800), "45%", "剩余"),
        SystemUsageData("内存占用", Color(0xFFE91E63), "4.2GB", "已用"),
        SystemUsageData("存储空间", Color(0xFF9C27B0), "360GB", "已用")
    )
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // 区块标题和查看详情
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "系统使用统计",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = "查看详情",
                    tint = Color.Gray
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 使用统计列表
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                usageData.forEach { data ->
                    // 单项统计行
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 左侧：指示器和名称
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // 彩色指示器
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(data.iconColor.copy(alpha = 0.1f), CircleShape), // 浅色背景
                                contentAlignment = Alignment.Center
                            ) {
                                // 实心圆点
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(data.iconColor, CircleShape)
                                )
                            }
                            
                            // 统计项名称
                            Text(
                                text = data.appName,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        
                        // 右侧：数值和状态
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            // 使用数值
                            Text(
                                text = data.usageValue,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = data.iconColor
                            )
                            // 状态描述
                            Text(
                                text = data.status,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 系统工具箱组件
 * 提供常用系统工具的快捷入口
 * 
 * 功能特点：
 * - 四个常用工具（文件管理、应用锁、网络测试、系统信息）
 * - 圆形图标按钮
 * - 网格布局排列
 * - 统一的视觉风格
 */
@Composable
fun SystemToolsSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // 区块标题
            Text(
                text = "系统工具箱",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // 工具网格
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // 定义工具列表
                val tools = listOf(
                    "文件管理" to Icons.Default.Folder,
                    "应用锁" to Icons.Default.Lock,
                    "网络测试" to Icons.Default.NetworkCheck,
                    "系统信息" to Icons.Default.Info
                )
                
                tools.forEach { (title, icon) ->
                    // 单个工具项
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // 圆形图标按钮
                        IconButton(
                            onClick = { /* TODO: 跳转到对应工具页面 */ },
                            modifier = Modifier
                                .size(50.dp)
                                .background(
                                    Color(0xFF2196F3).copy(alpha = 0.1f), // 浅蓝色背景
                                    CircleShape
                                )
                        ) {
                            Icon(
                                icon,
                                contentDescription = title,
                                tint = Color(0xFF2196F3), // 蓝色图标
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        
                        // 工具名称
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

/**
 * 设置选项组件
 * 提供各种设置功能的入口
 * 
 * 功能特点：
 * - 四个主要设置项（账户安全、隐私设置、通知设置、关于我们）
 * - 列表形式展示
 * - 统一的图标和箭头设计
 * - 可点击跳转到对应设置页面
 */
@Composable
fun SettingsSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // 区块标题
            Text(
                text = "设置",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // 定义设置项列表
            val settings = listOf(
                "账户安全" to Icons.Default.Security,
                "隐私设置" to Icons.Default.PrivacyTip,
                "通知设置" to Icons.Default.Notifications,
                "关于我们" to Icons.Default.Info
            )
            
            // 设置项列表
            Column {
                settings.forEach { (title, icon) ->
                    // 单个设置项行
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 左侧：图标和标题
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                icon,
                                contentDescription = title,
                                tint = Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = title,
                                fontSize = 14.sp
                            )
                        }
                        
                        // 右侧：进入箭头
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = "进入",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }
} 