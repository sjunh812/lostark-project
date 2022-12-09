package org.sjhstudio.lostark.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.lostark.domain.model.LostArkApiResult
import org.sjhstudio.lostark.domain.model.response.Profile

interface ArmoryRepository {

    suspend fun getProfile(characterName: String): Flow<LostArkApiResult<Profile>>
}