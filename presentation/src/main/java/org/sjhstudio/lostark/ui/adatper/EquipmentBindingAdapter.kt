package org.sjhstudio.lostark.ui.adatper

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.sjhstudio.lostark.R
import org.sjhstudio.lostark.domain.model.response.Equipment

@BindingAdapter("equipmentImage")
fun ImageView.bindEquipmentImage(equipment: Equipment?) {
    equipment?.let { data ->
        background = when (data.grade) {
            "고대" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_ancient)
            "유물" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_relic)
            "전설" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_legend)
            else -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_default)
        }

        Glide.with(context)
            .load(data.iconUrl)
            .into(this)
    }
}
