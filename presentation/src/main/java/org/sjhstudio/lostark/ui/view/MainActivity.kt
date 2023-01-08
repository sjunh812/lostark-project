package org.sjhstudio.lostark.ui.view

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.sjhstudio.lostark.R
import org.sjhstudio.lostark.base.BaseActivity
import org.sjhstudio.lostark.databinding.ActivityMainBinding
import org.sjhstudio.lostark.domain.model.response.Equipment
import org.sjhstudio.lostark.domain.model.response.Profile
import org.sjhstudio.lostark.ui.adatper.EngravingAdapter
import org.sjhstudio.lostark.ui.viewmodel.MainViewModel
import org.sjhstudio.lostark.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModels()
    private val engravingAdapter: EngravingAdapter by lazy { EngravingAdapter() }

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

            etNickname.setOnEditorActionListener { _, actionId, _ ->
                val inputNickname = etNickname.text.toString()
                if (actionId == EditorInfo.IME_ACTION_DONE) mainViewModel.search(inputNickname)
                false
            }
            layoutEquipment.layoutEquipmentSummary.setOnClickListener { mainViewModel.changeEquipmentDetail() }
            layoutEquipment.layoutEquipmentDetail.setOnClickListener { mainViewModel.changeEquipmentDetail() }
        }
    }

    private fun observeData() {
        with(mainViewModel) {
            lifecycleScope.launchWhenStarted {
                profile.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            println("xxx 프로필 불러오기 성공!!")
                            binding.layoutProfile.container.isVisible = true
                            updateStatView(result.data?.stats)
                        } else {
                            println("xxx 프로필 불러오기 실패..")
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
                            updateEquipmentView(result.data)
                        } else {
                            println("xxx 장비 불러오기 실패..")
                            binding.layoutEquipment.container.isVisible = false
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                collapseEquipment.collectLatest { collapse ->
                    println("xxx $collapse")
                    binding.layoutEquipment.layoutEquipmentDetail.isVisible = !collapse
                    binding.layoutEquipment.layoutEquipmentSummary.isVisible = collapse
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
        with(binding.layoutEquipment) {
            val equipmentSetList = arrayListOf<EquipmentSet>()

            equipments?.forEach { equipment ->
                when (equipment.type) {
                    "무기" -> {
                        ivWeapon.setEquipmentImage(equipment)
                        ivWeapon2.setEquipmentImage(equipment)
                        tvWeaponQuality.setEquipmentQuality(equipment)
                        tvWeaponQuality2.setEquipmentQuality(equipment)
                        tvWeaponSummary.setEquipmentSummary(equipment)
                        tvWeaponSet.setEquipmentSet(equipment)
                        tvWeaponName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "투구" -> {
                        ivHead.setEquipmentImage(equipment)
                        ivHead2.setEquipmentImage(equipment)
                        tvHeadQuality.setEquipmentQuality(equipment)
                        tvHeadQuality2.setEquipmentQuality(equipment)
                        tvHeadSummary.setEquipmentSummary(equipment)
                        tvHeadSet.setEquipmentSet(equipment)
                        tvHeadName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "상의" -> {
                        ivTop.setEquipmentImage(equipment)
                        ivTop2.setEquipmentImage(equipment)
                        tvTopQuality.setEquipmentQuality(equipment)
                        tvTopQuality2.setEquipmentQuality(equipment)
                        tvTopSummary.setEquipmentSummary(equipment)
                        tvTopSet.setEquipmentSet(equipment)
                        tvTopName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "하의" -> {
                        ivBottom.setEquipmentImage(equipment)
                        ivBottom2.setEquipmentImage(equipment)
                        tvBottomQuality.setEquipmentQuality(equipment)
                        tvBottomQuality2.setEquipmentQuality(equipment)
                        tvBottomSummary.setEquipmentSummary(equipment)
                        tvBottomSet.setEquipmentSet(equipment)
                        tvBottomName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "장갑" -> {
                        ivGlove.setEquipmentImage(equipment)
                        ivGlove2.setEquipmentImage(equipment)
                        tvGloveQuality.setEquipmentQuality(equipment)
                        tvGloveQuality2.setEquipmentQuality(equipment)
                        tvGloveSummary.setEquipmentSummary(equipment)
                        tvGloveSet.setEquipmentSet(equipment)
                        tvGloveName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "어깨" -> {
                        ivShoulder.setEquipmentImage(equipment)
                        ivShoulder2.setEquipmentImage(equipment)
                        tvShoulderQuality.setEquipmentQuality(equipment)
                        tvShoulderQuality2.setEquipmentQuality(equipment)
                        tvShoulderSummary.setEquipmentSummary(equipment)
                        tvShoulderSet.setEquipmentSet(equipment)
                        tvShoulderName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "목걸이" -> {
                        ivNecklace.setEquipmentImage(equipment)
                        tvNecklaceQuality.setEquipmentQuality(equipment)
                    }
                    "귀걸이" -> {
                        if (tvEarring1Quality.text == "-") {
                            ivEarring1.setEquipmentImage(equipment)
                            tvEarring1Quality.setEquipmentQuality(equipment)
                        } else {
                            ivEarring2.setEquipmentImage(equipment)
                            tvEarring2Quality.setEquipmentQuality(equipment)
                        }
                    }
                    "반지" -> {
                        if (tvRing1Quality.text == "-") {
                            ivRing1.setEquipmentImage(equipment)
                            tvRing1Quality.setEquipmentQuality(equipment)
                        } else {
                            ivRing2.setEquipmentImage(equipment)
                            tvRing2Quality.setEquipmentQuality(equipment)
                        }
                    }
                    "팔찌" -> {
                        ivBracelet.setEquipmentImage(equipment)
                    }
                    "어빌리티 스톤" -> {
                        ivStone.setEquipmentImage(equipment)
                        tvStoneQuality.setEquipmentQuality(equipment)
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
}