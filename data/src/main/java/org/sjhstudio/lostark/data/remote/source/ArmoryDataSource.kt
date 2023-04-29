package org.sjhstudio.lostark.data.remote.source

import org.sjhstudio.lostark.data.exception.NetworkErrorException
import org.sjhstudio.lostark.data.remote.api.ArmoryService
import org.sjhstudio.lostark.data.remote.model.armory.*
import javax.inject.Inject

internal interface ArmoryDataSource {

    suspend fun getProfile(characterName: String): ProfileDto?

    suspend fun getEngraving(characterName: String): EngravingDto?

    suspend fun getEquipment(characterName: String): List<EquipmentDto>?

    suspend fun getGem(characterName: String): GemDto?

    suspend fun getCard(characterName: String): CardDto?
}

internal class ArmoryDataSourceImpl @Inject constructor(
    private val armoryApi: ArmoryService
) : ArmoryDataSource {

    override suspend fun getProfile(characterName: String): ProfileDto? {
        val response = armoryApi.getProfile(characterName)

        if (response.isSuccessful) {
            return response.body()
        } else {
            throw NetworkErrorException("[${response.code()}] : ${response.raw()}")
        }
    }

    override suspend fun getEngraving(characterName: String): EngravingDto? {
        val response = armoryApi.getEngraving(characterName)

        if (response.isSuccessful) {
            return response.body()
        } else {
            throw NetworkErrorException("[${response.code()}] : ${response.raw()}")
        }
    }

    override suspend fun getEquipment(characterName: String): List<EquipmentDto>? {
        val response = armoryApi.getEquipment(characterName)

        if (response.isSuccessful) {
            return response.body()
        } else {
            throw NetworkErrorException("[${response.code()}] : ${response.raw()}")
        }
    }

    override suspend fun getGem(characterName: String): GemDto? {
        val response = armoryApi.getGem(characterName)

        if (response.isSuccessful) {
            return response.body()
        } else {
            throw NetworkErrorException("[${response.code()}] : ${response.raw()}")
        }
    }

    override suspend fun getCard(characterName: String): CardDto? {
        val response = armoryApi.getCard(characterName)

        if (response.isSuccessful) {
            return response.body()
        } else {
            throw NetworkErrorException("[${response.code()}] : ${response.raw()}")
        }
    }
}