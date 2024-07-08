package com.frpc.common.pages.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.viewmodel.viewModel

@Composable
public fun SplashPage() {
    val viewModel = viewModel(SplashViewModel::class) {
        SplashViewModel()
    }
    Surface {
        Box(modifier = Modifier.fillMaxSize()) {

        }
    }
}