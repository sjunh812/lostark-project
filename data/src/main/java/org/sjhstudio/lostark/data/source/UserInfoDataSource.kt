package org.sjhstudio.lostark.data.source

import org.sjhstudio.lostark.data.api.LostArkService
import org.sjhstudio.lostark.data.exception.ClientErrorException
import org.sjhstudio.lostark.data.exception.EmptyBodyException
import org.sjhstudio.lostark.data.exception.NetworkErrorException
import org.sjhstudio.lostark.data.model.CharacterInfoDto
import javax.inject.Inject

internal interface CharacterInfoDataSource {

    suspend fun getCharacterInfo(userName: String): CharacterInfoDto
}

internal class CharacterInfoDataSourceImpl @Inject constructor(
    private val lostArkApi: LostArkService
) : CharacterInfoDataSource {

    override suspend fun getCharacterInfo(userName: String): CharacterInfoDto {
        try {
            val response = lostArkApi.getCharacterInfo(userName)

            if (response.isSuccessful) {
                return response.body()
                    ?: throw EmptyBodyException("[${response.code()}] : ${response.raw()}")
            } else {
                println("xxx hi?")
                throw NetworkErrorException("[${response.code()}] : ${response.raw()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw ClientErrorException(e.message)
        }
    }
}
