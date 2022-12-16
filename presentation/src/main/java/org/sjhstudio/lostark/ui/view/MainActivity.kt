package org.sjhstudio.lostark.ui.view

import android.os.Bundle
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
import org.sjhstudio.lostark.domain.model.response.Profile
import org.sjhstudio.lostark.ui.adatper.EngravingAdapter
import org.sjhstudio.lostark.ui.viewmodel.MainViewModel

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
            etNickname.setOnEditorActionListener { _, actionId, _ ->
                val inputNickname = etNickname.text.toString()
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mainViewModel.getProfile(inputNickname)
                }

                false
            }
            layoutProfile.rvEngraving.adapter = engravingAdapter
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
                            println("xxx equipment list : ${result.data}")
                            println("xxx 장비 불러오기 성공!!")
                        } else {
                            println("xxx 장비 불러오기 실패..")
                        }
                    }
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
}