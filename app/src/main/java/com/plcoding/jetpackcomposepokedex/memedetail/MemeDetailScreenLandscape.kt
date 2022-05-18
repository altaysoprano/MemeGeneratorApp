package com.plcoding.jetpackcomposepokedex.memedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@ExperimentalComposeUiApi
@Composable
fun MemeDetailScreenLandscape(
    navController: NavController,
    memeInfo: MemeInfoState,
    boxCount: Int,
    onTryAgain: () -> Unit,
    onPreviewClick: () -> Unit,
    onSaveClick: () -> Unit,
    isFailed: Boolean
) {

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colors.background,
                        Color.Transparent
                    )
                )
            )
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        MemeDetailTopSection(navController = navController)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focusManager.clearFocus()
                        }
                    )
                }
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                MemeDetailStateWrapper(
                    memeInfo = memeInfo,
                    onTryAgain = onTryAgain
                )
            }
            Column(modifier = Modifier.fillMaxSize()) {
                MemeTextField(boxCount = boxCount)
                Spacer(modifier = Modifier.height(8.dp))
                DetailScreenButton(
                    { onPreviewClick() },
                    { onSaveClick() },
                    isFailed
                )
            }
        }
    }
}