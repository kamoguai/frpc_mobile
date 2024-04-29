package com.frpc.common.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
public fun localResPainter(res: String): Painter {
    return painterResource(DrawableResource(res))
}

@Composable
public fun SpacerEx(size: Int): Unit = Spacer(Modifier.size(size.dp))



@Composable
public fun Dp.toPxValue() {
    val density = LocalDensity.current
    return with(density) {
        this@toPxValue.toPx()
    }
}