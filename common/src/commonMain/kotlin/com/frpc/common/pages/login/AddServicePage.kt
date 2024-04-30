package com.frpc.common.pages.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frpc.common.ADD_TUNNEL
import com.frpc.common.MAIN
import com.frpc.common.Router
import com.frpc.common.common.Constants
import com.frpc.common.common.SpacerEx
import com.frpc.common.getFrpcVersion

@Composable
public fun AddServer() {
    var serverAddress by remember { mutableStateOf("") }
    var serverPort by remember { mutableStateOf("") }
    var loginToken by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpacerEx(30)
        Text("添加服务器", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        SpacerEx(40)
        OutlinedTextField(
            value = serverAddress,
            onValueChange = { serverAddress = it },
            label = { Text("服务器地址") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp)
        )
        SpacerEx(5)
        OutlinedTextField(
            value = serverPort,
            onValueChange = { serverPort = it },
            label = { Text("服务器端口号") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp)
        )
        SpacerEx(5)
        OutlinedTextField(
            value = loginToken,
            onValueChange = { loginToken = it },
            label = { Text("登录token") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp)
        )
        SpacerEx(10)
        Row(modifier = Modifier.fillMaxWidth()) {
            SpacerEx(40)
            Text("添加隧道", modifier = Modifier.clickable {
                Router.navigateTo(ADD_TUNNEL)
            })
        }
        Button(
            onClick = {
                Router.navigateTo(MAIN)
            },
            modifier = Modifier
                .padding(40.dp)
                .fillMaxWidth(),
        ) {
            Text("确认添加", modifier = Modifier.padding(vertical = 10.dp))
        }

        Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
//            Text("frp kemel:${Constants.APP_VERSION}", modifier = Modifier.padding(20.dp))
            Text("frp kemel : ${getFrpcVersion()}", modifier = Modifier.padding(20.dp))
        }
    }
}