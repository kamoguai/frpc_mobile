package com.frpc.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import kotlinx.serialization.json.JsonObject
import platform.UIKit.UIViewController
import platform.UIKit.UIDevice
import platform.Foundation.NSUserDefaults

public actual fun getPlatformName(): String {
    return "iOS"
}

actual fun getFrpcVersion(): String {
    return com.frpclib.FrpclibGetVersion()
}

actual fun updateIniFile(
    filePath: String,
    json: JsonObject
): Int {
    TODO("Not yet implemented")

}

actual fun createSettingFactory(name: String): Settings {
    return NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults())
}

@Composable
private fun UIShow() {
    App()
}

public fun MainViewController(): UIViewController = ComposeUIViewController {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                UIShow()
            }
        }
    }
}