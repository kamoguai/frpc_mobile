package com.frpc.common.bean

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerInfoBean(
    @SerialName("name")
    val name: String,
    @SerialName("remoteAddress")
    val remoteAddress: String,
    @SerialName("remotePort")
    val remotePort: Int,
    @SerialName("tunnel")
    val tunnelDataBean: TunnelDataBean
) {

    var isStart: Boolean = false
    var isSelect = false

    var chatData : ChatData? = null

    var chatDataList: List<ChatData>? = null

    fun webUrl() = chatData?.text

    fun isWebUrl(): Boolean {
        return chatData?.text?.isUrl() ?: false
    }

    fun buildRecDataList(): String {
        return chatData?.text ?:""
//        val sb = StringBuilder()
//        chatDataList?.forEach {
//
//            sb.append(it)
//            if (it.isSelf) {
//                sb.append(":PP;")
//            }
//            sb.append("\n\r")
//        }
//
//        return sb.toString()
    }

}

private fun String.isUrl(): Boolean {
    val regex = Regex("""^(https?|ftp)://[^\s/$.?#].[^\s]*$""")
    return regex.matches(this)
}

@Serializable
data class ChatData(
    val text: String,
    val isSelf: Boolean
)