package com.example.market.data.repository

import com.example.market.data.model.AppItem
import com.example.market.data.model.BannerItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarketRepository {
    
    fun getBannerItems(): Flow<List<BannerItem>> = flow {
        delay(300) // 模拟网络延迟
        emit(
            listOf(
                BannerItem(
                    id = "1",
                    title = "618 嗨购狂欢",
                    subtitle = "AI 福利推荐巨来袭",
                    backgroundColor = 0xFFFF6B6B,
                    buttonText = "立即查看"
                )
            )
        )
    }
    
    fun getFeaturedApps(): Flow<List<AppItem>> = flow {
        delay(300) // 模拟网络延迟
        emit(getFeaturedAppsList())
    }
    
    fun getAppsByCategory(category: String): Flow<List<AppItem>> = flow {
        delay(300)
        val apps = when (category) {
            "精选" -> getFeaturedAppsList()
            "应用时刻" -> getPopularAppsList()
            "小游戏" -> getGameAppsList()
            "热门" -> getHotAppsList()
            "必备" -> getEssentialAppsList()
            else -> getFeaturedAppsList()
        }
        emit(apps)
    }
    
    private fun getFeaturedAppsList() = listOf(
        AppItem(
            id = "1",
            name = "金铲铲之战（赛博城市）",
            description = "登顶！赛博城市！",
            rating = 1.9f,
            downloadCount = "1.3 亿次",
            iconColor = 0xFF4A90E2,
            category = "游戏"
        ),
        AppItem(
            id = "2",
            name = "三国志·战略版（新赛季）",
            description = "近1个月有新版上线",
            rating = 4.1f,
            downloadCount = "4610 万次",
            iconColor = 0xFF8B4513,
            category = "游戏"
        ),
        AppItem(
            id = "3",
            name = "度小满金融",
            description = "额度最高20万元",
            rating = 4.5f,
            downloadCount = "4448 万次",
            iconColor = 0xFFE53E3E,
            category = "金融"
        ),
        AppItem(
            id = "4",
            name = "龙魂旅人",
            description = "1140个深圳人在玩",
            rating = 4.3f,
            downloadCount = "59.8 万次",
            iconColor = 0xFF9F7AEA,
            category = "游戏"
        ),
        AppItem(
            id = "5",
            name = "番茄免费小说",
            description = "简洁易用，快速搜索",
            rating = 4.7f,
            downloadCount = "52 亿次",
            iconColor = 0xFFFF5722,
            category = "阅读"
        )
    )
    
    private fun getPopularAppsList() = listOf(
        AppItem(
            id = "p1",
            name = "微信",
            description = "一个生活方式",
            rating = 4.8f,
            downloadCount = "100 亿次",
            iconColor = 0xFF07C160,
            category = "社交"
        ),
        AppItem(
            id = "p2",
            name = "QQ",
            description = "乐在沟通",
            rating = 4.6f,
            downloadCount = "80 亿次",
            iconColor = 0xFF1296DB,
            category = "社交"
        ),
        AppItem(
            id = "p3",
            name = "支付宝",
            description = "生活好，支付宝",
            rating = 4.7f,
            downloadCount = "50 亿次",
            iconColor = 0xFF1678FF,
            category = "金融"
        ),
        AppItem(
            id = "p4",
            name = "淘宝",
            description = "淘！我喜欢",
            rating = 4.5f,
            downloadCount = "30 亿次",
            iconColor = 0xFFFF4400,
            category = "购物"
        ),
        AppItem(
            id = "p5",
            name = "抖音",
            description = "记录美好生活",
            rating = 4.4f,
            downloadCount = "25 亿次",
            iconColor = 0xFF000000,
            category = "娱乐"
        )
    )
    
    private fun getGameAppsList() = listOf(
        AppItem(
            id = "g1",
            name = "王者荣耀",
            description = "5v5英雄公平对战手游",
            rating = 4.6f,
            downloadCount = "15 亿次",
            iconColor = 0xFF1E90FF,
            category = "游戏"
        ),
        AppItem(
            id = "g2",
            name = "和平精英",
            description = "腾讯光子工作室群自研射击手游",
            rating = 4.5f,
            downloadCount = "12 亿次",
            iconColor = 0xFF4169E1,
            category = "游戏"
        ),
        AppItem(
            id = "g3",
            name = "原神",
            description = "开放世界冒险RPG",
            rating = 4.8f,
            downloadCount = "8 亿次",
            iconColor = 0xFF9370DB,
            category = "游戏"
        ),
        AppItem(
            id = "g4",
            name = "迷你世界",
            description = "沙盒游戏",
            rating = 4.2f,
            downloadCount = "6 亿次",
            iconColor = 0xFF32CD32,
            category = "游戏"
        ),
        AppItem(
            id = "g5",
            name = "明日方舟",
            description = "策略塔防手游",
            rating = 4.7f,
            downloadCount = "3 亿次",
            iconColor = 0xFF2F4F4F,
            category = "游戏"
        )
    )
    
    private fun getHotAppsList() = listOf(
        AppItem(
            id = "h1",
            name = "百度",
            description = "有事搜一搜，没事看一看",
            rating = 4.3f,
            downloadCount = "20 亿次",
            iconColor = 0xFF2932E1,
            category = "工具"
        ),
        AppItem(
            id = "h2",
            name = "美团",
            description = "美好生活小帮手",
            rating = 4.4f,
            downloadCount = "15 亿次",
            iconColor = 0xFFFFC300,
            category = "生活"
        ),
        AppItem(
            id = "h3",
            name = "滴滴出行",
            description = "移动出行平台",
            rating = 4.2f,
            downloadCount = "10 亿次",
            iconColor = 0xFFFF6600,
            category = "交通"
        ),
        AppItem(
            id = "h4",
            name = "高德地图",
            description = "专业手机地图",
            rating = 4.6f,
            downloadCount = "12 亿次",
            iconColor = 0xFF00A0E9,
            category = "导航"
        ),
        AppItem(
            id = "h5",
            name = "网易云音乐",
            description = "音乐的力量",
            rating = 4.5f,
            downloadCount = "8 亿次",
            iconColor = 0xFFD33A31,
            category = "音乐"
        )
    )
    
    private fun getEssentialAppsList() = listOf(
        AppItem(
            id = "e1",
            name = "中国工商银行",
            description = "您身边的银行，可信赖的银行",
            rating = 4.1f,
            downloadCount = "5 亿次",
            iconColor = 0xFFCC0000,
            category = "金融"
        ),
        AppItem(
            id = "e2",
            name = "学习强国",
            description = "学而时习之，不亦说乎",
            rating = 4.6f,
            downloadCount = "3 亿次",
            iconColor = 0xFFE60012,
            category = "教育"
        ),
        AppItem(
            id = "e3",
            name = "国家反诈中心",
            description = "电信网络诈骗举报平台",
            rating = 4.8f,
            downloadCount = "2 亿次",
            iconColor = 0xFF1E6FBA,
            category = "安全"
        ),
        AppItem(
            id = "e4",
            name = "12306",
            description = "中国铁路官方购票平台",
            rating = 4.0f,
            downloadCount = "4 亿次",
            iconColor = 0xFF0066CC,
            category = "交通"
        ),
        AppItem(
            id = "e5",
            name = "个人所得税",
            description = "国家税务总局推出的官方税收管理软件",
            rating = 4.2f,
            downloadCount = "1.5 亿次",
            iconColor = 0xFF2E8B57,
            category = "政务"
        )
    )
} 