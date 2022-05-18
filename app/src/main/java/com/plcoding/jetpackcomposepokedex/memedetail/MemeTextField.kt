package com.plcoding.jetpackcomposepokedex.memedetail

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@ExperimentalComposeUiApi
@Composable
fun MemeTextField(
    viewModel: MemeDetailViewModel = hiltViewModel(),
    boxCount: Int
) {
    val textList = viewModel.textList
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn(
        modifier = Modifier.fillMaxHeight(0.7f)
    ) {
        items(count = boxCount) { index ->
            TextField(
                value = textList.value[index],
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onValueChange = {
                    viewModel.setText(it, index)
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                placeholder = {
                    Text("Type for Text ${index + 1}", color = MaterialTheme.colors.primary)
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onBackground,
                    backgroundColor = MaterialTheme.colors.background,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    if (textList.value[index].isNotBlank())
                        IconButton(onClick = {
                            viewModel.setText("", index)
                        }) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Clear",
                                modifier = Modifier.size(ButtonDefaults.IconSize),
                                tint = MaterialTheme.colors.onBackground
                            )
                        }
                }
            )
        }
    }
}