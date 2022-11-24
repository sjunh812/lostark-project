package org.sjhstudio.lostark.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.lostark.domain.model.LostArkResult
import org.sjhstudio.lostark.domain.model.UserInfo

interface UserInfoRepository {

    suspend fun getUserInfo(userName: String): Flow<LostArkResult<UserInfo>>
}