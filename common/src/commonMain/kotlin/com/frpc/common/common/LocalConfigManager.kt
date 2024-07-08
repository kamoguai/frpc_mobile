package com.frpc.common.common

import com.frpc.common.generated.resources.Res
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
object LocalConfigManager {

    suspend fun loadInitConfig(): Configuration {
        val configuration = withContext(Dispatchers.IO) {
            val bytes = Res.readBytes("files/config.ini")
            val configString = bytes.decodeToString()
            parseConfiguration(configString)
        }
        return configuration!!
    }

    private fun parseConfiguration(configString: String): Configuration? {
        val sections = HashMap<String, MutableMap<String, String>>()
        var currentSection = ""

        configString.lineSequence().forEach { line ->
            // 忽略空行和注释行
            if (line.isBlank() || line.startsWith("#")) return@forEach

            // 检查节
            if (line.startsWith("[") && line.endsWith("]")) {
                currentSection = line.substring(1, line.length - 1).trim()
                sections[currentSection] = HashMap()
                return@forEach
            }

            // 检查键值对
            val keyValueParts = line.split("=", limit = 2)
            if (keyValueParts.size == 2) {
                val key = keyValueParts[0].trim()
                val value = keyValueParts[1].trim()
                sections[currentSection]?.put(key, value)
            }
        }

        // 创建并填充CommonSection和SshSection
        val commonSection = sections["common"]?.let { commonMap ->
            CommonSection(
                serverAddr = commonMap["server_addr"] ?: return null,
                serverPort = commonMap["server_port"] ?: return null,
                token = commonMap["token"] ?: return null,
                type = commonMap["type"] ?: return null,
                protocol = commonMap["protocol"] ?: return null,
                logFile = commonMap["log_file"] ?: return null,
                logLevel = commonMap["log_level"] ?: return null,
                logMaxDays = commonMap["log_max_days"] ?: return null
            )
        }

        val sshSection = sections["ssh"]?.let { sshMap ->
            SshSection(
                name = sshMap["name"] ?: return null,
                type = sshMap["type"]?.removeSurrounding("\"") ?: return null, // 假设type可能被引号包围
                localIp = sshMap["local_ip"] ?: return null,
                localPort = sshMap["local_port"] ?: return null,
                remotePort = sshMap["remote_port"] ?: return null
            )
        }

        // 返回Configuration对象，如果任何部分为空则返回null
        return if (commonSection != null && sshSection != null) {
            Configuration(common = commonSection, ssh = sshSection)
        } else {
            null
        }
    }

}


@Serializable
data class Configuration(
    @SerialName("common") val common: CommonSection,
    @SerialName("ssh") val ssh: SshSection
)

@Serializable
data class CommonSection(
    @SerialName("server_addr") var serverAddr: String,
    @SerialName("server_port") var serverPort: String,
    @SerialName("token") var token: String,
    @SerialName("type") var type: String,
    @SerialName("protocol") var protocol: String,
    @SerialName("log_file") var logFile: String,
    @SerialName("log_level") var logLevel: String,
    @SerialName("log_max_days") var logMaxDays: String
)

@Serializable
data class SshSection(
    @SerialName("name") var name: String,
    @SerialName("type") var type: String,
    @SerialName("local_ip") var localIp: String,
    @SerialName("local_port") var localPort: String,
    @SerialName("remote_port") var remotePort: String,
    @SerialName("is_encrypt") var isEncrypt: Boolean = false,
    @SerialName("is_compress") var isCompress: Boolean = false
)