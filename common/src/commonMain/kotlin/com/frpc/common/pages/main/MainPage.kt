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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.frpc.common.Router
import com.frpc.common.common.CommonPageContext
import com.frpc.common.common.PageCtx
import com.frpc.common.common.SpacerEx
import moe.tlaster.precompose.viewmodel.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage() {
    val viewModel = viewModel(MainViewModel::class) {
        MainViewModel()
    }

    val pageCtx = CommonPageContext()

    var dataList = remember {
        mutableStateOf(viewModel.getServerConfig())
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
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
                                pageCtx.showLoading()
                                viewModel.startFrPC(it) { result ->
                                    pageCtx.hideLoading()
                                    it.isStart = true
                                    it.isSelect = false
                                }
                            }
                        }
                        dataList.value = list
                    })
                    SpacerEx(5)
                    Text("停止", color = Color.Black, modifier = Modifier.clickable {
                        val list = dataList.value
                        list.forEach {
                            if (it.isSelect) {
                                pageCtx.showLoading()
                                viewModel.stopFrpc(it) { result ->
                                    pageCtx.hideLoading()
                                    it.isStart = false
                                    it.isSelect = false
                                }
                            }
                        }
                        dataList.value = list
                    })
//                    SpacerEx(5)
//                    Text("全部启动", color = Color.Black, modifier = Modifier.clickable {
//
//                    })
//                    SpacerEx(5)
//                    Text("全部停止", color = Color.Black, modifier = Modifier.clickable {
//
//                    })
                    SpacerEx(5)
                }

            }, navigationIcon = {
                IconButton(onClick = { Router.popback() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            })
        }
    ) { paddingValue ->
        Column(modifier = Modifier.padding(paddingValue)) {
            val isEditMode = remember { mutableStateOf(false) }

            if (isEditMode.value) {
                // ?
            } else {
                val nestedScrollDispatcher = NestedScrollDispatcher()
                val nestedScrollConnection = object : NestedScrollConnection {}

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .nestedScroll(nestedScrollConnection, nestedScrollDispatcher),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(dataList.value) {
                        ServerInfoItem(it)
                    }
                }
            }
        }
    }
}

