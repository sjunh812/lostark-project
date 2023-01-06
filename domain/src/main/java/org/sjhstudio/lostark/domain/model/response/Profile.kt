package org.sjhstudio.lostark.domain.model.response

data class Profile(
    val characterImageUrl: String,  // 캐릭터 이미지 링크
    val expeditionLevel: String,   // 원정대 레벨
    val pvpGradeName: String,   // PvP 등급
    val townLevel: String, // 영지 레벨
    val townName: String,  // 영지명
    val title: String?,  // 칭호
    val guildMemberGrade: String,   // 길드 맴버 등급
    val guildName: String,  // 길드명
    val stats: List<Stat>,  // 특성
    val tendencies: List<Tendency>,   // 성향
    val serverName: String, // 서버명
    val characterName: String,  // 캐릭터명
    val characterLevel: String, // 캐릭터 전투 레벨
    val characterClassName: String, // 캐릭터 클래스명
    val itemLevel: String   // 무기 아이템 레벨
) {

    // 특성
    data class Stat(
        val type: String,
        val value: String,
        val tooltip: List<String>
    )

    // 성향
    data class Tendency(
        val type: String,
        val point: String,
        val maxPoint: String
    )
}