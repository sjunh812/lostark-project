package org.sjhstudio.lostark.data.di

import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.lostark.data.local.source.HistoryLocalDataSource
import org.sjhstudio.lostark.data.local.source.HistoryLocalDataSourceImpl
import org.sjhstudio.lostark.data.remote.source.ArmoryDataSource
import org.sjhstudio.lostark.data.remote.source.ArmoryDataSourceImpl
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindArmoryDataSource(armoryDataSourceImpl: ArmoryDataSourceImpl): ArmoryDataSource

    @Binds
    @Singleton
    abstract fun bindHistoryLocalDataSource(historyLocalDataSourceImpl: HistoryLocalDataSourceImpl): HistoryLocalDataSource
}