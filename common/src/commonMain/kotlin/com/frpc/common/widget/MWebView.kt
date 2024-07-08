package com.frpc.common.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState


@Composable
fun MWebView(
    url: String, modifier: Modifier = Modifier
) {
    val webViewState = rememberWebViewState(url)
    webViewState.webSettings.apply {
        isJavaScriptEnabled = true
        customUserAgentString =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_1) AppleWebKit/625.20 (KHTML, like Gecko) Version/14.3.43 Safari/625.20"
        androidWebSettings.apply {
            isAlgorithmicDarkeningAllowed = true
            safeBrowsingEnabled = false
        }

    }

    WebView(webViewState, modifier = modifier)
}