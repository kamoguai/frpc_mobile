package com.frpc.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.frpc.common.common.ServerManager
import com.frpc.common.def.ADD_SERVER
import com.frpc.common.def.MAIN
import com.frpc.common.def.Router
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
internal fun App() {
    val init = remember { doAppInit() }

    PreComposeApp {
        MaterialTheme {
            val navigator = rememberNavigator()
            Router.initNavigation(navigator)
            NavHost(
                navigator = navigator,
                initialRoute = ADD_SERVER.route
            ) {
//                SPLASH.registerRoute(this)
                ADD_SERVER.registerRoute(this)
                MAIN.registerRoute(this)
            }
        }
    }
}

private fun doAppInit(){
    ServerManager.initLocalServerConfig()
}