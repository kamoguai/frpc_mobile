package com.frpc.common

import android.content.Context
import androidx.compose.runtime.Composable
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
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

actual fun createSettingFactory(name: String): Settings {
    val sp = AndroidBaseEnv.context().getSharedPreferences(name, Context.MODE_PRIVATE)
    return SharedPreferencesSettings(sp)
}

@Composable
public fun UIShow() {
    App()
}