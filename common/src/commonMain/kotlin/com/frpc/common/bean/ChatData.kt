package com.frpc.common.bean

import kotlinx.serialization.Serializable

@Serializable
data class ChatData(
    val text: String,
    val isSelf: Boolean
)