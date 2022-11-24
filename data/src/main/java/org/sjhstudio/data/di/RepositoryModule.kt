package org.sjhstudio.data.di

import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.data.repository.UserInfoRepositoryImpl
import org.sjhstudio.domain.repository.UserInfoRepository
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserInfoRepository(userInfoRepositoryImpl: UserInfoRepositoryImpl): UserInfoRepository
}