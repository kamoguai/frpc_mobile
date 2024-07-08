package com.frpc.common.bean

import androidx.compose.runtime.mutableStateOf
import com.frpc.common.common.SshSection
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerInfoBean(
    @SerialName("name")
    val name: String,
    @SerialName("remoteAddress")
    val remoteAddress: String,
    @SerialName("remotePort")
    val remotePort: String,
    @SerialName("tunnel")
    val sshSection: SshSection,
) {

    var isSelectState = mutableStateOf(false)

    var isSelect: Boolean
        set(value) {
            isSelectState.value = value
        }
        get() {
            return isSelectState.value
        }


    var isStartState = mutableStateOf(false)

    var isStart: Boolean
        set(value) {
            isStartState.value = value
        }
        get() {
            return isStartState.value
        }



    var chatData: ChatData? = null

    var chatDataList: List<ChatData>? = null

    fun webUrl() = chatData?.text

    fun isWebUrl(): Boolean {
        return chatData?.text?.isUrl() ?: false
    }

    fun buildRecDataList(): String {
        return chatData?.text ?: ""
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

    private fun String.isUrl(): Boolean {
        val regex = Regex("""^(https?|ftp)://[^\s/$.?#].[^\s]*$""")
        return regex.matches(this)
    }
}