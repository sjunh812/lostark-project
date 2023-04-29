package org.sjhstudio.lostark.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View.OnFocusChangeListener
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
import org.sjhstudio.lostark.ui.search.adapter.SearchHistoryAdapter
import org.sjhstudio.lostark.util.setEquipmentSetSummary
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModels()

    private val prgDialog by lazy { PrgDialog.newInstance() }

    private val searchHistoryAdapter by lazy {
        SearchHistoryAdapter(
            viewType = SearchHistoryAdapter.SUMMARY_VIEW_TYPE,
            onClick = { history -> search(history.name) },
            onDelete = { history -> mainViewModel.deleteSearchHistory(history) }
        )
    }
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
        private const val PRG_DIALOG = "progress_dialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind()
        initViews()
        observeData()
        search(mainViewModel.nickname)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.let { view ->
            if (view is TextInputEditText && ev?.action == MotionEvent.ACTION_UP) {
                val x = ev.rawX
                val y = ev.rawY
                val etOutLocation = IntArray(2) // 검색창 시작지점
                val rvOutLocation = IntArray(2) // 검색기록창 시작지점

                view.getLocationOnScreen(etOutLocation)
                binding.rvSearchHistory.getLocationOnScreen(rvOutLocation)

                if (x < etOutLocation[0] || x > etOutLocation[0] + view.width ||
                    y < etOutLocation[1] || y > etOutLocation[1] + view.height
                ) {
                    if (x < rvOutLocation[0] || x > rvOutLocation[0] + binding.rvSearchHistory.width ||
                        y < rvOutLocation[1] || y > rvOutLocation[1] + binding.rvSearchHistory.height
                    ) {
                        view.clearFocus()
                        imm.hideSoftInputFromWindow(view.windowToken, 0)
                    }
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
            rvSearchHistory.adapter = searchHistoryAdapter
            layoutProfile.rvEngraving.adapter = engravingAdapter
            layoutEquipment.rvBraceletSpecialEffect.adapter = braceletEffectAdapter
            layoutGem.rvGemSummary.adapter = gemSummaryAdapter
            layoutGem.rvGemDetail.adapter = gemDetailAdapter
            layoutCard.rvCardEffectSummary.adapter = cardEffectSummaryAdapter
            layoutCard.rvCardEffectDetail.adapter = cardEffectDetailAdapter
            layoutCard.rvCard.apply {
                adapter = cardAdapter
                itemAnimator?.changeDuration = 0
            }

            etNickname.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    etNickname.text?.also { nickname ->
                        search(nickname.trim().toString())
                    }?.run {
                        clear()
                    }
                }
                false
            }
            etNickname.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                rvSearchHistory.isVisible = hasFocus
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
                errorMessage.collectLatest { errorMessage ->
                    prgDialog.dismissAllowingStateLoss()
                    binding.layoutFail.isVisible = true
                    Snackbar.make(binding.root, errorMessage, 1500).show()
                }
            }

            lifecycleScope.launchWhenStarted {
                profile.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        prgDialog.dismiss()

                        if (result.success) {
                            Log.d(LOG, "profile success")
                            binding.scrollView.scrollTo(0, 0)   // 최상단 스크롤
                            initAdapters()  // 모든 어뎁터 초기화

                            result.data?.let { data ->
                                updateStatViews(data.stats)
                                insertSearchHistory(data)
                                binding.layoutFail.isVisible = false
                            }
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                engraving.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            Log.e(LOG, "engraving success")
                            engravingAdapter.submitList(result.data?.effects)
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
                            result.data?.let { map ->
                                if (map.containsKey("팔찌")) {
                                    val effectList = result.data!!["팔찌"]?.effects
                                    braceletEffectAdapter.submitList(effectList?.filter { effect -> effect.isSpecial })
                                }
                                binding.layoutEquipment.tvEquipmentSetSummary.setEquipmentSetSummary(
                                    map
                                )
                            }
                        } else {
                            Log.e(LOG, "equipment fail")
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                gem.collectLatest { apiResult ->
                    apiResult?.let { result ->
                        if (result.success) {
                            Log.e(LOG, "gem success")
                            gemSummaryAdapter.submitList(result.data?.gems)
                            gemDetailAdapter.submitList(result.data?.gems)
                        } else {
                            Log.e(LOG, "gem fail")
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
                        } else {
                            Log.e(LOG, "card fail")
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                searchHistoryList.collectLatest { list ->
                    Log.e(LOG, "search history: $list")
                    searchHistoryAdapter.submitList(list)
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

    private fun search(nickname: String) {
        binding.etNickname.clearFocus()
        imm.hideSoftInputFromWindow(binding.etNickname.windowToken, 0)

        if (mainViewModel.profile.value?.data?.characterName != nickname &&
            nickname.trim().isNotEmpty()
        ) {
            mainViewModel.search(nickname)
            if (!prgDialog.isVisible) prgDialog.show(supportFragmentManager, PRG_DIALOG)
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