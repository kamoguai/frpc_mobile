package com.frpc.common.common

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


 object KsJsonUtils {

    public val json: Json by lazy {
        Json {
            isLenient = true
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    public inline fun <reified T> encodeToString(obj: T): String {
        return json.encodeToString(obj)
    }

    public inline fun <reified T> decodeFromString(jsonStr: String): T {
        return json.decodeFromString(jsonStr)
    }

}