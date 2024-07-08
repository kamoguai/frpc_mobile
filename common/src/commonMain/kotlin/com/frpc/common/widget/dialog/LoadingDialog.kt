package com.frpc.common.widget.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun LoadingDialog(controller: ComposeDialogController) {
    val showDialog = remember { controller.dialogSwitch }

    if (showDialog.value) {
        Dialog(
            onDismissRequest = {
                showDialog.value = false
            },
            properties = DialogProperties(
                dismissOnBackPress = controller.dismissOnBackPress,
                dismissOnClickOutside = controller.dismissOnClickOutside
            ),
            content = {
                Box(
                    modifier = Modifier.size(100.dp)
                        .background(Color.White, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {

                    CircularProgressIndicator(modifier = Modifier.size(75.dp))
                }
            }
        )
    }

}