package com.frpc.common.bean

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TunnelDataBean(
    @SerialName("type")
    val type: String,
    @SerialName("compress")
    val isCompress : Boolean,
    @SerialName("encrypt")
    val isEncrypt : Boolean,

    @SerialName("tunnelName")
    val tunnelName : String,
    @SerialName("localAddress")
    val localAddress : String,
    @SerialName("localPort")
    val localPort : Int,
    @SerialName("remotePort")
    val remotePort : Int,

)