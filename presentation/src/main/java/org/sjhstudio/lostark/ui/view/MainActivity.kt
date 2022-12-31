package org.sjhstudio.lostark.ui.view

import android.os.Bundle
import android.text.Html
import android.view.inputmethod.EditorInfo
import android.widget.Toast
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
import org.sjhstudio.lostark.util.setEquipmentImage
import org.sjhstudio.lostark.util.setEquipmentQuality
import org.sjhstudio.lostark.util.setEquipmentSet
import org.sjhstudio.lostark.util.setEquipmentSummary

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
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mainViewModel.getProfile(inputNickname)
                }
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
                            binding.layoutProfile.container.isVisible = true
                            updateStatView(result.data?.stats)
                            println("xxx 프로필 불러오기 성공!!")
                        } else {
                            println("xxx 프로필 불러오기 실패..")
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                engraving.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            engravingAdapter.submitList(result.data?.effects)
                            println("xxx 각인 불러오기 성공!!")
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
                            binding.layoutEquipment.container.isVisible = true
                            updateEquipmentView(result.data)
                            println("xxx equipment list : ${result.data}")
                            println("xxx 장비 불러오기 성공!!")
                        } else {
                            println("xxx 장비 불러오기 실패..")
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                equipmentDetail.collectLatest { isDetail ->
                    binding.layoutEquipment.layoutEquipmentDetail.isVisible = isDetail
                    binding.layoutEquipment.layoutEquipmentSummary.isVisible = !isDetail
                }
            }
        }
    }

    private fun updateStatView(stats: List<Profile.Stat>?) {
        with(binding.layoutProfile) {
            stats?.forEach { stat ->
                when (stat.type) {
                    getString(R.string.label_attack_point) -> { // 공격력
                        tvAttackPoint.text = stat.value
                    }
                    getString(R.string.label_health_point) -> { // 최대 생명력
                        tvHealthPoint.text = stat.value
                    }
                    getString(R.string.label_critical) -> { // 치명
                        tvCritical.text = stat.value
                    }
                    getString(R.string.label_specialization) -> {   // 특화
                        tvSpecialization.text = stat.value
                    }
                    getString(R.string.label_domination) -> {   // 제압
                        tvDomination.text = stat.value
                    }
                    getString(R.string.label_swiftness) -> {    // 신속
                        tvSwiftness.text = stat.value
                    }
                    getString(R.string.label_endurance) -> {    // 인내
                        tvEndurance.text = stat.value
                    }
                    getString(R.string.label_expertise) -> {    // 숙련
                        tvExpertise.text = stat.value
                    }
                }
            }
        }
    }

    private fun updateEquipmentView(equipments: List<Equipment>?) {
        with(binding.layoutEquipment) {
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
                    }
                    "투구" -> {
                        ivHead.setEquipmentImage(equipment)
                        ivHead2.setEquipmentImage(equipment)
                        tvHeadQuality.setEquipmentQuality(equipment)
                        tvHeadQuality2.setEquipmentQuality(equipment)
                        tvHeadSummary.setEquipmentSummary(equipment)
                        tvHeadSet.setEquipmentSet(equipment)
                        tvHeadName.text = equipment.name
                    }
                    "상의" -> {
                        ivTop.setEquipmentImage(equipment)
                        ivTop2.setEquipmentImage(equipment)
                        tvTopQuality.setEquipmentQuality(equipment)
                        tvTopQuality2.setEquipmentQuality(equipment)
                        tvTopSummary.setEquipmentSummary(equipment)
                        tvTopSet.setEquipmentSet(equipment)
                        tvTopName.text = equipment.name
                    }
                    "하의" -> {
                        ivBottom.setEquipmentImage(equipment)
                        ivBottom2.setEquipmentImage(equipment)
                        tvBottomQuality.setEquipmentQuality(equipment)
                        tvBottomQuality2.setEquipmentQuality(equipment)
                        tvBottomSummary.setEquipmentSummary(equipment)
                        tvBottomSet.setEquipmentSet(equipment)
                        tvBottomName.text = equipment.name
                    }
                    "장갑" -> {
                        ivGlove.setEquipmentImage(equipment)
                        ivGlove2.setEquipmentImage(equipment)
                        tvGloveQuality.setEquipmentQuality(equipment)
                        tvGloveQuality2.setEquipmentQuality(equipment)
                        tvGloveSummary.setEquipmentSummary(equipment)
                        tvGloveSet.setEquipmentSet(equipment)
                        tvGloveName.text = equipment.name
                    }
                    "어깨" -> {
                        ivShoulder.setEquipmentImage(equipment)
                        ivShoulder2.setEquipmentImage(equipment)
                        tvShoulderQuality.setEquipmentQuality(equipment)
                        tvShoulderQuality2.setEquipmentQuality(equipment)
                        tvShoulderSummary.setEquipmentSummary(equipment)
                        tvShoulderSet.setEquipmentSet(equipment)
                        tvShoulderName.text = equipment.name
                    }
                }
            }
        }
    }
}