package org.sjhstudio.lostark.domain.model.response

data class Equipment(
    val type: String,
    val name: String,
    val iconUrl: String,
    val grade: String,
    val quality: String,
    val level: String
)