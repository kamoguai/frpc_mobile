package com.frpc.common.pages.main

import com.frpc.common.bean.ServerInfoBean
import com.frpc.common.common.ServerManager
import moe.tlaster.precompose.viewmodel.ViewModel

class MainViewModel : ViewModel() {

    fun getServerConfig() = ServerManager.getLocalConfig().map {
        ServerInfoBean(
            it.common.serverAddr,
            it.common.serverAddr,
            it.common.serverPort,
            it.ssh
        )
    }

    fun startFrPC(serverInfoBean: ServerInfoBean, callback: (Boolean) -> Unit) {
        //todo
        callback(true)
    }

    fun stopFrpc(serverInfoBean: ServerInfoBean,callback: (Boolean) -> Unit){
        //todo
        callback(true)
    }
}