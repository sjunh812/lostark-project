package org.sjhstudio.lostark.domain.model.response

data class Gem(
    val gems: List<GemInfo>
) {

    data class GemInfo(
        val priority: Int,  // 멸화:0, 홍염:1, 청명:2, 원해:3
        val slot: String,
        val name: String,
        val iconUrl: String,
        val level: String,
        val grade: String,
        var effect: Effect? = null
    ) {
        data class Effect(
            val gemSlot: String,
            val name: String,
            val description: String,
            val iconUrl: String
        )
    }
}