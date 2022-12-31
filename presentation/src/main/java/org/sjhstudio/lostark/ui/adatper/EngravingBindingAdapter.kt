package org.sjhstudio.lostark.ui.adatper

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import org.sjhstudio.lostark.domain.model.response.Engraving

@BindingAdapter("engravingSlot1Image")
fun ImageView.bindEngravingSlot1Image(slots: List<Engraving.Slot>?) {
    if (!slots.isNullOrEmpty()) {
        println("xxx slot 1 : ${slots[0]}")
        Glide.with(context)
            .load(slots[0].iconUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
            .into(this)
    }
}

@BindingAdapter("engravingSlot2Image")
fun ImageView.bindEngravingSlot2Image(slots: List<Engraving.Slot>?) {
    if (!slots.isNullOrEmpty() && slots.size > 1) {
        println("xxx slot 2 : ${slots[1]}")
        Glide.with(context)
            .load(slots[1].iconUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
            .into(this)
    }
}