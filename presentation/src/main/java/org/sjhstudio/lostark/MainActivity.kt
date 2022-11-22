package org.sjhstudio.lostark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.sjhstudio.lostark.base.successOrNull

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                userInfoUiState.collectLatest { uiState ->
                    uiState.successOrNull()?.let { userInfo ->
                        println("xxx $userInfo")
                    }
                }
            }
        }
    }
}