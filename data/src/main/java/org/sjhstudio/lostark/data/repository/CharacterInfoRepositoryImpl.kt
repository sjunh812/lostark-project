package org.sjhstudio.lostark.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sjhstudio.lostark.data.mapperToUserInfo
import org.sjhstudio.lostark.data.source.CharacterInfoDataSource
import org.sjhstudio.lostark.domain.model.CharacterInfo
import org.sjhstudio.lostark.domain.model.LostArkResult
import org.sjhstudio.lostark.domain.repository.CharacterInfoRepository
import javax.inject.Inject

internal class CharacterInfoRepositoryImpl @Inject constructor(
    private val characterInfoDataSource: CharacterInfoDataSource
) : CharacterInfoRepository {

    override suspend fun getCharacterInfo(userName: String): Flow<LostArkResult<CharacterInfo>> =
        flow {
            val userInfoDto = characterInfoDataSource.getCharacterInfo(userName)
            val result = LostArkResult(
                result = userInfoDto.result,
                data = if (userInfoDto.result == "Success") mapperToUserInfo(userInfoDto) else null
            )

            println("xxx jewl : ${result.data?.jewl}")

            emit(result)
        }
}