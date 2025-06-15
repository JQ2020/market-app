package com.example.market.data.model

data class AppItem(
    val id: String,
    val name: String,
    val description: String,
    val rating: Float,
    val downloadCount: String,
    val iconColor: Long = 0xFFFF6B6B,
    val category: String = "",
    val size: String = "",
    val hasUpdate: Boolean = false
)

data class BannerItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val backgroundColor: Long = 0xFFFF6B6B,
    val buttonText: String = "立即查看"
) 