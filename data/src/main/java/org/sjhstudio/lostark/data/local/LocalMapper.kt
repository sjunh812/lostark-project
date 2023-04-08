package org.sjhstudio.lostark.data.local

import org.sjhstudio.lostark.data.local.model.HistoryEntity
import org.sjhstudio.lostark.domain.model.response.History

fun mapperToHistoryList(entities: List<HistoryEntity>): List<History> {
    return entities.map { entity -> entity.toHistory() }
}

fun mapperToHistoryEntity(history: History) = HistoryEntity(history.name)