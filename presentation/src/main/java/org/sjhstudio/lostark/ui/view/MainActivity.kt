package org.sjhstudio.lostark.ui.view

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.sjhstudio.lostark.R
import org.sjhstudio.lostark.base.BaseActivity
import org.sjhstudio.lostark.databinding.ActivityMainBinding
import org.sjhstudio.lostark.domain.model.response.Profile
import org.sjhstudio.lostark.ui.adatper.*
import org.sjhstudio.lostark.ui.common.PrgDialog
import org.sjhstudio.lostark.ui.viewmodel.MainViewModel
import org.sjhstudio.lostark.util.setEquipmentSetSummary
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject
    lateinit var imm: InputMethodManager

    private val mainViewModel: MainViewModel by viewModels()
    private val prgDialog: PrgDialog by lazy { PrgDialog.newInstance() }
    private val engravingAdapter: EngravingAdapter by lazy { EngravingAdapter() }
    private val braceletEffectAdapter: BraceletEffectAdapter by lazy { BraceletEffectAdapter() }
    private val gemSummaryAdapter: GemSummaryAdapter by lazy { GemSummaryAdapter() }
    private val gemDetailAdapter: GemDetailAdapter by lazy { GemDetailAdapter() }
    private val cardAdapter: CardAdapter by lazy { CardAdapter(mainViewModel) }
    private val cardEffectSummaryAdapter: CardEffectSummaryAdapter by lazy { CardEffectSummaryAdapter() }
    private val cardEffectDetailAdapter: CardEffectDetailAdapter by lazy { CardEffectDetailAdapter() }

    companion object {
        private const val LOG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind()
        initViews()
        observeData()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val view = currentFocus

        view?.let { v ->
            if (v is TextInputEditText && ev?.action == MotionEvent.ACTION_UP) {
                val x = ev.rawX
                val y = ev.rawY
                val outLocation = IntArray(2)

                v.getLocationOnScreen(outLocation)

                if (x < outLocation[0] || x > outLocation[0] + view.width || y < outLocation[1] || y > outLocation[1] + view.height) {
                    v.clearFocus()
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun bind() {
        with(binding) {
            viewModel = mainViewModel
        }
    }

    private fun initViews() {
        with(binding) {
            prgDialog.show(supportFragmentManager, "prg_dialog")

            layoutProfile.rvEngraving.adapter = engravingAdapter
            layoutEquipment.rvBraceletSpecialEffect.adapter = braceletEffectAdapter
            layoutGem.rvGemSummary.adapter = gemSummaryAdapter
            layoutGem.rvGemDetail.adapter = gemDetailAdapter
            layoutCard.rvCard.apply {
                adapter = cardAdapter
                itemAnimator?.changeDuration = 0
            }
            layoutCard.rvCardEffectSummary.adapter = cardEffectSummaryAdapter
            layoutCard.rvCardEffectDetail.adapter = cardEffectDetailAdapter

            etNickname.setOnEditorActionListener { _, actionId, _ ->
                val inputNickname = etNickname.text.toString()

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    etNickname.text?.clear()
                    initAdapters()
                    mainViewModel.search(inputNickname)
                }

                false
            }
            layoutEquipment.layoutEquipmentTop.setOnClickListener { mainViewModel.changeEquipmentDetail() }
            layoutEquipment.layoutAccessoryTop.setOnClickListener { mainViewModel.changeAccessoryDetail() }
            layoutGem.layoutGemTop.setOnClickListener { mainViewModel.changeGemDetail() }
            layoutCard.layoutCardTop.setOnClickListener { mainViewModel.changeCardDetail() }
        }
    }

    private fun observeData() {
        with(mainViewModel) {
            lifecycleScope.launchWhenStarted {
                profile.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            Log.d(LOG, "프로필 불러오기 성공")
                            prgDialog.dismiss()
                            updateStatViews(result.data?.stats)
//                            binding.layoutProfile.executePendingBindings()
                            binding.layoutProfile.container.isVisible = true
                        } else {
                            Log.d(LOG, "프로필 불러오기 실패")
                            binding.layoutProfile.container.isVisible = false
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                engraving.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            Log.d(LOG, "각인 불러오기 성공")
//                            binding.layoutProfile.executePendingBindings()
                            engravingAdapter.submitList(result.data?.effects)
                        } else {
                            Log.d(LOG, "각인 불러오기 실패")
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                equipment.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            Log.d(LOG, "장비 불러오기 성공")
                            result.data?.let { equipmentMap ->
                                if (equipmentMap.containsKey("팔찌")) {
                                    val effectList = result.data!!["팔찌"]?.effects
                                    braceletEffectAdapter.submitList(effectList?.filter { effect -> effect.isSpecial })
                                }
                                binding.layoutEquipment.tvEquipmentSetSummary.setEquipmentSetSummary(
                                    equipmentMap
                                )
                                binding.layoutEquipment.container.isVisible = true
                            }
                        } else {
                            Log.d(LOG, "장비  불러오기 실패")
                            binding.layoutEquipment.container.isVisible = false
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                gem.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            Log.d(LOG, "보석 불러오기 성공")
                            gemSummaryAdapter.submitList(result.data?.gems)
                            gemDetailAdapter.submitList(result.data?.gems)
                            binding.layoutGem.layoutGem.isVisible = true
                        } else {
                            Log.d(LOG, "보석 불러오기 실패")
                            binding.layoutGem.layoutGem.isVisible = false
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                card.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            Log.d(LOG, "카드 불러오기 성공")
                            Log.d(LOG, "card: ${result.data?.effects}")
                            cardAdapter.submitList(result.data?.cards)

                            result.data?.effects?.filter { effect ->
                                effect.items.isNotEmpty()
                            }.run {
                                cardEffectSummaryAdapter.submitList(this)
                                cardEffectDetailAdapter.submitList(this)
                            }
                            binding.layoutCard.layoutCard.isVisible = true
                        } else {
                            Log.d(LOG, "카드 불러오기 실패")
                            binding.layoutCard.layoutCard.isVisible = false
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
                collapseGem.collectLatest { collapse ->
                    binding.layoutGem.layoutGemSummary.isVisible = collapse
                    binding.layoutGem.layoutGemDetail.isVisible = !collapse
                }
            }

            lifecycleScope.launchWhenStarted {
                collapseCard.collectLatest { collapse ->
                    cardAdapter.notifyItemRangeChanged(0, cardAdapter.currentList.size)
                    binding.layoutCard.rvCardEffectSummary.isVisible = collapse
                    binding.layoutCard.rvCardEffectDetail.isVisible = !collapse
                }
            }

            lifecycleScope.launchWhenStarted {
                searchFailCount.collectLatest { count ->
                    if (count >= 2) {
                        prgDialog.dismiss()
                        Snackbar.make(binding.root, "검색 결과가 없습니다..\uD83E\uDEE0", 1500).show()
                    }
                }
            }
        }
    }

    // 전투 특성 갱신
    private fun updateStatViews(stats: List<Profile.Stat>?) {
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

    private fun initAdapters() {
        engravingAdapter.submitList(null)
        braceletEffectAdapter.submitList(null)
        gemSummaryAdapter.submitList(null)
        gemDetailAdapter.submitList(null)
        cardAdapter.submitList(null)
        cardEffectSummaryAdapter.submitList(null)
    }
}