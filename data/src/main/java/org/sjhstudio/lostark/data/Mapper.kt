package org.sjhstudio.lostark.data

import org.sjhstudio.lostark.data.model.armory.EngravingDto
import org.sjhstudio.lostark.data.model.armory.EquipmentDto
import org.sjhstudio.lostark.data.model.armory.GemDto
import org.sjhstudio.lostark.data.model.armory.ProfileDto
import org.sjhstudio.lostark.domain.model.response.Engraving
import org.sjhstudio.lostark.domain.model.response.Equipment
import org.sjhstudio.lostark.domain.model.response.Gem
import org.sjhstudio.lostark.domain.model.response.Profile

/**
 * @description     : 프로필 매핑
 */
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

/**
 * @description     : 각인 매핑
 */
internal fun mapperToEngraving(engravingDto: EngravingDto) =
    Engraving(
        slots = engravingDto.engravings?.map { slotDto ->
            Engraving.Slot(
                index = slotDto.slot,
                name = slotDto.name,
                iconUrl = slotDto.icon,
                tooltip = slotDto.tooltip
            )
        },
        effects = engravingDto.effects?.map { effectDto ->
            Engraving.Effect(
                name = effectDto.name,
                description = effectDto.description
            )
        }
    )

/**
 * @description     : 장비 매핑
 * @return          : HashMap<String, Equipment>
 */
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

/**
 * @description     : 보석 매핑
 */
internal fun mapperToGem(dto: GemDto): Gem {
    val gems = mutableListOf<Gem.GemInfo>()

    dto.gems.forEach { gemDto ->
        val priority =
            if (gemDto.name.contains("멸화")) 0
            else if (gemDto.name.contains("홍염")) 1
            else if (gemDto.name.contains("청명")) 2
            else if (gemDto.name.contains("원해")) 3
            else 4
        val gemInfo = Gem.GemInfo(
            priority = priority,
            slot = gemDto.slot.toString(),
            name = gemDto.name,
            iconUrl = gemDto.icon,
            level = gemDto.level.toString(),
            grade = gemDto.grade
        )
        gems.add(gemInfo)
    }
    dto.effects.forEach { effectDto ->
        val effect = Gem.GemInfo.Effect(
            gemSlot = effectDto.gemSlot.toString(),
            name = effectDto.name,
            description = effectDto.description,
            iconUrl = effectDto.icon
        )
        gems.forEach { gem ->
            if (gem.slot.toIntOrNull() == effectDto.gemSlot) {
                gem.effect = effect
            }
        }
    }
    gems.sortWith { gem1, gem2 ->
        if (gem1.priority < gem2.priority) -1
        else if (gem1.priority > gem2.priority) 1
        else {
            if (gem1.level < gem2.level) 1
            else -1
        }
    }

    return Gem(gems = gems)
}

/**
 * @description     : 장비 품질 매핑
 *                    1) 팔찌 : 기본효과 및 전투특성 표시 -- ex) 치 특 힘
 *                    2) 어빌리티 스톤 : 세공결과 표시 -- ex) 9 7 1
 *                    3) 장비 : 품질수치 표시 -- ex) 99
 */
internal fun mapperToEquipmentQuality(
    type: String,
    tooltip: String,
    engravingList: List<Equipment.Engraving>?,
    effectList: List<Equipment.Effect>?
): String {
    var quality = ""

    when (type) {
        "팔찌" -> {
            effectList?.forEach { effect ->
                if (!effect.isSpecial) quality += "${effect.name[0]} "
            }
        }
        "어빌리티 스톤" -> {
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

/**
 * @description     : 장비 레벨 매핑
 */
internal fun mapperToEquipmentLevel(type: String, name: String): String {
    return when (type) {
        "무기", "투구", "어깨", "상의", "하의", "장갑" -> name.split(" ")[0].substring(1)
        else -> "-1"
    }
}

/**
 * @description     : 장비 별 세트 정보 매핑
 */
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

/**
 * @description     : 악세를 제외한 장비 요약 매핑
 *                    종류 + 장비로 표현 -- ex) 무기 25
 */
internal fun mapperToEquipmentSummary(type: String, level: String): String {
    var summary = type
    if (level != "-1") summary += " $level"

    return summary
}

/**
 * @description     : 악세 각인 매핑
 *                    악세(어빌리티 스톤 포함)에 부여된 각인 효과를 매핑하며
 *                    감소 효과 각인의 경우 리스트의 마지막 원소로 삽입!
 */
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

/**
 * @description     : 악세 효과 매핑
 *                    특수효과(isSpecial = true, 팔찌에 한함) -- ex) 순환, 정밀
 *                    기본효과(isSpecial = false) -- ex) 치명, 특화, 힘, 체력
 */
internal fun mapperToAccessoryEffectList(
    type: String,
    tooltip: String
): List<Equipment.Effect>? {
    val effectList = arrayListOf<Equipment.Effect>()
    var index = tooltip.indexOf("Element_001")

    return when (type) {
        "팔찌" -> {
            index = tooltip.indexOf("</img>")

            while (index != -1) {
                var i = index + 6
                var str = ""

                index = tooltip.indexOf("</img>", index + 1)

                while (!str.contains("<img") && !str.contains("\"\r\n")) {
                    str += tooltip[i++]
                }
                str = str.replace("\"\r\n", "").trim()

                var name: String = ""   // 효과
                var value: String = ""
                var special: Boolean = false

                if (str.startsWith("[")) {
                    val end = str.indexOf("]")
                    name = str.substring(1, end)
                    value = str.substring(end + 2).replace("<BR><img", "")
                    special = true
                } else {
                    if (str.contains("효과 부여 가능")) continue

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

                effectList.add(Equipment.Effect(name, value, special))
                println("xxx 팔찌 : $effectList")
            }

            effectList
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
                        effectList.add(
                            Equipment.Effect(
                                name = effect.split(" +")[0],
                                value = effect.split(" +")[1]
                            )
                        )
                    }
                }

                index = tooltip.indexOf("Element_001", index + 1)
            }

            effectList
        }
        else -> null
    }
}