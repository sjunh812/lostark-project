package org.sjhstudio.lostark.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.sjhstudio.lostark.domain.model.LostArkApiResult
import org.sjhstudio.lostark.domain.model.response.*
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

    private var _equipment = MutableStateFlow<LostArkApiResult<HashMap<String, Equipment>>?>(null)
    val equipment = _equipment.asStateFlow()

    private var _gem = MutableStateFlow<LostArkApiResult<Gem>?>(null)
    val gem = _gem.asStateFlow()

    private var _card = MutableStateFlow<LostArkApiResult<Card>?>(null)
    val card = _card.asStateFlow()

    private var _collapseEquipment = MutableStateFlow<Boolean>(true)
    val collapseEquipment = _collapseEquipment.asStateFlow()

    private var _collapseAccessory = MutableStateFlow<Boolean>(true)
    val collapseAccessory = _collapseAccessory.asStateFlow()

    private var _collapseGem = MutableStateFlow<Boolean>(true)
    val collapseGem = _collapseGem.asStateFlow()

    private var _searchFailCount = MutableStateFlow<Int>(0)
    val searchFailCount = _searchFailCount.asStateFlow()

    init {
        search("흑당곡물라떼")    // 캐릭터 검색
    }

    fun search(characterName: String) {
        initSearchFailCount()    // 검색 실패횟수 초기화

        getProfile(characterName)   // 프로필
        getEngraving(characterName) // 각인
        getEquipment(characterName) // 장비
        getGem(characterName)   // 보석
        getCard(characterName)  // 카드

        changeEquipmentDetail(true) // 장비세부창 접기
        changeAccessoryDetail(true) // 악세세부창 접기
        changeGemDetail(true)   // 보석세부창 접기
    }

    fun initSearchFailCount() = viewModelScope.launch {
        _searchFailCount.emit(0)
    }

    fun addSearchFailCount() = viewModelScope.launch {
        _searchFailCount.emit(searchFailCount.value + 1)
    }

    fun getProfile(characterName: String) = viewModelScope.launch {
        armoryRepository.getProfile(characterName)
            .onStart { }
            .onCompletion { }
            .catch { }
            .collectLatest { apiResult ->
                println("xxx apiResult : $apiResult")
                if (!apiResult.success) addSearchFailCount()
                _profile.emit(apiResult)
            }
    }

    fun getEngraving(characterName: String) = viewModelScope.launch {
        armoryRepository.getEngraving(characterName)
            .onStart { }
            .onCompletion { }
            .catch { }
            .collectLatest { apiResult ->
                if (!apiResult.success) addSearchFailCount()
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

    fun getGem(characterName: String) = viewModelScope.launch {
        armoryRepository.getGem(characterName)
            .onStart { }
            .onCompletion { }
            .catch { }
            .collectLatest { apiResult ->
                _gem.emit(apiResult)
            }
    }

    fun getCard(characterName: String) = viewModelScope.launch {
        armoryRepository.getCard(characterName)
            .onStart {  }
            .onCompletion {  }
            .catch {  }
            .collectLatest { apiResult ->
                _card.emit(apiResult)
            }
    }

    fun changeEquipmentDetail(collapse: Boolean? = null) = viewModelScope.launch {
        _collapseEquipment.emit(collapse ?: !collapseEquipment.value)
    }

    fun changeAccessoryDetail(collapse: Boolean? = null) = viewModelScope.launch {
        _collapseAccessory.emit(collapse ?: !collapseAccessory.value)
    }

    fun changeGemDetail(collapse: Boolean? = null) = viewModelScope.launch {
        _collapseGem.emit(collapse ?: !collapseGem.value)
    }
}