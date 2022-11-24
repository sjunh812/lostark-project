package org.sjhstudio.lostark.data.source

import org.sjhstudio.lostark.data.api.LostArkService
import org.sjhstudio.lostark.data.exception.EmptyBodyException
import org.sjhstudio.lostark.data.exception.NetworkErrorException
import org.sjhstudio.lostark.data.model.UserInfoDto
import javax.inject.Inject

internal interface UserInfoDataSource {

    suspend fun getUserInfo(userName: String): UserInfoDto
}

internal class UserInfoDataSourceImpl @Inject constructor(
    private val lostArkApi: LostArkService
) : UserInfoDataSource {

    override suspend fun getUserInfo(userName: String): UserInfoDto {
        println("xxx getUserInfo($userName)")
        try {
            val response = lostArkApi.getUserInfo(userName)

            if (response.isSuccessful) {
                println("xxx hi")
                return response.body()
                    ?: throw EmptyBodyException("[${response.code()}] : ${response.raw()}")
            } else {
                println("xxx hi?")
                throw NetworkErrorException("[${response.code()}] : ${response.raw()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("xxx $e")
            throw NetworkErrorException(e.message)
        }
    }
}
