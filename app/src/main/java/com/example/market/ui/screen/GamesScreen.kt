package com.example.market.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.market.data.model.AppItem
import com.example.market.data.model.BannerItem
import com.example.market.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@Composable
fun GamesScreen(
    viewModel: HomeViewModel = viewModel(),
    onNavigateToSearch: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 搜索栏
        item {
            GameSearchBar(onNavigateToSearch = onNavigateToSearch)
        }
        
        // 游戏横幅
        item {
            GameBannerCarousel()
        }
        
        // 热门游戏标题
        item {
            Text(
                text = "🔥 热门游戏",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        // 游戏列表
        items(uiState.featuredApps) { app ->
            GameAppItem(app = app)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameSearchBar(onNavigateToSearch: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = { },
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .clickable { onNavigateToSearch() },
            placeholder = { 
                Text(
                    "搜索游戏...",
                    fontSize = 14.sp
                ) 
            },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "搜索")
            },
            trailingIcon = {
                Icon(Icons.Default.Mic, contentDescription = "语音")
            },
            shape = RoundedCornerShape(28.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
            ),
            singleLine = true,
            readOnly = true,
            enabled = false,
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp)
        )
        
        IconButton(
            onClick = { },
            modifier = Modifier
                .size(56.dp)
                .background(
                    color = Color.Gray.copy(alpha = 0.1f),
                    shape = CircleShape
                )
        ) {
            Icon(
                Icons.Default.Download,
                contentDescription = "下载",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameBannerCarousel() {
    // 游戏轮播数据
    val carouselItems = remember {
        listOf(
            BannerItem(
                id = "1",
                title = "🎮 游戏狂欢节",
                subtitle = "热门游戏限时免费下载",
                backgroundColor = 0xFF6200EA,
                buttonText = "立即体验"
            ),
            BannerItem(
                id = "2", 
                title = "🏆 电竞大赛",
                subtitle = "参与赛事赢取丰厚奖励",
                backgroundColor = 0xFFFF5722,
                buttonText = "马上参与"
            ),
            BannerItem(
                id = "3",
                title = "🎯 新游首发",
                subtitle = "抢先体验最新游戏大作",
                backgroundColor = 0xFF009688,
                buttonText = "抢先下载"
            ),
            BannerItem(
                id = "4",
                title = "🎊 周末特惠",
                subtitle = "精品游戏全场8折优惠",
                backgroundColor = 0xFFE91E63,
                buttonText = "查看优惠"
            )
        )
    }
    
    val pagerState = rememberPagerState(pageCount = { carouselItems.size })
    
    // 自动轮播效果
    LaunchedEffect(pagerState) {
        while (true) {
            delay(2500) // 2.5秒间隔
            val nextPage = (pagerState.currentPage + 1) % carouselItems.size
            pagerState.animateScrollToPage(nextPage)
        }
    }
    
    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) { page ->
            val currentItem = carouselItems[page]
            
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(currentItem.backgroundColor))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 左侧文字内容
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = currentItem.title,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = currentItem.subtitle,
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            Surface(
                                onClick = { /* 按钮点击事件 */ },
                                color = Color.White.copy(alpha = 0.25f),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.wrapContentSize()
                            ) {
                                Text(
                                    text = currentItem.buttonText,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                        }
                        
                        // 右侧装饰图标
                        Box(
                            modifier = Modifier.size(80.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = when (page % 4) {
                                    0 -> "🎯"
                                    1 -> "🏆"
                                    2 -> "🚀"
                                    else -> "🎊"
                                },
                                fontSize = 40.sp
                            )
                        }
                    }
                    
                    // 轮播指示器 - 放在卡片内部底部
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(carouselItems.size) { index ->
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(
                                        if (index == pagerState.currentPage) 
                                            Color.White.copy(alpha = 0.9f)
                                        else 
                                            Color.Gray.copy(alpha = 0.6f),
                                        CircleShape
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GameAppItem(app: AppItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 应用图标
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = Color(app.iconColor)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = app.name.take(1),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // 应用信息
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = app.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = app.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
                
                // 评分
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(5) { index ->
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = if (index < app.rating.toInt()) Color(0xFFFFD700) else Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = app.rating.toString(),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            
            // 下载按钮
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EA)
                ),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "安装",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
} 