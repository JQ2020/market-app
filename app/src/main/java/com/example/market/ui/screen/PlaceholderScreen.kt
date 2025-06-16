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

data class ManagementItem(
    val title: String,
    val subtitle: String,
    val count: String,
    val icon: ImageVector,
    val iconColor: Color,
    val backgroundColor: Color
)

data class SystemUsageData(
    val appName: String,
    val iconColor: Color,
    val usageValue: String,
    val status: String
)

@Composable
fun PlaceholderScreen(title: String) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 用户信息卡片
        item {
            ProfileUserCard()
        }
        
        // 应用管理功能
        item {
            AppManagementSection()
        }
        
        // 系统存储管理
        item {
            SystemStorageSection()
        }
        
        // 系统健康状态
        item {
            SystemHealthSection()
        }
        
        // 系统使用统计
        item {
            SystemUsageSection()
        }
        
        // 系统工具
        item {
            SystemToolsSection()
        }
        
        // 设置选项
        item {
            SettingsSection()
        }
    }
}

@Composable
fun ProfileUserCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 头像
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF9C27B0)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "用户头像",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                
                Column {
                    Text(
                        text = "Kratos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFD700)
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
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF4CAF50)
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
            
            OutlinedButton(
                onClick = { },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFF2196F3),
                    contentColor = Color.White
                ),
                border = null,
                shape = RoundedCornerShape(20.dp),
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

@Composable
fun AppManagementSection() {
    val managementItems = listOf(
        ManagementItem(
            "应用更新",
            "23个可更新",
            "23",
            Icons.Default.Update,
            Color.White,
            Color(0xFFE91E63)
        ),
        ManagementItem(
            "应用备份",
            "云端同步",
            "",
            Icons.Default.CloudUpload,
            Color.White,
            Color(0xFF2196F3)
        ),
        ManagementItem(
            "应用锁",
            "隐私保护",
            "5",
            Icons.Default.Lock,
            Color.White,
            Color(0xFF4CAF50)
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
            Text(
                text = "应用管理",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                managementItems.forEach { item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .background(item.backgroundColor, RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (item.count.isNotEmpty()) {
                                Badge(
                                    modifier = Modifier.offset(x = 20.dp, y = (-20).dp)
                                ) {
                                    Text(item.count, fontSize = 10.sp)
                                }
                            }
                            Icon(
                                item.icon,
                                contentDescription = item.title,
                                tint = item.iconColor,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        
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
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "存储空间管理",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "建议清理 35 个缓存文件",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                LinearProgressIndicator(
                    progress = 0.72f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = Color(0xFFFF9800),
                    trackColor = Color(0xFFE0E0E0)
                )
                
                Text(
                    text = "360.2 GB/500 GB",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = "查看详情",
                tint = Color.Gray
            )
        }
    }
}

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
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "88",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
                Text(
                    text = "分",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = "查看详情",
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun SystemUsageSection() {
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(data.iconColor.copy(alpha = 0.1f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(data.iconColor, CircleShape)
                                )
                            }
                            
                            Text(
                                text = data.appName,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = data.usageValue,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = data.iconColor
                            )
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
            Text(
                text = "系统工具箱",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val tools = listOf(
                    "文件管理" to Icons.Default.Folder,
                    "应用锁" to Icons.Default.Lock,
                    "网络测试" to Icons.Default.NetworkCheck,
                    "系统信息" to Icons.Default.Info
                )
                
                tools.forEach { (title, icon) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .size(50.dp)
                                .background(
                                    Color(0xFF2196F3).copy(alpha = 0.1f),
                                    CircleShape
                                )
                        ) {
                            Icon(
                                icon,
                                contentDescription = title,
                                tint = Color(0xFF2196F3),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        
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
            Text(
                text = "设置",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            val settings = listOf(
                "账户安全" to Icons.Default.Security,
                "隐私设置" to Icons.Default.PrivacyTip,
                "通知设置" to Icons.Default.Notifications,
                "关于我们" to Icons.Default.Info
            )
            
            Column {
                settings.forEach { (title, icon) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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