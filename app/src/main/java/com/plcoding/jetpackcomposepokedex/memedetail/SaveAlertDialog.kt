package com.plcoding.jetpackcomposepokedex.memedetail

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun SaveAlertDialog(
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = { onSave() }) {
                Text(text = "SAVE")
            }
        },
        title = { Text(text = "Save Meme") },
        text = { Text(text = "Do you want to save it to your gallery?") }
    )
}
