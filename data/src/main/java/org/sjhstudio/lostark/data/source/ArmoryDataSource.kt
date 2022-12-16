package org.sjhstudio.lostark.data.source

import org.sjhstudio.lostark.data.api.ArmoryService
import org.sjhstudio.lostark.data.exception.ClientErrorException
import org.sjhstudio.lostark.data.exception.NetworkErrorException
import org.sjhstudio.lostark.data.model.armory.EngravingDto
import org.sjhstudio.lostark.data.model.armory.EquipmentDto
import org.sjhstudio.lostark.data.model.armory.ProfileDto
import javax.inject.Inject

internal interface ArmoryDataSource {

    suspend fun getProfile(characterName: String): ProfileDto?

    suspend fun getEngraving(characterName: String): EngravingDto?

    suspend fun getEquipment(characterName: String): List<EquipmentDto>?
}

internal class ArmoryDataSourceImpl @Inject constructor(
    private val armoryApi: ArmoryService
) : ArmoryDataSource {

    override suspend fun getProfile(characterName: String): ProfileDto? {
        try {
            val response = armoryApi.getProfile(characterName)

            if (response.isSuccessful) {
                return response.body()
            } else {
                throw NetworkErrorException("[${response.code()}] : ${response.raw()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw ClientErrorException(e.message)
        }
    }

    override suspend fun getEngraving(characterName: String): EngravingDto? {
        try {
            val response = armoryApi.getEngraving(characterName)

            if (response.isSuccessful) {
                return response.body()
            } else {
                throw NetworkErrorException("[${response.code()}] : ${response.raw()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw ClientErrorException(e.message)
        }
    }

    override suspend fun getEquipment(characterName: String): List<EquipmentDto>? {
        try {
            val response = armoryApi.getEquipment(characterName)

            if (response.isSuccessful) {
                return response.body()
            } else {
                throw NetworkErrorException("[${response.code()}] : ${response.raw()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw ClientErrorException(e.message)
        }
    }
}