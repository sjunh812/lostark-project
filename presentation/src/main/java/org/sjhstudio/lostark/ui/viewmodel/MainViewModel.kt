package org.sjhstudio.lostark.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.sjhstudio.lostark.base.UiState
import org.sjhstudio.lostark.domain.model.CharacterInfo
import org.sjhstudio.lostark.domain.repository.CharacterInfoRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val characterInfoRepository: CharacterInfoRepository
) : ViewModel() {

    private var _characterInfoUiState = MutableStateFlow<UiState<CharacterInfo>>(UiState.Loading)
    val characterInfoUiState = _characterInfoUiState.asStateFlow()

    init {
        getUserInfo("아가벽력일섬")
    }

    fun getUserInfo(userName: String) = viewModelScope.launch {
        characterInfoRepository.getCharacterInfo(userName)
            .onStart { }
            .onCompletion { }
            .catch { }
            .collectLatest { apiResult ->
                apiResult.data?.let { data ->
                    _characterInfoUiState.emit(UiState.Success(data))
                } ?: _characterInfoUiState.emit(UiState.Fail("error"))
            }
    }
}