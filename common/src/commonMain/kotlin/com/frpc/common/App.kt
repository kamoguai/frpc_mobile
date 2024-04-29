package com.frpc.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
internal fun App() {
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
                ADD_TUNNEL.registerRoute(this)
                MAIN.registerRoute(this)
            }
        }
    }
}