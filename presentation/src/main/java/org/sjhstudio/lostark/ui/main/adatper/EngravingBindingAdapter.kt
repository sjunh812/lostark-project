package org.sjhstudio.lostark.ui.main.adatper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import org.sjhstudio.lostark.domain.model.response.Engraving

/**
 * @description : 각인 레벨 (`fullName`의 마지막 인덱스에서 substring()하여 추출)
 * @example : "예리한 둔기 Lv. 3" → "3"
 */
@BindingAdapter("engravingLevel")
fun TextView.bindEngravingLevel(fullName: String?) {
    if (!fullName.isNullOrEmpty()) {
        text = fullName.substring(fullName.length - 1)
    }
}

/**
 * @description : 각인 이름 (`fullName`에서 "Lv. x" 부분을 제외하여 추출)
 * @example : "예리한 둔기 Lv. 3" → "예리한 둔기"
 */
@BindingAdapter("engravingName")
fun TextView.bindEngravingName(fullName: String?) {
    if (!fullName.isNullOrEmpty()) {
        text = fullName.substring(0, fullName.length - 6)
    }
}

// 첫 번째 장착각인 이미지
@BindingAdapter("engravingSlot1Image")
fun ImageView.bindEngravingSlot1Image(slots: List<Engraving.Slot>?) {
    visibility = if (!slots.isNullOrEmpty()) {
        Glide.with(context)
            .load(slots[0].iconUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
            .into(this)
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

// 두 번째 장착각인 이미지
@BindingAdapter("engravingSlot2Image")
fun ImageView.bindEngravingSlot2Image(slots: List<Engraving.Slot>?) {
    visibility = if (!slots.isNullOrEmpty() && slots.size > 1) {
        Glide.with(context)
            .load(slots[1].iconUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
            .into(this)
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

