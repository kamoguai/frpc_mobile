package com.frpc.common

import androidx.compose.runtime.Composable
import frpclib.Frpclib
public actual fun getPlatformName(): String {
    return "Android"
}

actual fun getFrpcVersion(): String {
    return Frpclib.getVersion()
}

@Composable
public fun UIShow() {
    App()
}