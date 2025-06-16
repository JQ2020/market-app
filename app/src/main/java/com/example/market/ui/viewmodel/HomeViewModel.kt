package com.example.market.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.market.data.model.AppItem
import com.example.market.data.model.BannerItem
import com.example.market.data.repository.MarketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 首页UI状态数据类
 * 定义首页所有可能的UI状态和数据
 * 
 * 使用数据类的优势：
 * - 不可变性：确保状态的一致性
 * - 结构化：清晰地组织UI相关数据
 * - 易于测试：便于单元测试验证状态变化
 * 
 * @param bannerItems 轮播横幅列表，用于顶部轮播展示
 * @param featuredApps 应用列表，根据选中的Tab显示不同分类的应用
 * @param isLoading 加载状态，控制加载指示器的显示
 * @param selectedTopTab 当前选中的顶部Tab，默认为"精选"
 */
data class HomeUiState(
    val bannerItems: List<BannerItem> = emptyList(),
    val featuredApps: List<AppItem> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTopTab: String = "精选"
)

/**
 * 首页视图模型类
 * 负责管理首页的业务逻辑和UI状态
 * 
 * 主要职责：
 * - 管理首页UI状态（横幅、应用列表、加载状态等）
 * - 处理用户交互（Tab切换、数据刷新等）
 * - 与Repository层交互获取数据
 * - 提供响应式的数据流给UI层
 * 
 * 架构模式：
 * - MVVM模式：分离UI逻辑和业务逻辑
 * - 单向数据流：UI状态统一管理和分发
 * - 响应式编程：使用StateFlow提供数据流
 * 
 * 生命周期：
 * - 与UI组件生命周期绑定
 * - 自动处理协程作用域管理
 * - 支持配置变更时的状态保持
 * 
 * @param repository 数据仓库实例，提供数据访问接口
 */
class HomeViewModel(
    private val repository: MarketRepository = MarketRepository()
) : ViewModel() {
    
    // 私有可变状态流，只能在ViewModel内部修改
    private val _uiState = MutableStateFlow(HomeUiState())
    
    // 公开只读状态流，供UI层订阅状态变化
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    /**
     * ViewModel初始化时自动调用
     * 加载首页初始数据
     */
    init {
        loadInitialData()
    }
    
    /**
     * 加载首页初始数据
     * 包括轮播横幅和默认Tab的应用列表
     * 
     * 执行流程：
     * 1. 设置加载状态为true
     * 2. 并行加载横幅数据和应用数据
     * 3. 更新UI状态
     * 4. 处理异常情况
     */
    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // 加载横幅数据
                repository.getBannerItems().collect { banners ->
                    _uiState.value = _uiState.value.copy(bannerItems = banners)
                }
                
                // 加载默认tab的数据
                loadDataForTab(_uiState.value.selectedTopTab)
            } catch (e: Exception) {
                // 异常处理：停止加载状态，保持其他数据不变
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
    
    /**
     * 处理顶部Tab选择事件
     * 当用户点击不同的Tab时调用此方法
     * 
     * 优化策略：
     * - 避免重复加载：只有当选择的Tab与当前不同时才加载数据
     * - 即时反馈：立即更新选中状态和加载状态
     * - 异步加载：在后台加载数据，不阻塞UI
     * 
     * @param tab 用户选择的Tab名称（精选、应用时刻、小游戏、热门、必备）
     */
    fun onTopTabSelected(tab: String) {
        if (tab != _uiState.value.selectedTopTab) {
            // 立即更新选中的Tab和加载状态
            _uiState.value = _uiState.value.copy(
                selectedTopTab = tab,
                isLoading = true
            )
            // 异步加载对应Tab的数据
            loadDataForTab(tab)
        }
    }
    
    /**
     * 根据Tab加载对应的应用数据
     * 私有方法，统一处理不同Tab的数据加载逻辑
     * 
     * 数据流处理：
     * 1. 调用Repository获取指定分类的应用数据
     * 2. 使用Flow.collect持续监听数据变化
     * 3. 更新UI状态中的应用列表和加载状态
     * 4. 异常处理确保UI状态的一致性
     * 
     * @param tab Tab名称，用于确定加载哪个分类的应用数据
     */
    private fun loadDataForTab(tab: String) {
        viewModelScope.launch {
            try {
                repository.getAppsByCategory(tab).collect { apps ->
                    _uiState.value = _uiState.value.copy(
                        featuredApps = apps,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                // 异常处理：停止加载状态，保持应用列表不变
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
} 