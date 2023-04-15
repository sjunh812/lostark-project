package org.sjhstudio.lostark.ui.search.adapter

import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import org.sjhstudio.lostark.domain.model.response.History

@BindingAdapter("searchHistoryVisible")
fun LinearLayout.bindSearchHistoryVisible(searchHistoryList: List<History>) {
    isVisible = searchHistoryList.isNotEmpty()
}