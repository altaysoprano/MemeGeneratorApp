package com.plcoding.jetpackcomposepokedex.memedetail

import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ShareScreen(bitmap: Bitmap) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra("BitmapImage", bitmap)
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val context = LocalContext.current

    Button(onClick = {
        context.startActivity(shareIntent)
    }){
        Text("Share")
    }
}