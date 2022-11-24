package org.sjhstudio.domain.model

data class LostArkResult<T>(
    val result: String,
    val data: T? = null
)