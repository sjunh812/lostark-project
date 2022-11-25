package org.sjhstudio.lostark.ui.view

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.sjhstudio.lostark.R
import org.sjhstudio.lostark.base.BaseActivity
import org.sjhstudio.lostark.base.successOrNull
import org.sjhstudio.lostark.databinding.ActivityMainBinding
import org.sjhstudio.lostark.ui.JewlAdapter
import org.sjhstudio.lostark.ui.common.PrgDialog
import org.sjhstudio.lostark.ui.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()
    private val jewlAdapter: JewlAdapter by lazy { JewlAdapter() }
    private val prgDialog: PrgDialog by lazy { PrgDialog.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        with(binding) {
            etNickname.setOnEditorActionListener { _, actionId, _ ->
                val inputNickname = etNickname.text.toString()
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (!prgDialog.isAdded) {
                        prgDialog.show(supportFragmentManager, "PrgDialog")
                    }
                    viewModel.getUserInfo(inputNickname)
                }
                false
            }
            layoutJewl.rvJewl.adapter = jewlAdapter
        }
    }

    private fun observeData() {
        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                characterInfoUiState.collectLatest { uiState ->
                    if (prgDialog.isAdded) prgDialog.dismiss()

                    uiState.successOrNull()?.let { userInfo ->
                        println("xxx $userInfo")
                        binding.tvServer.text = userInfo.basic?.server
                        binding.tvClassName.text = userInfo.basic?.classInfo?.name
                        binding.tvNickname.text = userInfo.basic?.name
                        binding.tvTitle.text = userInfo.basic?.title
                        binding.tvGuild.text = userInfo.basic?.guild
                        binding.tvItemLevel.text = userInfo.basic?.levelInfo?.item?.substring(3)
                        binding.tvBattle.text = userInfo.basic?.levelInfo?.battle
                        binding.tvExpedition.text = userInfo.basic?.levelInfo?.expedition
                        binding.tvWisdom.text =
                            "${userInfo.basic?.wisdom?.level} ${userInfo.basic?.wisdom?.name}"

                        Glide.with(this@MainActivity)
                            .load(userInfo.basic?.classInfo?.iconUrl)
                            .into(binding.ivClassIcon)

                        Glide.with(this@MainActivity)
                            .load(userInfo.avatarImgUrl)
                            .into(binding.ivAvatar)

                        jewlAdapter.submitList(userInfo.jewl)
                    }
                }
            }
        }
    }
}