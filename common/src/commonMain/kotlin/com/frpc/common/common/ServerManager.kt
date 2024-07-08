package com.frpc.common.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object ServerManager {

    private const val LOCAL_KEY_SERVER = "local_key_config"

    private val _localConfigFlow = MutableSharedFlow<List<Configuration>>()
    private var mLocalConfigCache = arrayListOf<Configuration>()
    val localConfigFlow: Flow<List<Configuration>> = _localConfigFlow

    private val mScope = CoroutineScope(Dispatchers.Default)

    fun getLocalConfig() = mLocalConfigCache.toMutableList()

    fun initLocalServerConfig() {
        mScope.launch {
            val localConfig = LocalStorage.getDefault().getStringOrNull(LOCAL_KEY_SERVER)
            if (!localConfig.isNullOrEmpty()) {
                val configurations = KsJsonUtils.decodeFromString<List<Configuration>>(localConfig)
                mLocalConfigCache = ArrayList(configurations)
                _localConfigFlow.tryEmit(configurations)
            }
        }
    }

    suspend fun addConfig(configuration: Configuration) {
        withContext(Dispatchers.Default) {
            val result = mLocalConfigCache.find {
                it.common.serverAddr == configuration.common.serverAddr && it.common.serverPort == configuration.common.serverPort
            }
            if (result == null) {
                mLocalConfigCache.add(0, configuration)
                LocalStorage.getDefault().putString(
                    LOCAL_KEY_SERVER, KsJsonUtils.encodeToString(
                        mLocalConfigCache
                    )
                )
            }
        }
    }

}