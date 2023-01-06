package org.sjhstudio.lostark.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.sjhstudio.lostark.domain.model.LostArkApiResult
import org.sjhstudio.lostark.domain.model.response.Engraving
import org.sjhstudio.lostark.domain.model.response.Equipment
import org.sjhstudio.lostark.domain.model.response.Profile
import org.sjhstudio.lostark.domain.repository.ArmoryRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val armoryRepository: ArmoryRepository
) : ViewModel() {

    private var _profile = MutableStateFlow<LostArkApiResult<Profile>?>(null)
    val profile = _profile.asStateFlow()

    private var _engraving = MutableStateFlow<LostArkApiResult<Engraving>?>(null)
    val engraving = _engraving.asStateFlow()

    private var _equipment = MutableStateFlow<LostArkApiResult<List<Equipment>>?>(null)
    val equipment = _equipment.asStateFlow()

    private var _collapseEquipment = MutableStateFlow<Boolean>(true)
    val collapseEquipment = _collapseEquipment.asStateFlow()

    init {
        search("백두사단")
    }

    fun search(characterName: String) {
        getProfile(characterName)
        getEngraving(characterName)
        getEquipment(characterName)
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

    fun getEngraving(characterName: String) = viewModelScope.launch {
        armoryRepository.getEngraving(characterName)
            .onStart { }
            .onCompletion { }
            .catch { }
            .collectLatest { apiResult ->
                _engraving.emit(apiResult)
            }
    }

    fun getEquipment(characterName: String) = viewModelScope.launch {
        armoryRepository.getEquipment(characterName)
            .onStart { }
            .onCompletion { }
            .catch { }
            .collectLatest { apiResult ->
                _equipment.emit(apiResult)
            }
    }

    fun changeEquipmentDetail() = viewModelScope.launch {
        println("xxx ??")
        _collapseEquipment.emit(!collapseEquipment.value)
    }
}