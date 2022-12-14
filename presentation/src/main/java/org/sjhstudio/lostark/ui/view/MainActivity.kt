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
                            println("xxx ????????? ???????????? ??????!!")
                            binding.layoutProfile.container.isVisible = true
                            updateStatView(result.data?.stats)
                        } else {
                            println("xxx ????????? ???????????? ??????..")
                            binding.layoutProfile.container.isVisible = false
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                engraving.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            println("xxx ?????? ???????????? ??????!!")
                            engravingAdapter.submitList(result.data?.effects)
                        } else {
                            println("xxx ?????? ???????????? ??????..")
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                equipment.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            println("xxx ?????? ???????????? ??????!!")
                            println("xxx equipment list : ${result.data}")
                            binding.layoutEquipment.container.isVisible = true
                            updateEquipmentView(result.data)
                        } else {
                            println("xxx ?????? ???????????? ??????..")
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

    // ?????? ?????? ??????
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

    // ?????? ??????
    private fun updateEquipmentView(equipments: List<Equipment>?) {
        with(binding.layoutEquipment) {
            val equipmentSetList = arrayListOf<EquipmentSet>()

            equipments?.forEach { equipment ->
                when (equipment.type) {
                    "??????" -> {
                        ivWeapon.setEquipmentImage(equipment)
                        ivWeapon2.setEquipmentImage(equipment)
                        tvWeaponQuality.setEquipmentQuality(equipment)
                        tvWeaponQuality2.setEquipmentQuality(equipment)
                        tvWeaponSummary.setEquipmentSummary(equipment)
                        tvWeaponSet.setEquipmentSet(equipment)
                        tvWeaponName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "??????" -> {
                        ivHead.setEquipmentImage(equipment)
                        ivHead2.setEquipmentImage(equipment)
                        tvHeadQuality.setEquipmentQuality(equipment)
                        tvHeadQuality2.setEquipmentQuality(equipment)
                        tvHeadSummary.setEquipmentSummary(equipment)
                        tvHeadSet.setEquipmentSet(equipment)
                        tvHeadName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "??????" -> {
                        ivTop.setEquipmentImage(equipment)
                        ivTop2.setEquipmentImage(equipment)
                        tvTopQuality.setEquipmentQuality(equipment)
                        tvTopQuality2.setEquipmentQuality(equipment)
                        tvTopSummary.setEquipmentSummary(equipment)
                        tvTopSet.setEquipmentSet(equipment)
                        tvTopName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "??????" -> {
                        ivBottom.setEquipmentImage(equipment)
                        ivBottom2.setEquipmentImage(equipment)
                        tvBottomQuality.setEquipmentQuality(equipment)
                        tvBottomQuality2.setEquipmentQuality(equipment)
                        tvBottomSummary.setEquipmentSummary(equipment)
                        tvBottomSet.setEquipmentSet(equipment)
                        tvBottomName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "??????" -> {
                        ivGlove.setEquipmentImage(equipment)
                        ivGlove2.setEquipmentImage(equipment)
                        tvGloveQuality.setEquipmentQuality(equipment)
                        tvGloveQuality2.setEquipmentQuality(equipment)
                        tvGloveSummary.setEquipmentSummary(equipment)
                        tvGloveSet.setEquipmentSet(equipment)
                        tvGloveName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "??????" -> {
                        ivShoulder.setEquipmentImage(equipment)
                        ivShoulder2.setEquipmentImage(equipment)
                        tvShoulderQuality.setEquipmentQuality(equipment)
                        tvShoulderQuality2.setEquipmentQuality(equipment)
                        tvShoulderSummary.setEquipmentSummary(equipment)
                        tvShoulderSet.setEquipmentSet(equipment)
                        tvShoulderName.text = equipment.name
                        setEquipmentSetList(equipmentSetList, equipment)
                    }
                    "?????????" -> {
                        ivNecklace.setEquipmentImage(equipment)
                        tvNecklaceQuality.setEquipmentQuality(equipment)
                    }
                    "?????????" -> {
                        if (tvEarring1Quality.text == "-") {
                            ivEarring1.setEquipmentImage(equipment)
                            tvEarring1Quality.setEquipmentQuality(equipment)
                        } else {
                            ivEarring2.setEquipmentImage(equipment)
                            tvEarring2Quality.setEquipmentQuality(equipment)
                        }
                    }
                    "??????" -> {
                        if (tvRing1Quality.text == "-") {
                            ivRing1.setEquipmentImage(equipment)
                            tvRing1Quality.setEquipmentQuality(equipment)
                        } else {
                            ivRing2.setEquipmentImage(equipment)
                            tvRing2Quality.setEquipmentQuality(equipment)
                        }
                    }
                    "??????" -> {
                        ivBracelet.setEquipmentImage(equipment)
                    }
                    "???????????? ??????" -> {
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