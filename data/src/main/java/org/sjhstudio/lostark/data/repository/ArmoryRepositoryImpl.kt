package org.sjhstudio.lostark.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sjhstudio.lostark.data.mapperToEngraving
import org.sjhstudio.lostark.data.mapperToEquipmentList
import org.sjhstudio.lostark.data.mapperToEquipmentMap
import org.sjhstudio.lostark.data.mapperToProfile
import org.sjhstudio.lostark.data.source.ArmoryDataSource
import org.sjhstudio.lostark.domain.model.LostArkApiResult
import org.sjhstudio.lostark.domain.model.response.Engraving
import org.sjhstudio.lostark.domain.model.response.Equipment
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
}