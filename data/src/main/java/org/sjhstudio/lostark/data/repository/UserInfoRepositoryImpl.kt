package org.sjhstudio.lostark.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sjhstudio.lostark.data.mapperToUserInfo
import org.sjhstudio.lostark.data.source.UserInfoDataSource
import org.sjhstudio.lostark.domain.model.LostArkResult
import org.sjhstudio.lostark.domain.model.UserInfo
import org.sjhstudio.lostark.domain.repository.UserInfoRepository
import javax.inject.Inject

internal class UserInfoRepositoryImpl @Inject constructor(
    private val userInfoDataSource: UserInfoDataSource
) : UserInfoRepository {

    override suspend fun getUserInfo(userName: String): Flow<LostArkResult<UserInfo>> = flow {
        val userInfoDto = userInfoDataSource.getUserInfo(userName)
        val result = LostArkResult(
            result = userInfoDto.result,
            data = if (userInfoDto.result == "Success") mapperToUserInfo(userInfoDto) else null
        )

        emit(result)
    }
}