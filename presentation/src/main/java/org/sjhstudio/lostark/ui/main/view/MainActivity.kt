package org.sjhstudio.lostark.ui.main.view

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
import org.sjhstudio.lostark.ui.common.PrgDialog
import org.sjhstudio.lostark.ui.main.adatper.*
import org.sjhstudio.lostark.ui.main.viewmodel.MainViewModel
import org.sjhstudio.lostark.util.setEquipmentSetSummary
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModels()

    private val prgDialog by lazy { PrgDialog.newInstance() }

    private val engravingAdapter by lazy { EngravingAdapter() }
    private val braceletEffectAdapter by lazy { BraceletEffectAdapter() }
    private val gemSummaryAdapter by lazy { GemSummaryAdapter() }
    private val gemDetailAdapter by lazy { GemDetailAdapter() }
    private val cardAdapter by lazy { CardAdapter(mainViewModel) }
    private val cardEffectSummaryAdapter by lazy { CardEffectSummaryAdapter() }
    private val cardEffectDetailAdapter by lazy { CardEffectDetailAdapter() }

    @Inject
    lateinit var imm: InputMethodManager

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
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    initAdapters()
                    mainViewModel.search(etNickname.text.toString())
                    etNickname.text?.clear()
                    imm.hideSoftInputFromWindow(etNickname.windowToken, 0)
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
                        prgDialog.dismiss()

                        if (result.success) {
                            Log.d(LOG, "profile success")
                            initAdapters()
                            result.data?.let { data ->
                                updateStatViews(data.stats)
                                insertSearchHistory(data)
                                binding.layoutFail.isVisible = false
//                                binding.layoutProfile.container.isVisible = true
                            }
                        } else {
                            Log.d(LOG, "profile fail")
                            Snackbar.make(binding.root, "검색 결과가 없습니다..\uD83E\uDEE0", 1500).show()
                            binding.layoutFail.isVisible = true
//                            binding.layoutProfile.container.isVisible = false
                        }

                        binding.layoutProfile.executePendingBindings()
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                engraving.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            Log.e(LOG, "engraving success")
                            engravingAdapter.submitList(result.data?.effects)
                            binding.layoutProfile.executePendingBindings()
                        } else {
                            Log.e(LOG, "engraving fail")
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                equipment.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            Log.e(LOG, "equipment success")
                            result.data?.let { equipmentMap ->
                                if (equipmentMap.containsKey("팔찌")) {
                                    val effectList = result.data!!["팔찌"]?.effects
                                    braceletEffectAdapter.submitList(effectList?.filter { effect -> effect.isSpecial })
                                }
                                binding.layoutEquipment.tvEquipmentSetSummary.setEquipmentSetSummary(
                                    equipmentMap
                                )
//                                binding.layoutEquipment.container.isVisible = true
                            }
                        } else {
                            Log.e(LOG, "equipment fail")
//                            binding.layoutEquipment.container.isVisible = false
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                gem.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            Log.d(LOG, "gem success")
                            gemSummaryAdapter.submitList(result.data?.gems)
                            gemDetailAdapter.submitList(result.data?.gems)
//                            binding.layoutGem.layoutGem.isVisible = true
                        } else {
                            Log.e(LOG, "gem fail")
//                            binding.layoutGem.layoutGem.isVisible = false
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                card.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            Log.e(LOG, "card success")
                            cardAdapter.submitList(result.data?.cards)

                            result.data?.effects?.filter { effect ->
                                effect.items.isNotEmpty()
                            }.run {
                                cardEffectSummaryAdapter.submitList(this)
                                cardEffectDetailAdapter.submitList(this)
                            }
//                            binding.layoutCard.layoutCard.isVisible = true
                        } else {
                            Log.e(LOG, "card fail")
//                            binding.layoutCard.layoutCard.isVisible = false
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                searchHistoryList.collectLatest { list ->
                    Log.e(LOG, "search history: $list")
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