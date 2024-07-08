package com.frpc.common.pages.server

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frpc.common.def.MAIN
import com.frpc.common.def.Router
import com.frpc.common.common.CommonPageContext
import com.frpc.common.common.CommonSection
import com.frpc.common.common.Configuration
import com.frpc.common.common.PageCtx
import com.frpc.common.common.SpacerEx
import com.frpc.common.common.SshSection
import com.frpc.common.getFrpcVersion
import com.frpc.common.pages.tunnel.AddTunnelPage
import com.oldguy.common.io.File
import moe.tlaster.precompose.viewmodel.viewModel

@Composable
fun AddServicePage() {
    val pageCtx = CommonPageContext()

    var configurationData by remember {
        mutableStateOf(
            Configuration(
                CommonSection("", "", "", "", "", "", "", ""),
                SshSection("", "", "", "", ""),
            )
        )
    }

    var loginToken by remember { mutableStateOf("") }
    var addTunnelShow by remember { mutableStateOf(false) }
    val viewModel = viewModel(AddServerViewModel::class) {
        AddServerViewModel()
    }
    var serverAddress by remember { mutableStateOf("") }
    var serverPort by remember { mutableStateOf("") }

    val launchState by remember { mutableStateOf(1) }
    LaunchedEffect(launchState) {
        val configuration = viewModel.initConfig()
        configurationData = configuration
        serverAddress = configuration.common.serverAddr
        serverPort = configuration.common.serverPort
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeContent)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpacerEx(30)
        Text("添加服务器", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        SpacerEx(40)
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            value = serverAddress,
            onValueChange = {
                serverAddress = it
                configurationData?.common?.serverAddr = it
            },
            label = { Text("服务器地址") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp),
        )
        SpacerEx(5)
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            value = serverPort,
            onValueChange = {
                serverPort = it
                configurationData.common.serverPort = it
            },
            label = { Text("服务器端口号") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp)
        )
        SpacerEx(5)
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            value = loginToken,
            onValueChange = {
                loginToken = it
                configurationData.common.token = it
            },
            label = { Text("登录token") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp)
        )
        SpacerEx(10)
        Row(
            modifier = Modifier.fillMaxWidth().clickable {
                addTunnelShow = !addTunnelShow
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpacerEx(40)
            Text("添加隧道")
            SpacerEx(5)
            Canvas(modifier = Modifier.size(15.dp)) {
                val rotate = if (addTunnelShow) {
                    0f
                } else {
                    180f
                }
                rotate(degrees = rotate) {
                    val path = Path().apply {
                        moveTo(size.width / 2, size.height)
                        lineTo(0f, 0f)
                        lineTo(size.width, 0f)
                        close()
                    }
                    drawPath(path, Color.Black, style = Stroke(width = 3f))
                }
            }
        }

        if (addTunnelShow && configurationData?.ssh != null) {
            AddTunnelPage(configurationData.ssh)
        } else {
            Spacer(Modifier.size(10.dp).weight(1f))
        }

        Button(
            onClick = {
                val data = configurationData
                if (checkConfigurationData(data, pageCtx)) {
                    viewModel.addServer(data) {
                        Router.navigateTo(MAIN)
                    }
                }
            },
            modifier = Modifier
                .padding(40.dp)
                .fillMaxWidth(),
        ) {
            Text("确认添加", modifier = Modifier.padding(vertical = 10.dp))
        }

        Spacer(Modifier.size(10.dp).weight(1f))

        Text("frp kemel : ${getFrpcVersion()}", modifier = Modifier.padding(20.dp))
    }
}

private fun checkConfigurationData(configuration: Configuration, pageCtx: PageCtx): Boolean {
    runCatching {
        val serverPort = configuration.common.serverPort
        val serverAddress = configuration.common.serverAddr
        val token: String = configuration.common.token
        if (serverPort.isNotEmpty()) {
            val port = serverPort.toInt()
            if (serverAddress.length > 2 && 65535 >= port && port >= 1024 && token.length > 2) {
                return true
            } else {
                pageCtx.toast.show("输入有误")
                return false
            }
        }
    }.onFailure {
        pageCtx.toast.show("输入有误")
    }
    return false
}

fun fileReader(filePath: String) {
    val directory = File(filePath)
    val exists = directory.exists
    val dir = directory.path
    val isDir = directory.isDirectory
    val full = directory.fullPath
    val files = directory.listFilesTree
    for (file in files) {
        println(file.name)
    }
}