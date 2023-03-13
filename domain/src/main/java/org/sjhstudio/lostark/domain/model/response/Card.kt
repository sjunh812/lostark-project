package org.sjhstudio.lostark.domain.model.response

data class Card(
    val cards: List<CardInfo>,
    val effects: List<CardEffect>
)

data class CardInfo(
    val slot: Int,
    val name: String,
    val iconUrl: String,
    val awakeCount: Int,
    val awakeTotal: Int,
    val grade: String,
    val tooltip: String
)

data class CardEffect(
    val index: Int,
    val slots: List<Int>,
    val items: List<Item>
) {
    data class Item(
        val name: String,
        val description: String
    )
}
