package org.sjhstudio.lostark.util

import android.widget.TextView
import androidx.core.view.isVisible
import org.sjhstudio.lostark.domain.model.response.Equipment

data class EquipmentSet(
    var setName: String,
    var count: Int
) : Comparable<EquipmentSet> {

    override fun compareTo(other: EquipmentSet): Int {
        if (this.setName != other.setName) return this.count.compareTo(other.count)
        return this.setName.compareTo(other.setName)
    }
}

fun TextView.setEquipmentSetSummary(equipmentMap: HashMap<String, Equipment>) {
    val setList = arrayListOf<EquipmentSet>()
    var setSummary = ""

    for (equipment in equipmentMap.values) {
        if (equipment.type == "무기" || equipment.type == "투구" || equipment.type == "상의"
            || equipment.type == "하의" || equipment.type == "장갑" || equipment.type == "어깨"
        ) {
            var addFlag = true
            setList.forEach { set ->
                if (set.setName == equipment.setName) {
                    set.count++
                    addFlag = false
                }
            }
            if (addFlag) setList.add(EquipmentSet(equipment.setName, 1))
        }
    }

    setList.forEach { set ->
        setSummary += "${set.setName} ${set.count} "
    }

    isVisible = setSummary.isNotEmpty()
    text = setSummary.trim()
}
