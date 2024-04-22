package com.frpc.common

import androidx.compose.runtime.Composable

public actual fun getPlatformName(): String {
    return "demo"
}

@Composable
public fun UIShow() {
    App()
}