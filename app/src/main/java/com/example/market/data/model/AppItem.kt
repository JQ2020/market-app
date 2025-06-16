package com.example.market.data.model

/**
 * 应用项目数据模型
 * 用于表示应用商店中的单个应用信息
 * 
 * 该数据类包含了应用在列表中显示所需的所有基本信息，
 * 包括应用标识、基本信息、评分、下载量等核心数据
 * 
 * @param id 应用唯一标识符，用于区分不同应用
 * @param name 应用名称，显示给用户的应用标题
 * @param description 应用描述，简要说明应用功能
 * @param rating 应用评分，范围通常为0.0-5.0
 * @param downloadCount 下载次数，格式化后的字符串（如"1.2万次"）
 * @param iconColor 应用图标背景色，默认为红色(0xFFFF6B6B)
 * @param category 应用分类，如"工具"、"游戏"等
 * @param size 应用大小，格式化后的字符串（如"25.6MB"）
 * @param hasUpdate 是否有可用更新，用于显示更新提示
 */
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

/**
 * 轮播横幅数据模型
 * 用于表示首页轮播区域的横幅广告或推荐内容
 * 
 * 该数据类定义了轮播卡片的视觉和内容信息，
 * 支持自定义背景色和行动按钮文字
 * 
 * @param id 横幅唯一标识符，用于区分不同横幅
 * @param title 横幅主标题，吸引用户注意的主要文字
 * @param subtitle 横幅副标题，提供额外的描述信息
 * @param backgroundColor 横幅背景色，默认为红色(0xFFFF6B6B)
 * @param buttonText 行动按钮文字，默认为"立即查看"
 */
data class BannerItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val backgroundColor: Long = 0xFFFF6B6B,
    val buttonText: String = "立即查看"
) 