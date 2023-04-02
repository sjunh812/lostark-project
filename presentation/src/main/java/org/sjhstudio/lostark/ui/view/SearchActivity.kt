package org.sjhstudio.lostark.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import dagger.hilt.android.AndroidEntryPoint
import org.sjhstudio.lostark.R
import org.sjhstudio.lostark.base.BaseActivity
import org.sjhstudio.lostark.databinding.ActivitySearchBinding

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    companion object {
        const val EXTRA_NICKNAME = "extra_nickname"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            etNickname.setOnEditorActionListener { _, actionId, _ ->
                val nickname = etNickname.text.toString()

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    etNickname.text?.clear()
                    navigateToMainActivity(nickname)
                }

                false
            }
        }
    }

    private fun navigateToMainActivity(nickname: String) {
        val intent = Intent(this, MainActivity::class.java)
            .apply {
                putExtra(EXTRA_NICKNAME, nickname)
            }
        startActivity(intent)
    }
}