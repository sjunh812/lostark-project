package org.sjhstudio.lostark.data.di

import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.lostark.data.repository.ArmoryRepositoryImpl
import org.sjhstudio.lostark.domain.repository.ArmoryRepository
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindArmoryRepository(armoryRepositoryImpl: ArmoryRepositoryImpl): ArmoryRepository
}