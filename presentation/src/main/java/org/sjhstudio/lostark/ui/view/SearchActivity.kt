package org.sjhstudio.lostark.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import org.sjhstudio.lostark.R
import org.sjhstudio.lostark.base.BaseActivity
import org.sjhstudio.lostark.databinding.ActivitySearchBinding
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    @Inject
    lateinit var imm: InputMethodManager

    companion object {
        const val EXTRA_NICKNAME = "extra_nickname"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
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
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(EXTRA_NICKNAME, nickname)
        }
        startActivity(intent)
    }
}