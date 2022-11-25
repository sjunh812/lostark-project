package org.sjhstudio.lostark.domain.model

data class LostArkResult<T>(
    val result: String,
    val data: T? = null
)