package com.plcoding.jetpackcomposepokedex.memedetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SaveAlertDialog(
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    onShare: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        dismissButton = {
            Button(
                onClick = { onShare() },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                modifier = Modifier.fillMaxSize().padding(vertical = 8.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text(text = "Share")
            }
        },
        confirmButton = {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { onDismiss() },
                    modifier = Modifier.fillMaxSize(0.5f).padding(horizontal = 4.dp, vertical = 8.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Text(text = "Cancel")
                }
                Button(
                    onClick = { onSave() },
                    modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp, vertical = 8.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Text(text = "SAVE")
                }
            }
        },
        title = { Text(text = "Save Meme") },
        text = { Text(text = "Do you want to save it to your gallery?") }
    )
}
