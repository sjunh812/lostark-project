package org.sjhstudio.data.api

import org.sjhstudio.data.model.UserInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface LostArkService {

    @GET("userinfo/{user_name}")
    suspend fun getUserInfo(@Path("user_name") userName: String): Response<UserInfoDto>
}