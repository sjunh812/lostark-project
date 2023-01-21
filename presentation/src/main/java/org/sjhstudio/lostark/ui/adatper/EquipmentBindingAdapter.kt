package org.sjhstudio.lostark.ui.adatper

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.sjhstudio.lostark.R
import org.sjhstudio.lostark.domain.model.response.Equipment

/**
 * @description : 장비 이미지 바인딩
 */
@BindingAdapter(value = ["imageType", "equipmentMap"])
fun ImageView.bindEquipmentImage(imageType: String, equipmentMap: HashMap<String, Equipment>?) {
    if (equipmentMap != null && equipmentMap.containsKey(imageType) && !equipmentMap[imageType]?.iconUrl.isNullOrEmpty()) {
        background = when (equipmentMap[imageType]?.grade) {
            "고대" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_ancient)
            "유물" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_relic)
            "전설" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_legend)
            else -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_default)
        }
        Glide.with(context)
            .load(equipmentMap[imageType]?.iconUrl)
            .into(this)
    } else {
        background = ContextCompat.getDrawable(context, R.drawable.bg_equipment_default)
        setImageResource(0)
    }
}

/**
 * @description     : 장비 이름 바인딩
 */
@BindingAdapter(value = ["nameType", "equipmentMap"])
fun TextView.bindEquipmentName(nameType: String, equipmentMap: HashMap<String, Equipment>?) {
    if (equipmentMap != null && equipmentMap.containsKey(nameType)) {
        text = equipmentMap[nameType]?.name
        isVisible = true
    } else {
        text = ""
        isVisible = false
    }
}

/**
 * @description     : 장비 품질 바인딩
 */
@BindingAdapter(value = ["qualityType", "equipmentMap"])
fun TextView.bindEquipmentQuality(qualityType: String, equipmentMap: HashMap<String, Equipment>?) {
    if (equipmentMap != null && equipmentMap.containsKey(qualityType)) {
        text = equipmentMap[qualityType]?.quality
        background = when (equipmentMap[qualityType]?.quality?.toIntOrNull()) {
            100 -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_quality_1)
            in 90..99 -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_quality_2)
            in 70..89 -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_quality_3)
            in 30..69 -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_quality_4)
            in 10..29 -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_quality_5)
            in 1..9 -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_quality_6)
            else -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_quality_7)
        }
        isVisible = true
    } else {
        text = context.getString(R.string.label_default_quality)
        background = ContextCompat.getDrawable(context, R.drawable.bg_equipment_quality_7)
    }
}

/**
 * @description     : 장비 요약 바인딩
 * @example         : 무기 20(이름 레벨)
 */
@BindingAdapter(value = ["summaryType", "equipmentMap"])
fun TextView.bindEquipmentSummary(summaryType: String, equipmentMap: HashMap<String, Equipment>?) {
    if (equipmentMap != null && equipmentMap.containsKey(summaryType)) {
        text = equipmentMap[summaryType]?.summary
        isVisible = true
    } else {
        text = ""
        isVisible = false
    }
}

/**
 * @description     : 장비 세트 바인딩
 * @example         : 환각 Lv.2(세트이름 Lv.레벨)
 */
@SuppressLint("SetTextI18n")
@BindingAdapter(value = ["setType", "equipmentMap"])
fun TextView.bindingEquipmentSet(setType: String, equipmentMap: HashMap<String, Equipment>?) {
    if (equipmentMap != null && equipmentMap.containsKey(setType) && equipmentMap[setType]?.setName?.isNotEmpty() == true) {
        text = "${equipmentMap[setType]?.setName} Lv.${equipmentMap[setType]?.setLevel}"
        isVisible = true
    } else {
        text = ""
        isVisible = false
    }
}

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
