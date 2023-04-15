package org.sjhstudio.lostark.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.sjhstudio.lostark.domain.model.response.History
import org.sjhstudio.lostark.domain.repository.HistoryRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _searchHistoryList = MutableStateFlow<List<History>>(emptyList())
    val searchHistoryList = _searchHistoryList.asStateFlow()

    init {
        getSearchHistoryList()
    }

    private fun getSearchHistoryList() = viewModelScope.launch {
        historyRepository.getHistoryList()
            .onStart { }
            .onCompletion { }
            .catch { }
            .collectLatest { list ->
                _searchHistoryList.emit(list)
            }
    }

    fun deleteSearchHistory(history: History) = viewModelScope.launch {
        historyRepository.deleteHistory(history)
    }
}