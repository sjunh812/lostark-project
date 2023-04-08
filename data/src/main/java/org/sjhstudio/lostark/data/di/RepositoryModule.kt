package org.sjhstudio.lostark.data.di

import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.lostark.data.repository.ArmoryRepositoryImpl
import org.sjhstudio.lostark.data.repository.HistoryRepositoryImpl
import org.sjhstudio.lostark.domain.repository.ArmoryRepository
import org.sjhstudio.lostark.domain.repository.HistoryRepository
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindArmoryRepository(armoryRepositoryImpl: ArmoryRepositoryImpl): ArmoryRepository

    @Binds
    @Singleton
    abstract fun bindHistoryRepository(historyRepositoryImpl: HistoryRepositoryImpl): HistoryRepository
}