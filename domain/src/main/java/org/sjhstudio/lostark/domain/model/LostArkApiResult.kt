package org.sjhstudio.lostark.domain.model

data class LostArkApiResult<T>(
    val success: Boolean,
    val data: T?
)