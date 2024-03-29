package org.sjhstudio.lostark.data.remote.api

import org.sjhstudio.lostark.data.remote.model.armory.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ArmoryService {

    @GET("/armories/characters/{characterName}/profiles")
    suspend fun getProfile(@Path("characterName") characterName: String): Response<ProfileDto?>

    @GET("/armories/characters/{characterName}/engravings")
    suspend fun getEngraving(@Path("characterName") characterName: String): Response<EngravingDto?>

    @GET("/armories/characters/{characterName}/equipment")
    suspend fun getEquipment(@Path("characterName") characterName: String): Response<List<EquipmentDto>?>

    @GET("/armories/characters/{characterName}/gems")
    suspend fun getGem(@Path("characterName") characterName: String): Response<GemDto?>

    @GET("/armories/characters/{characterName}/cards")
    suspend fun getCard(@Path("characterName") characterName: String): Response<CardDto?>
}