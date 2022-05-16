package com.plcoding.jetpackcomposepokedex.memedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable 
fun MemeDetailScreenPortrait(
    navController: NavController,
    memeInfo: MemeInfoState,
    boxCount: Int,
    onPreviewClick: () -> Unit,
    onSaveClick: () -> Unit
    ) {
    
    val focusManager = LocalFocusManager.current

    Column( //Buradan
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
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            }
    ) {
        MemeDetailTopSection(navController = navController)
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            contentAlignment = Alignment.Center
        ) {
            MemeDetailStateWrapper(
                memeInfo = memeInfo
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        MemeTextField(boxCount = boxCount)
        Spacer(modifier = Modifier.height(8.dp))
        DetailScreenButton(
            { onPreviewClick() },
            { onSaveClick() }
        )

    }
}
  
