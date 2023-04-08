package org.sjhstudio.lostark.data.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.lostark.data.local.source.HistoryLocalDataSource
import org.sjhstudio.lostark.domain.model.response.History
import org.sjhstudio.lostark.domain.repository.HistoryRepository
import javax.inject.Inject

internal class HistoryRepositoryImpl @Inject constructor(
    private val historyLocalDataSource: HistoryLocalDataSource
) : HistoryRepository {

    override suspend fun getHistoryList(): Flow<List<History>> {
       return historyLocalDataSource.getHistoryList()
    }

    override suspend fun insertHistory(history: History) {
        historyLocalDataSource.insertHistory(history)
    }

    override suspend fun deleteHistory(history: History) {
        historyLocalDataSource.deleteHistory(history)
    }
}