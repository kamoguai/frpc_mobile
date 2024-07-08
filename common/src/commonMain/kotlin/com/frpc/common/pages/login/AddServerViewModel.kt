package com.frpc.common.pages.login

import com.frpc.common.common.Configuration
import com.frpc.common.common.LocalConfigManager
import com.frpc.common.common.ServerManager
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class AddServerViewModel : ViewModel() {

//    private val _configFlow = MutableStateFlow<TunnelDataBean?>(null)
//    private val _commonSectionFlow = MutableStateFlow<CommonSection?>(null)
//
//    @Composable
//    fun sshConfigData() = _configFlow.collectAsState(null, viewModelScope.coroutineContext)
//
//    @Composable
//    fun commonConfigData() =
//        _commonSectionFlow.collectAsState(null, viewModelScope.coroutineContext)

    suspend fun initConfig() = LocalConfigManager.loadInitConfig()

    fun addServer(configuration: Configuration, callback: () -> Unit) {
        viewModelScope.launch {
            ServerManager.addConfig(configuration)
            callback()
        }
    }

}