package org.sjhstudio.lostark.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sjhstudio.lostark.data.mapperToProfile
import org.sjhstudio.lostark.data.source.ArmoryDataSource
import org.sjhstudio.lostark.domain.model.LostArkApiResult
import org.sjhstudio.lostark.domain.model.response.Profile
import org.sjhstudio.lostark.domain.repository.ArmoryRepository
import javax.inject.Inject

internal class ArmoryRepositoryImpl @Inject constructor(
    private val armoryDataSource: ArmoryDataSource
): ArmoryRepository {

    override suspend fun getProfile(characterName: String): Flow<LostArkApiResult<Profile>> =
        flow {
            val profileDto = armoryDataSource.getProfile(characterName)
            val apiResult = LostArkApiResult(
                success = profileDto != null,
                data = profileDto?.let { dto -> mapperToProfile(dto) }
            )

            emit(apiResult)
        }
}