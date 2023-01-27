package org.sjhstudio.lostark.domain.model.response

data class Gem(
    val gems: List<GemInfo>,
    val effects: List<Effect>
) {

    data class GemInfo(
        val slot: String,
        val name: String,
        val iconUrl: String,
        val level: String,
        val grade: String
    )

    data class Effect(
        val gemSlot: String,
        val name: String,
        val description: String,
        val iconUrl: String
    )
}