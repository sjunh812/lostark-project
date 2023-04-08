package org.sjhstudio.lostark.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.lostark.domain.model.response.History

interface HistoryRepository {

    suspend fun getHistoryList(): Flow<List<History>>

    suspend fun insertHistory(history: History)

    suspend fun deleteHistory(history: History)
}