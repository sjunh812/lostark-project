package org.sjhstudio.lostark.data.api

import org.sjhstudio.lostark.data.model.armory.ProfileDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ArmoryService {

    @GET("/armories/characters/{characterName}/profiles")
    suspend fun getProfile(@Path("characterName") characterName: String): Response<ProfileDto?>
}