package org.sjhstudio.lostark.ui.view

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.sjhstudio.lostark.R
import org.sjhstudio.lostark.base.BaseActivity
import org.sjhstudio.lostark.databinding.ActivityMainBinding
import org.sjhstudio.lostark.domain.model.response.Equipment
import org.sjhstudio.lostark.domain.model.response.Profile
import org.sjhstudio.lostark.ui.adatper.BraceletEffectAdapter
import org.sjhstudio.lostark.ui.adatper.EngravingAdapter
import org.sjhstudio.lostark.ui.viewmodel.MainViewModel
import org.sjhstudio.lostark.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModels()
    private val engravingAdapter: EngravingAdapter by lazy { EngravingAdapter() }
    private val braceletEffectAdapter: BraceletEffectAdapter by lazy { BraceletEffectAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind()
        initView()
        observeData()
    }

    private fun bind() {
        with(binding) {
            viewModel = mainViewModel
        }
    }

    private fun initView() {
        with(binding) {
            layoutProfile.rvEngraving.adapter = engravingAdapter
            layoutEquipment.rvBraceletSpecialEffect.adapter = braceletEffectAdapter

            etNickname.setOnEditorActionListener { _, actionId, _ ->
                val inputNickname = etNickname.text.toString()
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    initEngravingView()
                    mainViewModel.search(inputNickname)
                }
                false
            }
            layoutEquipment.layoutEquipment.setOnClickListener { mainViewModel.changeEquipmentDetail() }
            layoutEquipment.layoutAccessory.setOnClickListener { mainViewModel.changeAccessoryDetail() }
        }
    }

    private fun observeData() {
        with(mainViewModel) {
            lifecycleScope.launchWhenStarted {
                profile.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            println("xxx 프로필 불러오기 성공!!")
                            updateStatView(result.data?.stats)
                            binding.layoutProfile.executePendingBindings()
                            binding.layoutProfile.container.isVisible = true
                        } else {
                            println("xxx 프로필 불러오기 실패..")
                            addSearchFailCount()
                            binding.layoutProfile.container.isVisible = false
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                engraving.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            println("xxx 각인 불러오기 성공!!")
                            binding.layoutProfile.executePendingBindings()
                            engravingAdapter.submitList(result.data?.effects)
                        } else {
                            println("xxx 각인 불러오기 실패..")
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                equipment.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            println("xxx 장비 불러오기 성공!!")
                            println("xxx equipment list : ${result.data}")
                            binding.layoutEquipment.container.isVisible = true
//                            updateEquipmentView(result.data)
                        } else {
                            println("xxx 장비 불러오기 실패..")
                            addSearchFailCount()
                            binding.layoutEquipment.container.isVisible = false
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                collapseEquipment.collectLatest { collapse ->
                    binding.layoutEquipment.layoutEquipmentSummary.isVisible = collapse
                    binding.layoutEquipment.layoutEquipmentDetail.isVisible = !collapse
                }
            }

            lifecycleScope.launchWhenStarted {
                collapseAccessory.collectLatest { collapse ->
                    binding.layoutEquipment.layoutAccessorySummary.isVisible = collapse
                    binding.layoutEquipment.layoutAccessoryDetail.isVisible = !collapse
                }
            }

            lifecycleScope.launchWhenStarted {
                searchFailCount.collectLatest { count ->
                    if (count >= 2) Snackbar.make(binding.root, "검색 결과가 없습니다..\uD83E\uDEE0", 1500).show()
                }
            }
        }
    }

    // 전투 특성 갱신
    private fun updateStatView(stats: List<Profile.Stat>?) {
        with(binding.layoutProfile) {
            stats?.forEach { stat ->
                when (stat.type) {
                    getString(R.string.label_attack_point) -> tvAttackPoint.text = stat.value
                    getString(R.string.label_health_point) -> tvHealthPoint.text = stat.value
                    getString(R.string.label_critical) -> tvCritical.text = stat.value
                    getString(R.string.label_specialization) -> tvSpecialization.text = stat.value
                    getString(R.string.label_domination) -> tvDomination.text = stat.value
                    getString(R.string.label_swiftness) -> tvSwiftness.text = stat.value
                    getString(R.string.label_endurance) -> tvEndurance.text = stat.value
                    getString(R.string.label_expertise) -> tvExpertise.text = stat.value
                }
            }
        }
    }

    // 장비 갱신
    private fun updateEquipmentView(equipments: List<Equipment>?) {
        initEquipmentView()
        with(binding.layoutEquipment) {
            val equipmentSetList = arrayListOf<EquipmentSet>()
            var firstEarring = true
            var secondRing = true

            equipments?.forEach { equipment ->
                when (equipment.type) {
                    "무기" -> {
                        ivWeapon.setEquipmentImage(equipment)
                        ivWeaponDetail.setEquipmentImage(equipment)
                        tvWeaponQuality.setEquipmentQuality(equipment)
                        tvWeaponDetailQuality.setEquipmentQuality(equipment)
                        tvWeaponSummary.setEquipmentSummary(equipment)
                        tvWeaponSet.setEquipmentSet(equipment)
                        tvWeaponName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "투구" -> {
                        ivHead.setEquipmentImage(equipment)
                        ivHeadDetail.setEquipmentImage(equipment)
                        tvHeadQuality.setEquipmentQuality(equipment)
                        tvHeadDetailQuality.setEquipmentQuality(equipment)
                        tvHeadSummary.setEquipmentSummary(equipment)
                        tvHeadSet.setEquipmentSet(equipment)
                        tvHeadName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "상의" -> {
                        ivTop.setEquipmentImage(equipment)
                        ivTopDetail.setEquipmentImage(equipment)
                        tvTopQuality.setEquipmentQuality(equipment)
                        tvTopDetailQuality.setEquipmentQuality(equipment)
                        tvTopSummary.setEquipmentSummary(equipment)
                        tvTopSet.setEquipmentSet(equipment)
                        tvTopName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "하의" -> {
                        ivBottom.setEquipmentImage(equipment)
                        ivBottomDetail.setEquipmentImage(equipment)
                        tvBottomQuality.setEquipmentQuality(equipment)
                        tvBottomDetailQuality.setEquipmentQuality(equipment)
                        tvBottomSummary.setEquipmentSummary(equipment)
                        tvBottomSet.setEquipmentSet(equipment)
                        tvBottomName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "장갑" -> {
                        ivGlove.setEquipmentImage(equipment)
                        ivGloveDetail.setEquipmentImage(equipment)
                        tvGloveQuality.setEquipmentQuality(equipment)
                        tvGloveDetailQuality.setEquipmentQuality(equipment)
                        tvGloveSummary.setEquipmentSummary(equipment)
                        tvGloveSet.setEquipmentSet(equipment)
                        tvGloveName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "어깨" -> {
                        ivShoulder.setEquipmentImage(equipment)
                        ivShoulderDetail.setEquipmentImage(equipment)
                        tvShoulderQuality.setEquipmentQuality(equipment)
                        tvShoulderDetailQuality.setEquipmentQuality(equipment)
                        tvShoulderSummary.setEquipmentSummary(equipment)
                        tvShoulderSet.setEquipmentSet(equipment)
                        tvShoulderName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "목걸이" -> {
                        ivNecklace.setEquipmentImage(equipment)
                        ivNecklaceDetail.setEquipmentImage(equipment)
                        tvNecklaceQuality.setEquipmentQuality(equipment)
                        tvNecklaceDetailQuality.setEquipmentQuality(equipment)
                        tvNecklaceEffect.setAccessoryEffectList(equipment)
                        chipGroupNecklace.setAccessoryEngravingList(equipment)
                        tvNecklaceName.text = equipment.name
                    }
                    "귀걸이" -> {
                        if (firstEarring) {
                            firstEarring = false
                            ivEarring1.setEquipmentImage(equipment)
                            ivEarring1Detail.setEquipmentImage(equipment)
                            tvEarring1Quality.setEquipmentQuality(equipment)
                            tvEarring1DetailQuality.setEquipmentQuality(equipment)
                            tvEarring1Effect.setAccessoryEffectList(equipment)
                            chipGroupEarring1.setAccessoryEngravingList(equipment)
                            tvEarring1Name.text = equipment.name
                        } else {
                            ivEarring2.setEquipmentImage(equipment)
                            ivEarring2Detail.setEquipmentImage(equipment)
                            tvEarring2Quality.setEquipmentQuality(equipment)
                            tvEarring2DetailQuality.setEquipmentQuality(equipment)
                            tvEarring2Effect.setAccessoryEffectList(equipment)
                            chipGroupEarring2.setAccessoryEngravingList(equipment)
                            tvEarring2Name.text = equipment.name
                        }
                    }
                    "반지" -> {
                        if (secondRing) {
                            secondRing = false
                            ivRing1.setEquipmentImage(equipment)
                            ivRing1Detail.setEquipmentImage(equipment)
                            tvRing1Quality.setEquipmentQuality(equipment)
                            tvRing1DetailQuality.setEquipmentQuality(equipment)
                            tvRing1Effect.setAccessoryEffectList(equipment)
                            chipGroupRing1.setAccessoryEngravingList(equipment)
                            tvRing1Name.text = equipment.name
                        } else {
                            ivRing2.setEquipmentImage(equipment)
                            ivRing2Detail.setEquipmentImage(equipment)
                            tvRing2Quality.setEquipmentQuality(equipment)
                            tvRing2DetailQuality.setEquipmentQuality(equipment)
                            tvRing2Effect.setAccessoryEffectList(equipment)
                            chipGroupRing2.setAccessoryEngravingList(equipment)
                            tvRing2Name.text = equipment.name
                        }
                    }
                    "팔찌" -> {
                        ivBracelet.setEquipmentImage(equipment)
                        tvBraceletQuality.setEquipmentQuality(equipment)
                        ivBraceletDetail.setEquipmentImage(equipment)
                        tvBraceletDetailQuality.setEquipmentQuality(equipment)
                        tvBraceletEffect.setAccessoryEffectList(equipment)
                        braceletEffectAdapter.submitList(getBraceletSpecialEffects(equipment))
                        tvBraceletName.text = equipment.name
                    }
                    "어빌리티 스톤" -> {
                        ivStone.setEquipmentImage(equipment)
                        ivStoneDetail.setEquipmentImage(equipment)
                        tvStoneQuality.setEquipmentQuality(equipment)
                        tvStoneDetailQuality.setEquipmentQuality(equipment)
                        tvStoneEffect.setAccessoryEffectList(equipment)
                        chipGroupStone.setAccessoryEngravingList(equipment)
                        tvStoneName.text = equipment.name
                    }
                }
            }

            tvEquipmentSetSummary.apply {
                val equipmentSetSummary = getEquipmentSetSummary(equipmentSetList)
                isVisible = equipmentSetSummary.isNotEmpty()
                text = equipmentSetSummary
            }
        }
    }

    private fun initEngravingView() {
        with(binding.layoutProfile) {
            engravingAdapter.submitList(null)
            ivEngravingSlot1.setImageResource(0)
            ivEngravingSlot2.setImageResource(0)
        }
    }

    private fun initEquipmentView() {
        with(binding.layoutEquipment) {
            initEquipmentImage(
                listOf(
                    ivWeapon, ivWeaponDetail, ivHead, ivHeadDetail, ivShoulder, ivShoulderDetail,
                    ivTop, ivTopDetail, ivBottom, ivBottomDetail, ivGlove, ivGloveDetail,
                    ivNecklace, ivNecklaceDetail, ivEarring1, ivEarring1Detail, ivEarring2, ivEarring2Detail,
                    ivRing1, ivRing1Detail, ivRing2, ivRing2Detail, ivBracelet, ivBraceletDetail, ivStone, ivStoneDetail
                )
            )
            initEquipmentQuality(
                listOf(
                    tvWeaponQuality, tvWeaponDetailQuality, tvHeadQuality, tvHeadDetailQuality, tvShoulderQuality, tvShoulderDetailQuality,
                    tvTopQuality, tvTopDetailQuality, tvBottomQuality, tvBottomDetailQuality, tvGloveQuality, tvGloveDetailQuality,
                    tvNecklaceQuality, tvNecklaceDetailQuality, tvEarring1Quality, tvEarring1DetailQuality, tvEarring2Quality, tvEarring2DetailQuality,
                    tvRing1Quality, tvRing1DetailQuality, tvRing2Quality, tvRing2DetailQuality, tvBraceletQuality, tvBraceletDetailQuality, tvStoneQuality, tvStoneDetailQuality
                )
            )
            initEquipmentTextView(
                listOf(
                    tvWeaponName, tvHeadName, tvShoulderName, tvTopName, tvBottomName, tvGloveName,
                    tvNecklaceName, tvEarring1Name, tvEarring2Name, tvRing1Name, tvRing2Name, tvBraceletName, tvStoneName,
                    tvNecklaceEffect, tvEarring1Effect, tvEarring2Effect, tvRing1Effect, tvRing2Effect, tvBraceletEffect, tvBraceletQuality
                )
            )
        }
    }
}