package org.sjhstudio.lostark.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sjhstudio.lostark.data.*
import org.sjhstudio.lostark.data.source.ArmoryDataSource
import org.sjhstudio.lostark.domain.model.LostArkApiResult
import org.sjhstudio.lostark.domain.model.response.*
import org.sjhstudio.lostark.domain.repository.ArmoryRepository
import javax.inject.Inject

internal class ArmoryRepositoryImpl @Inject constructor(
    private val armoryDataSource: ArmoryDataSource
) : ArmoryRepository {

    override suspend fun getProfile(characterName: String): Flow<LostArkApiResult<Profile>> =
        flow {
            val profileDto = armoryDataSource.getProfile(characterName)
            val apiResult = LostArkApiResult(
                success = profileDto != null,
                data = profileDto?.let { dto -> mapperToProfile(dto) }
            )

            emit(apiResult)
        }

    override suspend fun getEngraving(characterName: String): Flow<LostArkApiResult<Engraving>> =
        flow {
            val engravingDto = armoryDataSource.getEngraving(characterName)
            val apiResult = LostArkApiResult(
                success = engravingDto != null,
                data = engravingDto?.let { dto -> mapperToEngraving(dto) }
            )

            emit(apiResult)
        }

    override suspend fun getEquipment(characterName: String): Flow<LostArkApiResult<HashMap<String, Equipment>>> =
        flow {
            val equipmentListDto = armoryDataSource.getEquipment(characterName)

            val apiResult = LostArkApiResult(
                success = equipmentListDto != null,
                data = equipmentListDto?.let { dto -> mapperToEquipmentMap(dto) }
            )

            emit(apiResult)
        }

    override suspend fun getGem(characterName: String): Flow<LostArkApiResult<Gem>> =
        flow {
            val gemDto = armoryDataSource.getGem(characterName)
            val apiResult = LostArkApiResult(
                success = gemDto != null,
                data = gemDto?.let { dto -> mapperToGem(dto) }
            )

            emit(apiResult)
        }

    override suspend fun getCard(characterName: String): Flow<LostArkApiResult<Card>> =
        flow {
            val cardDto = armoryDataSource.getCard(characterName)
            val apiResult = LostArkApiResult(
                success = cardDto != null,
                data = cardDto?.let { dto -> mapperToCard(dto) }
            )

            emit(apiResult)
        }
}