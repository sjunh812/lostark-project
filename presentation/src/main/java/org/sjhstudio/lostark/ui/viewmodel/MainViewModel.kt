package org.sjhstudio.lostark.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.sjhstudio.lostark.domain.model.LostArkApiResult
import org.sjhstudio.lostark.domain.model.response.Profile
import org.sjhstudio.lostark.domain.repository.ArmoryRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val armoryRepository: ArmoryRepository
) : ViewModel() {

    private var _profile = MutableStateFlow<LostArkApiResult<Profile>?>(null)
    val profile = _profile.asStateFlow()

    init {
        getProfile("흑당곡물라떼")
    }

    fun getProfile(characterName: String) = viewModelScope.launch {
        armoryRepository.getProfile(characterName)
            .onStart { }
            .onCompletion { }
            .catch { }
            .collectLatest { apiResult ->
                _profile.emit(apiResult)
            }
    }
}