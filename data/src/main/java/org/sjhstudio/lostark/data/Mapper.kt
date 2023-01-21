package org.sjhstudio.lostark.data

import org.sjhstudio.lostark.data.model.armory.EngravingDto
import org.sjhstudio.lostark.data.model.armory.EquipmentDto
import org.sjhstudio.lostark.data.model.armory.ProfileDto
import org.sjhstudio.lostark.domain.model.response.Engraving
import org.sjhstudio.lostark.domain.model.response.Equipment
import org.sjhstudio.lostark.domain.model.response.Profile

// 프로필 매핑
internal fun mapperToProfile(profileDto: ProfileDto) =
    Profile(
        characterImageUrl = profileDto.characterImage ?: "",
        expeditionLevel = profileDto.expeditionLevel.toString(),
        pvpGradeName = profileDto.pvpGradeName,
        townLevel = profileDto.townLevel.toString(),
        townName = profileDto.townName,
        title = profileDto.title ?: "",
        guildMemberGrade = profileDto.guildMemberGrade ?: "",
        guildName = profileDto.guildName ?: "",
        stats = profileDto.stats.map { statDto ->
            Profile.Stat(
                type = statDto.type,
                value = statDto.value,
                tooltip = statDto.tooltip
            )
        },
        tendencies = profileDto.tendencies.map { tendencyDto ->
            Profile.Tendency(
                type = tendencyDto.type,
                point = tendencyDto.point.toString(),
                maxPoint = tendencyDto.maxPoint.toString()
            )
        },
        serverName = profileDto.serverName,
        characterName = profileDto.characterName,
        characterLevel = profileDto.characterLevel.toString(),
        characterClassName = profileDto.characterClassName,
        itemLevel = profileDto.itemAvgLevel
    )

// 각인 매핑
internal fun mapperToEngraving(engravingDto: EngravingDto) =
    Engraving(
        slots = engravingDto.engravings.map { slotDto ->
            Engraving.Slot(
                index = slotDto.slot,
                name = slotDto.name,
                iconUrl = slotDto.icon,
                tooltip = slotDto.tooltip
            )
        },
        effects = engravingDto.effects.map { effectDto ->
            Engraving.Effect(
                name = effectDto.name,
                description = effectDto.description
            )
        }
    )

internal fun mapperToEquipmentMap(dtoList: List<EquipmentDto>): HashMap<String, Equipment> {
    val map = hashMapOf<String, Equipment>()

    dtoList.forEach { dto ->
        var type = dto.type
        val level = mapperToEquipmentLevel(dto.type, dto.name)
        val setInfo = mapperToEquipmentSet(dto.tooltip)
        val engravingList = mapperToAccessoryEngravingList(dto.type, dto.tooltip)
        val effectList = mapperToAccessoryEffectList(dto.type, dto.tooltip)

        if (map.containsKey(dto.type)) type = "${dto.type}2"

        map[type] = Equipment(
            type = dto.type,
            name = dto.name,
            iconUrl = dto.icon,
            grade = dto.grade,
            quality = mapperToEquipmentQuality(dto.type, dto.tooltip, engravingList, effectList),
            level = level,
            setName = setInfo?.get(0) ?: "",
            setLevel = setInfo?.get(1) ?: "",
            summary = mapperToEquipmentSummary(dto.type, level),
            engravings = engravingList,
            effects = effectList
        )
    }

    return map
}

// 장비(악세 포함) 매핑
internal fun mapperToEquipmentList(dtoList: List<EquipmentDto>) =
    dtoList.map { dto ->
        val level = mapperToEquipmentLevel(dto.type, dto.name)
        val setInfo = mapperToEquipmentSet(dto.tooltip)
        val engravingList = mapperToAccessoryEngravingList(dto.type, dto.tooltip)
        val effectList = mapperToAccessoryEffectList(dto.type, dto.tooltip)
        Equipment(
            type = dto.type,
            name = dto.name,
            iconUrl = dto.icon,
            grade = dto.grade,
            quality = mapperToEquipmentQuality(dto.type, dto.tooltip, engravingList, effectList),
            level = level,
            setName = setInfo?.get(0) ?: "",
            setLevel = setInfo?.get(1) ?: "",
            summary = mapperToEquipmentSummary(dto.type, level),
            engravings = engravingList,
            effects = effectList
        )
    }

internal fun mapperToEquipmentQuality(
    type: String,
    tooltip: String,
    engravingList: List<Equipment.Engraving>?,
    effectList: List<Equipment.Effect>?
): String {
    var quality = ""

    when (type) {
        "팔찌" -> {   // 팔찌의 경우 기본효과 및 전투특성 표시 → ex) 치 특 힘
            effectList?.forEach { effect ->
                if (!effect.isSpecial) quality += "${effect.name[0]} "
            }
        }
        "어빌리티 스톤" -> {  // 어빌리티 스톤의 경우 세공 결과 표시
            engravingList?.let { engravings ->
                engravings.forEach { engraving ->
                    quality += "${engraving.active} "
                }
            }
        }
        else -> {
            // ex) qualityValue": 96,
            val startIndexOfWord = tooltip.indexOf("qualityValue")
            val startIndexOfQuality = startIndexOfWord + 15

            for (i in startIndexOfQuality..startIndexOfQuality + 3) {
                if (tooltip[i].isDigit()) quality += tooltip[i]
            }
        }
    }

    return quality.trim()
}

internal fun mapperToEquipmentLevel(type: String, name: String): String {
    return when (type) {
        "무기", "투구", "어깨", "상의", "하의", "장갑" -> name.split(" ")[0].substring(1)
        else -> "-1"
    }
}

internal fun mapperToEquipmentSet(tooltip: String): ArrayList<String>? {
    val set = arrayListOf<String>()
    val startIndexOfWord = tooltip.indexOf("Lv.")
    val startIndexOfSetName = startIndexOfWord - 25

    return try {
        set.add(tooltip.substring(startIndexOfSetName, startIndexOfSetName + 2))
        set.add(tooltip[startIndexOfWord + 3].toString())
        set
    } catch (e: Exception) {
        null
    }
}

internal fun mapperToEquipmentSummary(type: String, level: String): String {
    var summary = type
    if (level != "-1") summary += " $level"
    return summary
}

// 악세(어빌리티 스톤 포함)에 부여된 각인 효과 매핑
// 감소 효과 각인은 리스트의 마지막 원소로 삽입!
internal fun mapperToAccessoryEngravingList(
    type: String,
    tooltip: String
): List<Equipment.Engraving>? {
    return when (type) {
        "목걸이", "귀걸이", "반지", "어빌리티 스톤" -> {
            val list = arrayListOf<Equipment.Engraving>()
            var index = tooltip.indexOf("[<FONT COLOR='#FFFFAC'>")

            while (index != -1) {
                var i = index + 23
                var name = ""
                var active = ""
                var nameFlag = true

                while (tooltip[i] != '}') {
                    if (tooltip[i] == '<') {
                        if (nameFlag) nameFlag = false
                        else break
                    }
                    if (nameFlag) name += tooltip[i]
                    if (tooltip[i].isDigit()) active += tooltip[i]
                    i++
                }

                list.add(Equipment.Engraving(name, active))
                index =
                    if (list.size >= 2) tooltip.indexOf("[<FONT COLOR='#FE2E2E'>", i)
                    else tooltip.indexOf("[<FONT COLOR='#FFFFAC'>", i)
            }

            list
        }
        else -> null
    }
}

internal fun mapperToAccessoryEffectList(
    type: String,
    tooltip: String
): List<Equipment.Effect>? {
    val list = arrayListOf<Equipment.Effect>()
    var index = tooltip.indexOf("Element_001")

    return when (type) {
        "팔찌" -> {
            index = tooltip.indexOf("</img>")

            while (index != -1) {
                var i = index + 6
                var str = ""

                while (!str.contains("<img") && !str.contains("\"\r\n")) {
                    str += tooltip[i++]
                }
                str = str.trim()

                var name: String = ""
                var value: String   = ""
                var special: Boolean = false

                if (str.startsWith("[")) {
                    val end = str.indexOf("]")
                    name = str.substring(1, end)
                    value = str.substring(end + 2).replace("<BR><img", "")
                    special = true
                } else {
                    val words = str.split(" ")
                    words.forEachIndexed { idx, word ->
                        if (idx != words.lastIndex) {
                            name += word
                        } else {
                            val valueCandidate = word.substring(1)
                            for (j in valueCandidate.indices) {
                                if (valueCandidate[j].isDigit()) value += valueCandidate[j]
                            }
                        }
                    }
                    special = false
                }


                list.add(Equipment.Effect(name, value, special))
                println("xxx 팔찌 : $list")
                index = tooltip.indexOf("</img>", index + 1)
            }

            list
        }
        "목걸이", "귀걸이", "반지", "어빌리티 스톤" -> {
            var start = 0
            val last = if (type == "어빌리티 스톤") 1 else 2

            while (index != -1 || start <= last) {
                if (start++ == last) {
                    var i = index + 15
                    var str = ""

                    while (tooltip[i] != '"') {
                        str += tooltip[i++]
                    }

                    str.split("<BR>").forEach { effect ->
                        list.add(
                            Equipment.Effect(
                                name = effect.split(" +")[0],
                                value = effect.split(" +")[1]
                            )
                        )
                    }
                    println("xxx $str")
                }

                index = tooltip.indexOf("Element_001", index + 1)
            }

            list
        }
        else -> null
    }
}