package org.sjhstudio.lostark.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.sjhstudio.lostark.R
import org.sjhstudio.lostark.base.BaseActivity
import org.sjhstudio.lostark.base.successOrNull
import org.sjhstudio.lostark.databinding.ActivityMainBinding
import org.sjhstudio.lostark.ui.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                userInfoUiState.collectLatest { uiState ->
                    uiState.successOrNull()?.let { userInfo ->
                        println("xxx $userInfo")

                        Glide.with(this@MainActivity)
                            .load(userInfo.avatarImgUrl)
                            .into(binding.ivAvatar)
                    }
                }
            }
        }
    }
}