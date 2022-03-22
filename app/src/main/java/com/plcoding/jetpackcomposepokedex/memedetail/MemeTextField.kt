package com.plcoding.jetpackcomposepokedex.memedetail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MemeTextField(
    viewModel: MemeDetailViewModel = hiltViewModel(),
) {

    val textList = viewModel.textList

    TextField(
        value = textList[0],
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ,
        onValueChange = {
            viewModel.setText0(it)
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        placeholder = {
            Text("Type for first text", color = MaterialTheme.colors.primary)
        },
    )
}