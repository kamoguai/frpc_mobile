package com.frpc.common.pages.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.frpc.common.bean.ServerInfoBean
import com.frpc.common.common.Center
import com.frpc.common.common.SpacerEx
import com.frpc.common.widget.MWebView

@Composable
fun ServerInfoItem(bean: ServerInfoBean) {
    val isSelect = remember { bean.isSelectState }
    val isStart = remember { bean.isStartState }


    var m = Modifier.clickable {
        bean.isSelect = !bean.isSelect
    }
    if (isSelect.value) {
        m = m.border(5.dp, Color.Yellow)
    }
    Column {
        Row(
            modifier = m,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.padding(15.dp)) {
                val tunnelDataBean = bean.sshSection
                Text(bean.name)
                SpacerEx(5)
                val type = tunnelDataBean.type
                Text("$type:${bean.remoteAddress}")
                SpacerEx(5)
                Text("远端端口号:${bean.remotePort}")
            }

            Center(m = Modifier.weight(1f)) {
                if (isStart.value) {
                    StateDot(Color.Green)
                } else {
                    StateDot(Color.Red)
                }
            }

            Box(
                modifier = Modifier.weight(2f).padding(vertical = 20.dp, horizontal = 15.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    onClick = {

                    },
                    modifier = Modifier.width(150.dp)
                ) {
                    Text("复制/分享", color = Color.White)
                }
            }
        }


        if (isStart.value) {
            SpacerEx(15)
            if (bean.isWebUrl()) {
                MWebView(url = bean.webUrl()!!)
            } else {
                Text(bean.chatData?.text ?: "")
            }

            var sendValue by remember { mutableStateOf(TextFieldValue()) }
            TextField(
                value = sendValue,
                onValueChange = { sendValue = it },
                maxLines = 5,
            )
            SpacerEx(15)

            Row(modifier = Modifier.fillMaxWidth()) {
                SpacerEx(15)
                Text("发送", modifier = Modifier.weight(1f))
                SpacerEx(15)
                Text("载入", modifier = Modifier.weight(1f))
                SpacerEx(15)
            }


        }
    }
}

@Composable
private fun StateDot(color: Color) {
    Box(
        modifier = Modifier
            .size(10.dp)
            .background(color, RoundedCornerShape(10.dp)) // 圆点颜色
    )
}