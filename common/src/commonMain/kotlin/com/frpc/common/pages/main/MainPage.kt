package com.frpc.common.pages.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.frpc.common.bean.ServerInfoBean
import com.frpc.common.bean.TunnelDataBean
import com.frpc.common.common.SpacerEx
import com.multiplatform.webview.web.WebContent


@Composable
fun MainPage() {
    var dataList = remember {
        mutableStateOf(
            arrayListOf(
                ServerInfoBean(
                    "test1", "8.8.8.8", 8888, TunnelDataBean(
                        "TCP", false, false, "", "127.0.0.1", 3306, 3306
                    )
                ).apply {
                    isSelect = true
                },
                ServerInfoBean(
                    "test2", "8.8.8.8", 8888, TunnelDataBean(
                        "HTTP", false, false, "", "127.0.0.1", 3306, 3306
                    )
                )
            )
        )
    }

    Column {
        //TITLE
        Row(
            modifier = Modifier.fillMaxWidth().padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("添加", color = Color.Blue, modifier = Modifier.clickable {

            })
            SpacerEx(25)
            Text("编辑", color = Color.Blue, modifier = Modifier.clickable {

            })
            Text("", modifier = Modifier.weight(1f))

            Text("启动", color = Color.Black, modifier = Modifier.clickable {
                val list = dataList.value
                list.forEach {
                    if (it.isSelect) {
                        it.isStart = true
                    }
                }
                dataList.value = list
            })
            SpacerEx(5)
            Text("停止", color = Color.Black, modifier = Modifier.clickable {
                val list = dataList.value
                list.forEach {
                    if (it.isSelect) {
                        it.isStart = false
                    }
                }
                dataList.value = list
            })
            SpacerEx(5)
            Text("全部启动", color = Color.Black, modifier = Modifier.clickable {

            })
            SpacerEx(5)
            Text("全部停止", color = Color.Black, modifier = Modifier.clickable {

            })
            SpacerEx(5)
        }

        val isEditMode = remember { mutableStateOf(false) }


        if (isEditMode.value) {
            // ?
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(dataList.value) {
                    ServerInfoItem(it)
                }
            }
        }
    }
}

