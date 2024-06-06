package com.frpc.common

import kotlinx.serialization.json.JsonObject

public expect fun getPlatformName(): String
public expect fun getFrpcVersion(): String
public expect fun updateIniFile(filePath:String, json:JsonObject): Int