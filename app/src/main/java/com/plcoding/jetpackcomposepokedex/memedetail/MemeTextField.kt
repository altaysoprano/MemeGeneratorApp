package com.plcoding.jetpackcomposepokedex.memedetail

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
    boxCount: Int
) {

    val textList = viewModel.textList
    
    LazyColumn(
        modifier = Modifier.fillMaxHeight(0.7f)
    ) {
        items(count = boxCount) { index ->
            TextField(
                value = textList.value[index],
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                ,
                onValueChange = {
                    viewModel.setText(it, index)
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                placeholder = {
                    Text("Type for Text ${index+1}", color = MaterialTheme.colors.primary)
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onBackground,
                    backgroundColor = MaterialTheme.colors.background,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }
    }
}