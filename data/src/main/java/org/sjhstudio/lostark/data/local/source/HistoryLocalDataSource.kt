package org.sjhstudio.lostark.data.local.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.sjhstudio.lostark.data.local.AppDatabase
import org.sjhstudio.lostark.data.local.mapperToHistoryEntity
import org.sjhstudio.lostark.data.local.mapperToHistoryList
import org.sjhstudio.lostark.domain.model.response.History
import javax.inject.Inject

internal interface HistoryLocalDataSource {

    suspend fun getHistoryList(): Flow<List<History>>

    suspend fun insertHistory(history: History)

    suspend fun deleteHistory(history: History)
}

internal class HistoryLocalDataSourceImpl @Inject constructor(
    private val database: AppDatabase
): HistoryLocalDataSource {

    override suspend fun getHistoryList(): Flow<List<History>> {
        return database.historyDao().getHistoryList().map { entities -> mapperToHistoryList(entities) }
    }

    override suspend fun insertHistory(history: History) {
        database.historyDao().insertHistory(mapperToHistoryEntity(history))
    }

    override suspend fun deleteHistory(history: History) {
        database.historyDao().deleteHistory(mapperToHistoryEntity(history))
    }
}