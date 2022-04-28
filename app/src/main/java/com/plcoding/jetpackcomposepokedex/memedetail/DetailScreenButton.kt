package com.plcoding.jetpackcomposepokedex.memedetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailScreenButton(
    onPreviewClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(4.dp)
    ) {
        Button(
            onClick = onPreviewClick,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.5f)
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
            elevation = ButtonDefaults.elevation(4.dp)
        ) {
            Icon(
                Icons.Default.Preview,
                contentDescription = "Preview",
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = MaterialTheme.colors.onBackground
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text ="Preview", color = MaterialTheme.colors.onBackground)
        }
        Button(
            onClick = {onSaveClick() },
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(4.dp),
            contentPadding = PaddingValues(
                start = 4.dp,
                top = 2.dp,
                end = 4.dp,
                bottom = 2.dp
            ),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onBackground),
            elevation = ButtonDefaults.elevation(4.dp)
        ) {
            Icon(
                Icons.Default.Save,
                contentDescription = "Save",
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = MaterialTheme.colors.background
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = "Save".uppercase(), color = MaterialTheme.colors.background)
        }
    }
}