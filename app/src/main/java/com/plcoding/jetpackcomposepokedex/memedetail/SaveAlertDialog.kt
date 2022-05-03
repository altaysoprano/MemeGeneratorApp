package com.plcoding.jetpackcomposepokedex.memedetail

import androidx.compose.material.*
import androidx.compose.runtime.Composable

@Composable
fun SaveAlertDialog(
    saveState: SaveState,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    if(saveState.isLoading) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary,
        )
    }
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
