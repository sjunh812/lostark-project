package org.sjhstudio.lostark.domain.model


data class UserInfo(
    val avatarImgUrl: String?,
    val basic: Basic?,
    val card: List<CardInfo>?,
    val characterList: List<Character>?,
    val collections: List<Map<String, String>>?,
    val detailedTri: Map<String, List<TriInfo>>?,
    val gold: Gold?,
    val items: Items?,
    val jewl: List<JewlInfo>?,
    val skill: Skill?
)

data class Basic(
    val classInfo: Class,
    val engrave: List<String>,
    val guild: String,
    val levelInfo: Level,
    val name: String,
    val server: String,
    val stat: Stat,
    val tendency: Tendency,
    val title: String,
    val wisdom: Wisdom
) {
    data class Class(
        val iconUrl: String,
        val name: String
    )

    data class Level(
        val battle: String,
        val expedition: String,
        val item: String
    )

    data class Stat(
        val agility: String,
        val attack: String,
        val critical: String,
        val endurance: String,
        val health: String,
        val proficiency: String,
        val specialty: String,
        val subdue: String
    )

    data class Tendency(
        val bravery: String,
        val charm: String,
        val intellect: String,
        val kindness: String
    )

    data class Wisdom(
        val level: String,
        val name: String
    )
}

data class CardInfo(
    val effect: String,
    val name: String
)

data class Character(
    val classInfo: String,
    val level: String,
    val name: String,
    val server: String
)

data class TriInfo(
    val level: String,
    val name: String
)

data class Gold(
    val goldList: List<GoldInfo>,
    val totalGold: String
) {
    data class GoldInfo(
        val classInfo: String,
        val gold: String,
        val level: String,
        val name: String
    )
}

data class Items(
    val headArmor: Armor,
    val shoulderArmor: Armor,
    val top: Armor,
    val pants: Armor,
    val glove: Armor,
    val weapon: Armor,
    val necklace: Accessory,
    val earring1: Accessory,
    val earring2: Accessory,
    val ring1: Accessory,
    val ring2: Accessory,
    val bracelet: Bracelet,
    val abilityStone: AbilityStone
) {
    data class EngraveInfo(
        val effect: String,
        val engraveName: String
    )

    data class Armor(
        val name: String,
        val quality: String
    )

    data class Accessory(
        val engrave: List<EngraveInfo>,
        val name: String,
        val plus: List<String>,
        val quality: String
    )

    data class Bracelet(
        val name: String,
        val plus: List<String>
    )

    data class AbilityStone(
        val basic: String,
        val engrave: List<EngraveInfo>,
        val name: String,
        val plus: String
    )
}

data class JewlInfo(
    val effect: String,
    val jewlName: String,
    val skillName: String
)

data class Skill(
    val skillList: List<SkillInfo>,
    val skillPoint: SkillPoint,
    val skillRune: Map<String, String>
) {
    data class SkillInfo(
        val level: String,
        val name: String,
        val tri: String
    )

    data class SkillPoint(
        val total: String,
        val used: String
    )
}