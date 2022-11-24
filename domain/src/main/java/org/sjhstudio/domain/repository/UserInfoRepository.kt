package org.sjhstudio.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.domain.model.LostArkResult
import org.sjhstudio.domain.model.UserInfo

interface UserInfoRepository {

    suspend fun getUserInfo(userName: String): Flow<LostArkResult<UserInfo>>
}