package com.example.market.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.market.data.model.AppItem
import com.example.market.data.model.BannerItem
import com.example.market.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 搜索栏
        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = viewModel::onSearchQueryChanged
        )
        
        // 顶部Tab
        TopTabRow(
            selectedTab = uiState.selectedTopTab,
            onTabSelected = viewModel::onTopTabSelected
        )
        
        // 主要内容
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 轮播图
            if (uiState.bannerItems.isNotEmpty()) {
                item {
                    BannerCarousel(banners = uiState.bannerItems)
                }
            }
            
            // 不同tab显示不同的标题
            item {
                val sectionTitle = when (uiState.selectedTopTab) {
                    "精选" -> "精品随心选"
                    "应用时刻" -> "热门应用推荐"
                    "小游戏" -> "精品游戏合集"
                    "热门" -> "热门应用榜单"
                    "必备" -> "装机必备应用"
                    else -> "应用推荐"
                }
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = sectionTitle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            }
            
            // 应用列表
            if (uiState.isLoading && uiState.featuredApps.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else {
                items(uiState.featuredApps) { app ->
                    AppListItem(app = app)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            placeholder = { 
                Text(
                    "搜索应用...",
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
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp)
        )
        
        IconButton(
            onClick = { /* 下载按钮点击事件 */ },
            modifier = Modifier
                .size(56.dp)
                .background(
                    Color.Gray.copy(alpha = 0.1f),
                    CircleShape
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

@Composable
fun TopTabRow(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    val tabs = listOf("应用时刻", "精选", "小游戏", "热门", "必备")
    
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tabs) { tab ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onTabSelected(tab) }
            ) {
                Text(
                    text = tab,
                    fontSize = 16.sp,
                    fontWeight = if (tab == selectedTab) FontWeight.Bold else FontWeight.Normal,
                    color = if (tab == selectedTab) MaterialTheme.colorScheme.primary else Color.Gray
                )
                if (tab == selectedTab) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height(2.dp)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(1.dp)
                            )
                    )
                }
            }
        }
    }
    
    HorizontalDivider(
        modifier = Modifier.padding(top = 12.dp),
        color = Color.Gray.copy(alpha = 0.2f)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerCarousel(banners: List<BannerItem>) {
    // 轮播数据
    val carouselItems = remember {
        listOf(
            BannerItem(
                id = "1",
                title = "🎉 618狂欢节",
                subtitle = "精品应用限时免费下载",
                backgroundColor = 0xFFFF6B6B,
                buttonText = "立即抢购"
            ),
            BannerItem(
                id = "2", 
                title = "🎮 游戏盛典",
                subtitle = "热门游戏全场5折起",
                backgroundColor = 0xFF4ECDC4,
                buttonText = "马上体验"
            ),
            BannerItem(
                id = "3",
                title = "💼 办公神器",
                subtitle = "提升效率必备工具",
                backgroundColor = 0xFF45B7D1,
                buttonText = "免费下载"
            ),
            BannerItem(
                id = "4",
                title = "🔥 新品首发",
                subtitle = "最新应用抢先体验",
                backgroundColor = 0xFF96CEB4,
                buttonText = "立即尝鲜"
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
                                    0 -> "🛍️"
                                    1 -> "🎯"
                                    2 -> "⚡"
                                    else -> "✨"
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
fun AppListItem(app: AppItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 应用图标（用纯色背景代替）
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        Color(app.iconColor),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = app.name.take(2),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
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
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "评分",
                        modifier = Modifier.size(14.dp),
                        tint = Color(0xFFFFB800)
                    )
                    Text(
                        text = app.rating.toString(),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "↓${app.downloadCount}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                Text(
                    text = app.description,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            // 安装按钮
            Button(
                onClick = { /* 安装按钮点击事件 */ },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f),
                    contentColor = Color(0xFF4CAF50)
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text("安装", fontSize = 12.sp)
            }
        }
    }
} 