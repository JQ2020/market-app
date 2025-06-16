package com.example.market.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.market.data.model.AppItem
import com.example.market.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit,
    onSearch: (String) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val uiState by viewModel.uiState.collectAsState()
    
    // 模拟历史搜索记录
    val searchHistory = remember {
        listOf("微信", "抖音", "支付宝", "QQ", "淘宝", "美团", "滴滴出行", "网易云音乐")
    }
    
    // 热门搜索词
    val hotSearches = remember {
        listOf("游戏", "社交", "购物", "音乐", "视频", "工具", "教育", "健康")
    }
    
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // 顶部搜索栏
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )
        
        // 搜索输入框
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    placeholder = { 
                        Text(
                            "搜索应用、游戏...",
                            fontSize = 14.sp,
                            color = Color.Gray
                        ) 
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "搜索", tint = Color.Gray)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "清除", tint = Color.Gray)
                            }
                        }
                    },
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (searchQuery.isNotEmpty()) {
                                keyboardController?.hide()
                                onSearch(searchQuery)
                            }
                        }
                    )
                )
                
                Button(
                    onClick = {
                        if (searchQuery.isNotEmpty()) {
                            keyboardController?.hide()
                            onSearch(searchQuery)
                        }
                    },
                    enabled = searchQuery.isNotEmpty(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("搜索", fontSize = 14.sp)
                }
            }
        }
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 历史搜索
            if (searchHistory.isNotEmpty()) {
                item {
                    SearchSection(
                        title = "历史搜索",
                        items = searchHistory,
                        onItemClick = { query ->
                            searchQuery = query
                            onSearch(query)
                        },
                        onClearAll = { /* 清除历史搜索 */ }
                    )
                }
            }
            
            // 热门搜索
            item {
                SearchSection(
                    title = "热门搜索",
                    items = hotSearches,
                    onItemClick = { query ->
                        searchQuery = query
                        onSearch(query)
                    },
                    showClear = false
                )
            }
            
            // 推荐应用
            item {
                Text(
                    text = "🔥 推荐应用",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            items(uiState.featuredApps.take(10)) { app ->
                RecommendAppItem(
                    app = app,
                    onClick = { /* 点击应用 */ }
                )
            }
        }
    }
}

@Composable
fun SearchSection(
    title: String,
    items: List<String>,
    onItemClick: (String) -> Unit,
    onClearAll: (() -> Unit)? = null,
    showClear: Boolean = true
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                
                if (showClear && onClearAll != null) {
                    TextButton(onClick = onClearAll) {
                        Text(
                            "清除",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 搜索标签
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { item ->
                    SearchTag(
                        text = item,
                        onClick = { onItemClick(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchTag(
    text: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = Color.Gray.copy(alpha = 0.1f),
        modifier = Modifier.wrapContentSize()
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            fontSize = 13.sp,
            color = Color.Gray.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun RecommendAppItem(
    app: AppItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 应用图标
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = Color(app.iconColor)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = app.name.take(1),
                    color = Color.White,
                    fontSize = 18.sp,
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
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = app.description,
                    fontSize = 12.sp,
                    color = Color.Gray,
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
                        modifier = Modifier.size(12.dp),
                        tint = Color(0xFFFFB800)
                    )
                    Text(
                        text = app.rating.toString(),
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "↓${app.downloadCount}",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
            
            // 获取按钮
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f),
                    contentColor = Color(0xFF4CAF50)
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                modifier = Modifier.height(32.dp)
            ) {
                Text("获取", fontSize = 12.sp)
            }
        }
    }
} 