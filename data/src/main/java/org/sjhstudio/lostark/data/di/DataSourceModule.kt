package org.sjhstudio.lostark.data.di

import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.lostark.data.source.UserInfoDataSource
import org.sjhstudio.lostark.data.source.UserInfoDataSourceImpl
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindUserInfoDataSource(userInfoDataSourceImpl: UserInfoDataSourceImpl): UserInfoDataSource
}