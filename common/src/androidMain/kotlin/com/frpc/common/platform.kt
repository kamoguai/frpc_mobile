package com.frpc.common

import androidx.compose.runtime.Composable
import frpclib.Frpclib
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
public actual fun getPlatformName(): String {
    return "Android"
}

actual fun getFrpcVersion(): String {
    return Frpclib.getVersion()
}

actual  fun updateIniFile(filePath:String, json:JsonObject) : Int {
    return 0
}


@Composable
public fun UIShow() {
    App()
}