package org.sjhstudio.lostark.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.lostark.domain.model.LostArkApiResult
import org.sjhstudio.lostark.domain.model.response.*

interface ArmoryRepository {

    suspend fun getProfile(characterName: String): Flow<LostArkApiResult<Profile>>

    suspend fun getEngraving(characterName: String): Flow<LostArkApiResult<Engraving>>

    suspend fun getEquipment(characterName: String): Flow<LostArkApiResult<HashMap<String, Equipment>>>

    suspend fun getGem(characterName: String): Flow<LostArkApiResult<Gem>>

    suspend fun getCard(characterName: String): Flow<LostArkApiResult<Card>>
}