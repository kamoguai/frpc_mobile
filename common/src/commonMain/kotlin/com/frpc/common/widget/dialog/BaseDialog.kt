package com.frpc.common.widget.dialog

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

open class ComposeDialogController {

    var dismissOnBackPress: Boolean = true
    var dismissOnClickOutside: Boolean = true
    val dialogSwitch: MutableState<Boolean> = mutableStateOf(false)

    fun showDialog() {
        dialogSwitch.value = true
    }

}