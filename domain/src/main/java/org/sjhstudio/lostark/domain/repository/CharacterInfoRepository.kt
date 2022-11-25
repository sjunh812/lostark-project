package org.sjhstudio.lostark.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.lostark.domain.model.CharacterInfo
import org.sjhstudio.lostark.domain.model.LostArkResult

interface CharacterInfoRepository {

    suspend fun getCharacterInfo(userName: String): Flow<LostArkResult<CharacterInfo>>
}