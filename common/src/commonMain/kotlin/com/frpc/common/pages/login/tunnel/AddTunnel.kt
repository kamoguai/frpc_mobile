package com.frpc.common.pages.login.tunnel

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.frpc.common.bean.ConnectionType
import com.frpc.common.common.SpacerEx
import com.frpc.common.common.SshSection

@Composable
fun AddTunnelPage(data: SshSection) {
    Column {
        val mPageData = remember { data }

        ConnectionTypeSelection {
            mPageData.type = it.name
        }

        EncryptionCompressionCheckbox(mPageData)

        InputLayout("隧道名称  ", "请填写隧道名称", mPageData.name) {
            mPageData.name = it
        }
        InputLayout("局域网地址 ", "请填写局域网地址", mPageData.localIp) {
            mPageData.localIp = it
        }
        InputLayout("局域网端口号", "请填写局域网端口号", mPageData.localPort.toString()) {
            runCatching {
                mPageData.localPort = it
            }
        }
        InputLayout("远程端口号 ", "请填写远程端口号", mPageData.remotePort.toString()) {
            runCatching {
                mPageData.remotePort = it
            }
        }
    }
}

@Composable
private fun EncryptionCompressionCheckbox(pageData: SshSection) {
    var isEncryptionChecked by remember { mutableStateOf(false) }
    var isCompressionChecked by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SpacerEx(15)
        Text(text = "选项")
        SpacerEx(10)
        Checkbox(
            checked = isEncryptionChecked,
            onCheckedChange = {
                isEncryptionChecked = it
                pageData.isCompress = it
            }
        )
        Text(text = "加密")
        SpacerEx(30)
        Checkbox(
            checked = isCompressionChecked,
            onCheckedChange = {
                isCompressionChecked = it
                pageData.isEncrypt = it
            }
        )
        Text(text = "压缩")
    }
}


@Composable
private fun ConnectionTypeSelection(onConnectionTypeSelected: (ConnectionType) -> Unit) {
    var selectedConnectionType by remember { mutableStateOf(ConnectionType.TCP) }
    Row(
        modifier = Modifier.padding(15.dp).horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("类型")
        SpacerEx(5)
        ConnectionTypeRadioButton(
            text = "TCP",
            selectedConnectionType = selectedConnectionType,
            connectionType = ConnectionType.TCP,
            onConnectionTypeSelected = {
                selectedConnectionType = it
                onConnectionTypeSelected(it)
            }
        )
        ConnectionTypeRadioButton(
            text = "UDP",
            selectedConnectionType = selectedConnectionType,
            connectionType = ConnectionType.UDP,
            onConnectionTypeSelected = {
                selectedConnectionType = it
                onConnectionTypeSelected(it)
            }
        )
        ConnectionTypeRadioButton(
            text = "HTTP",
            selectedConnectionType = selectedConnectionType,
            connectionType = ConnectionType.HTTP,
            onConnectionTypeSelected = {
                selectedConnectionType = it
                onConnectionTypeSelected(it)
            }
        )
        ConnectionTypeRadioButton(
            text = "HTTPS",
            selectedConnectionType = selectedConnectionType,
            connectionType = ConnectionType.HTTPS,
            onConnectionTypeSelected = {
                selectedConnectionType = it
                onConnectionTypeSelected(it)
            }
        )
    }
}

@Composable
private fun ConnectionTypeRadioButton(
    text: String,
    selectedConnectionType: ConnectionType,
    connectionType: ConnectionType,
    onConnectionTypeSelected: (ConnectionType) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selectedConnectionType == connectionType,
            onClick = { onConnectionTypeSelected(connectionType) }
        )
        Text(text = text)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputLayout(
    name: String,
    hint: String,
    initValue: String = "",
    onValueChange: (String) -> Unit
) {
    var value by remember { mutableStateOf(TextFieldValue(initValue)) }
    Row(
        modifier = Modifier.padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = name)
        SpacerEx(5)
        TextField(
            value = value,
            onValueChange = {
                value = it
                onValueChange(it.text)
            },
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.Transparent
            ),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
            placeholder = { Text(hint, textAlign = TextAlign.End, color = Color.Gray) }
        )
    }
}