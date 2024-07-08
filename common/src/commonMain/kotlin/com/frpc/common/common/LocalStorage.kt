package com.frpc.common.common

import com.frpc.common.createSettingFactory
import com.russhwolf.settings.Settings

class DefaultStorage(
    val defaultSetting: Settings = createSettingFactory("default")
) : Settings by defaultSetting


object LocalStorage {

    private val defaultSetting by lazy { DefaultStorage() }

    fun getDefault() = defaultSetting

}