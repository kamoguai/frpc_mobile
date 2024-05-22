package com.frpc.common.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
public fun SpacerEx(size: Int): Unit = Spacer(Modifier.size(size.dp))

@Composable
public fun Dp.toPxValue() {
    val density = LocalDensity.current
    return with(density) {
        this@toPxValue.toPx()
    }
}