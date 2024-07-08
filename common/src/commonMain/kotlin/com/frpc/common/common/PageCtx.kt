package com.frpc.common.common

import androidx.compose.runtime.Composable
import com.dokar.sonner.Toaster
import com.dokar.sonner.ToasterState
import com.dokar.sonner.rememberToasterState
import com.frpc.common.widget.dialog.ComposeDialogController
import com.frpc.common.widget.dialog.LoadingDialog

class PageCtx(
    val loading: ComposeDialogController = ComposeDialogController(),
    val toast: ToasterState
) {

    fun showToast(msg: String?) {
        if (!msg.isNullOrEmpty()) {
            toast.show(msg)
        }
    }

    fun showLoading() {
        if (!loading.dialogSwitch.value){
            loading.showDialog()
        }
    }

    fun hideLoading() {
        loading.dialogSwitch.value = false
    }
}


@Composable
fun CommonPageContext(): PageCtx {
    val toaster = rememberToasterState()
    val loadingController = ComposeDialogController()

    Toaster(toaster)
    LoadingDialog(loadingController)

    return PageCtx(loadingController, toaster)
}