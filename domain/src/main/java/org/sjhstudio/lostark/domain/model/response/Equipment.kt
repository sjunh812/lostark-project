package org.sjhstudio.lostark.domain.model.response

data class Equipment(
    val type: String,   // 타입
    val name: String,   // 이름
    val iconUrl: String,    // 이미지 url
    val grade: String,  // 등급
    val quality: String,    // 품질
    val level: String,  // 레벨(악세제외)
    val setName: String,    // 세트 이름(악세제외)
    val setLevel: String,   // 세트 레벨(악세제외)
    val summary: String,    // 요약
    val engravings: List<Engraving>? = null // 악세 각인 효과 리스트(마지막 원소는 감소 각인 효과)
) {

    // 악세에 부여되는 각인 정보
    data class Engraving(
        val name: String,   // 이름
        val active: String  // 활성도
    )
}