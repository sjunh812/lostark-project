package org.sjhstudio.lostark.ui.adatper

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import org.sjhstudio.lostark.R
import org.sjhstudio.lostark.domain.model.response.Equipment
import org.sjhstudio.lostark.util.dpToPx

/**
 * 장비(Equipment) 바인딩 유의사항
 *
 * 장비 종류를 Key로 하여 장비 Map에서 Value값을 찾음.
 * <장비 종류>
 * 1. 장비 : 무기, 투구, 견갑, 상의, 하의, 장갑
 * 2. 악세사리  : 목걸이, 귀걸이, 반지, 팔찌, 어빌리티 스톤
 *
 * @since 2023.02.05
 */

/**
 * @description : 장비 이미지(URL)
 *                장비 등급(고대, 유물, 전설, 영웅, 고급)에 따라 배경 설정
 */
@BindingAdapter(value = ["imageType", "equipmentMap"])
fun ImageView.bindEquipmentImage(imageType: String, equipmentMap: HashMap<String, Equipment>?) {
    if (equipmentMap != null && equipmentMap.containsKey(imageType) && !equipmentMap[imageType]?.iconUrl.isNullOrEmpty()) {
        Glide.with(context)
            .load(equipmentMap[imageType]?.iconUrl)
            .into(this)

        background = when (equipmentMap[imageType]?.grade) {
            "고대" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_ancient)
            "유물" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_relic)
            "전설" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_legend)
            "영웅" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_hero)
            "희귀" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_rare)
            "고급" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_high)
            else -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_default)
        }
    } else {
        background = ContextCompat.getDrawable(context, R.drawable.bg_equipment_default)
        setImageResource(0)
    }
}

/**
 * @description : 장비 이름
 */
@BindingAdapter(value = ["nameType", "equipmentMap"])
fun TextView.bindEquipmentName(nameType: String, equipmentMap: HashMap<String, Equipment>?) {
    text =
        if (equipmentMap != null && equipmentMap.containsKey(nameType)) equipmentMap[nameType]?.name
        else ""
}

/**
 * @description : 장비 품질
 *                품질 범위에 따라 배경 설정
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
 * @description : 장비 Summary
 * @example : 무기 20(이름 레벨)
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
 * @description : 장비 세트
 * @example : 환각 Lv.2(이름 Lv.레벨)
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

/**
 * @description : 악세사리 기본 특성
 * @example : 치명 190(이름 수치)
 */
@BindingAdapter(value = ["effectType", "equipmentMap"])
fun TextView.bindAccessoryEffect(effectType: String, equipmentMap: HashMap<String, Equipment>?) {
    if (equipmentMap != null && equipmentMap.containsKey(effectType)) {
        val effectList = equipmentMap[effectType]?.effects
        var effectText = ""

        effectList?.forEachIndexed { i, effect ->
            if (!effect.isSpecial) {
                effectText += "${effect.name} ${effect.value}"
                if (i != effectList.lastIndex) effectText += "  "
            }
        }

        text = effectText
    } else {
        text = ""
    }
}

/**
 * @description : 악세사리 각인
 * @example : 질풍노도 3(이름 활성도)
 */
@SuppressLint("SetTextI18n")
@BindingAdapter(value = ["engravingType", "equipmentMap"])
fun ChipGroup.bindAccessoryEngraving(
    engravingType: String,
    equipmentMap: HashMap<String, Equipment>?
) {
    removeAllViews()    // ChipGroup 초기화

    if (equipmentMap != null && equipmentMap.containsKey(engravingType)) {
        equipmentMap[engravingType]?.engravings?.let { list ->
            list.forEach { engraving ->
                val chip: Chip = Chip(context).apply {
                    val drawable = ChipDrawable.createFromAttributes(
                        context,
                        null,
                        0,
                        R.style.Widget_LostArk_Chip
                    )
                    setChipDrawable(drawable)
                    setTextAppearanceResource(R.style.TextAppearance_LostArk_Chip)
                    text = "${engraving.name} ${engraving.active}"
                    isCheckable = false
                    isClickable = false
                    ensureAccessibleTouchTarget(context.dpToPx(30).toInt())
                    setEnsureMinTouchTargetSize(false)
                }

                addView(chip)
            }
        }
    }
}