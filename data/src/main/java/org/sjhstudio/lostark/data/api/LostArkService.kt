package org.sjhstudio.lostark.data.api

import org.sjhstudio.lostark.data.model.CharacterInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface LostArkService {

    @GET("userinfo/{user_name}")
    suspend fun getCharacterInfo(@Path("user_name") userName: String): Response<CharacterInfoDto>
}