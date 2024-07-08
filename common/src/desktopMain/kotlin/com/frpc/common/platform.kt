package com.frpc.common

import androidx.compose.runtime.Composable
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import kotlinx.serialization.json.JsonObject
import java.util.prefs.Preferences

public actual fun getPlatformName(): String {
    return "demo"
}

actual fun getFrpcVersion(): String {
    return "Frpclib Version()!!"
}

actual fun updateIniFile(
    filePath: String,
    json: JsonObject
): Int {
    TODO("Not yet implemented")
}

actual fun createSettingFactory(name: String): Settings {
    return PreferencesSettings(Preferences.userRoot())
}

actual fun getRandom():Int {
    val random = (0..100).random()
    return random
}

@Composable
public fun UIShow() {
    App()
}