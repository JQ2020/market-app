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

data class HomeUiState(
    val bannerItems: List<BannerItem> = emptyList(),
    val featuredApps: List<AppItem> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTopTab: String = "精选",
    val searchQuery: String = ""
)

class HomeViewModel(
    private val repository: MarketRepository = MarketRepository()
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadInitialData()
    }
    
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
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
    
    fun onTopTabSelected(tab: String) {
        if (tab != _uiState.value.selectedTopTab) {
            _uiState.value = _uiState.value.copy(
                selectedTopTab = tab,
                isLoading = true
            )
            loadDataForTab(tab)
        }
    }
    
    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }
    
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
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
} 