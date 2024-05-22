package com.frpc.common

import androidx.compose.runtime.Composable

public actual fun getPlatformName(): String {
    return "demo"
}

actual fun getFrpcVersion(): String {
    return "Frpclib Version()!!"
}

@Composable
public fun UIShow() {
    App()
}