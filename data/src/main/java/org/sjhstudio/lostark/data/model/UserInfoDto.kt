package org.sjhstudio.lostark.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class UserInfoDto(
    @Json(name = "Result") val result: String,
    @Json(name = "Avatar_img") val avatarImgUrl: String? = null,
    @Json(name = "Basic") val basic: Basic? = null,
    @Json(name = "Card") val card: List<CardInfo>? = null,
    @Json(name = "CardList") val cardList: List<Card>? = null,
    @Json(name = "CharacterList") val characterList: List<Character>? = null,
    @Json(name = "Collections") val collections: List<Map<String, String>>? = null,
    @Json(name = "Detailed_Tri") val detailedTri: Map<String, List<TriInfo>>? = null,
    @Json(name = "Gold") val gold: Gold? = null,
    @Json(name = "Items") val items: Items? = null,
    @Json(name = "Jewl") val jewl: List<JewlInfo>? = null,
    @Json(name = "Sasa") val sasa: Sasa? = null,
    @Json(name = "Skill") val skill: Skill? = null
)

@JsonClass(generateAdapter = true)
internal data class Basic(
    @Json(name = "Class") val classInfo: Class,
    @Json(name = "Engrave") val engrave: List<String>,
    @Json(name = "Guild") val guild: String,
    @Json(name = "Level") val levelInfo: Level,
    @Json(name = "Name") val name: String,
    @Json(name = "Server") val server: String,
    @Json(name = "Stat") val stat: Stat,
    @Json(name = "Tendency") val tendency: Tendency,
    @Json(name = "Title") val title: String,
    @Json(name = "Wisdom") val wisdom: Wisdom
) {
    @JsonClass(generateAdapter = true)
    internal data class Class(
        @Json(name = "Icon") val iconUrl: String,
        @Json(name = "Name") val name: String
    )

    @JsonClass(generateAdapter = true)
    internal data class Level(
        @Json(name = "Battle") val battle: String,
        @Json(name = "Expedition") val expedition: String,
        @Json(name = "Item") val item: String
    )

    @JsonClass(generateAdapter = true)
    internal data class Stat(
        @Json(name = "Agility") val agility: String,
        @Json(name = "Attack") val attack: String,
        @Json(name = "Critical") val critical: String,
        @Json(name = "Endurance") val endurance: String,
        @Json(name = "Health") val health: String,
        @Json(name = "Proficiency") val proficiency: String,
        @Json(name = "Specialty") val specialty: String,
        @Json(name = "Subdue") val subdue: String
    )

    @JsonClass(generateAdapter = true)
    internal data class Tendency(
        @Json(name = "Bravery") val bravery: String,
        @Json(name = "Charm") val charm: String,
        @Json(name = "Intellect") val intellect: String,
        @Json(name = "Kindness") val kindness: String
    )

    @JsonClass(generateAdapter = true)
    internal data class Wisdom(
        @Json(name = "Level") val level: String,
        @Json(name = "Name") val name: String
    )
}

@JsonClass(generateAdapter = true)
internal data class CardInfo(
    @Json(name = "Effect") val effect: String,
    @Json(name = "Name") val name: String
)

@JsonClass(generateAdapter = true)
internal data class Card(
    @Json(name = "Awake") val awake: String,
    @Json(name = "Name") val name: String,
    @Json(name = "Tier") val tier: String
)

@JsonClass(generateAdapter = true)
internal data class Character(
    @Json(name = "Class") val classInfo: String,
    @Json(name = "Level") val level: String,
    @Json(name = "Name") val name: String,
    @Json(name = "Server") val server: String
)

@JsonClass(generateAdapter = true)
internal data class TriInfo(
    @Json(name = "lvl") val level: String,
    @Json(name = "name") val name: String
)

@JsonClass(generateAdapter = true)
internal data class Gold(
    @Json(name = "GoldList") val goldList: List<GoldInfo>,
    @Json(name = "TotalGold") val totalGold: String
) {
    @JsonClass(generateAdapter = true)
    internal data class GoldInfo(
        @Json(name = "Class") val classInfo: String,
        @Json(name = "Gold") val gold: String,
        @Json(name = "Level") val level: String,
        @Json(name = "Name") val name: String
    )
}

@JsonClass(generateAdapter = true)
internal data class Items(
    @Json(name = "머리 방어구") val headArmor: Armor,
    @Json(name = "어깨 방어구") val shoulderArmor: Armor,
    @Json(name = "상의") val top: Armor,
    @Json(name = "하의") val pants: Armor,
    @Json(name = "장갑") val glove: Armor,
    @Json(name = "무기") val weapon: Armor,
    @Json(name = "목걸이") val necklace: Accessory,
    @Json(name = "귀걸이1") val earring1: Accessory,
    @Json(name = "귀걸이2") val earring2: Accessory,
    @Json(name = "반지1") val ring1: Accessory,
    @Json(name = "반지2") val ring2: Accessory,
    @Json(name = "팔찌") val bracelet: Bracelet,
    @Json(name = "어빌리티 스톤") val abilityStone: AbilityStone
) {
    @JsonClass(generateAdapter = true)
    internal data class EngraveInfo(
        @Json(name = "Effect") val effect: String,
        @Json(name = "EngraveName") val engraveName: String
    )

    @JsonClass(generateAdapter = true)
    internal data class Armor(
        @Json(name = "Name") val name: String,
        @Json(name = "Quality") val quality: String
    )

    @JsonClass(generateAdapter = true)
    internal data class Accessory(
        @Json(name = "Engrave") val engrave: List<EngraveInfo>,
        @Json(name = "Name") val name: String,
        @Json(name = "Plus") val plus: List<String>,
        @Json(name = "Quality") val quality: String
    )

    @JsonClass(generateAdapter = true)
    internal data class Bracelet(
        @Json(name = "Name") val name: String,
        @Json(name = "Plus") val plus: List<String>
    )

    @JsonClass(generateAdapter = true)
    internal data class AbilityStone(
        @Json(name = "Basic") val basic: String,
        @Json(name = "Engrave") val engrave: List<EngraveInfo>,
        @Json(name = "Name") val name: String,
        @Json(name = "Plus") val plus: String
    )
}

@JsonClass(generateAdapter = true)
internal data class JewlInfo(
    @Json(name = "Effect") val effect: String,
    @Json(name = "JewlName") val jewlName: String,
    @Json(name = "SkillName") val skillName: String
)

@JsonClass(generateAdapter = true)
internal data class Sasa(
    @Json(name = "SasaList") val sasaList: List<String>,
    @Json(name = "SasaUrl") val sasaUrl: String
)

@JsonClass(generateAdapter = true)
internal data class Skill(
    @Json(name = "Skill") val skillList: List<SkillInfo>,
    @Json(name = "SkillPoint") val skillPoint: SkillPoint,
    @Json(name = "SkillRune") val skillRune: Map<String, String>
) {
    @JsonClass(generateAdapter = true)
    internal data class SkillInfo(
        @Json(name = "Level") val level: String,
        @Json(name = "Name") val name: String,
        @Json(name = "Tri") val tri: String
    )

    @JsonClass(generateAdapter = true)
    internal data class SkillPoint(
        @Json(name = "Total") val total: String,
        @Json(name = "Used") val used: String
    )
}