package org.sjhstudio.lostark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.sjhstudio.domain.model.UserInfo
import org.sjhstudio.domain.repository.UserInfoRepository
import org.sjhstudio.lostark.base.UiState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {

    private var _userInfoUiState = MutableStateFlow<UiState<UserInfo>>(UiState.Loading)
    val userInfoUiState = _userInfoUiState.asStateFlow()

    init {
        getUserInfo("아가벽력일섬")
    }

    fun getUserInfo(userName: String) = viewModelScope.launch {
        userInfoRepository.getUserInfo(userName)
            .onStart { }
            .onCompletion { }
            .catch { }
            .collectLatest { apiResult ->
                apiResult.data?.let { data ->
                    _userInfoUiState.emit(UiState.Success(data))
                } ?: _userInfoUiState.emit(UiState.Fail("error"))
            }
    }
}