package com.plcoding.jetpackcomposepokedex.memelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.jetpackcomposepokedex.R

@Composable
fun NoConnectionScreen(
    onTryAgain : () -> Unit,
    titleText: String,
    titleSize: Int,
    textSize: Int,
    icon: Int,
    buttonText: String
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "No Internet Connection",
                modifier = Modifier.fillMaxSize(0.25f)
            )
            Text(text = titleText, fontWeight = FontWeight.Bold, fontSize = titleSize.sp)
            Text(
                text = "No internet connection found. Check your internet",
                fontSize = textSize.sp,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp)
            )
            Text(text = "connection or try again.", fontSize = textSize.sp)
            Button(
                onClick = { onTryAgain() },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                elevation = ButtonDefaults.elevation(4.dp)
            ) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = buttonText,
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                    tint = MaterialTheme.colors.onBackground
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text ="Try Again", color = MaterialTheme.colors.onBackground)
            }
        }
    }
}