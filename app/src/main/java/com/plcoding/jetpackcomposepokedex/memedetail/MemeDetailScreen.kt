package com.plcoding.jetpackcomposepokedex.memedetail

import android.util.Log
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.plcoding.jetpackcomposepokedex.R
import com.plcoding.jetpackcomposepokedex.data.models.MemeListEntry
import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme.Meme
import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme_list.MemeList
import com.plcoding.jetpackcomposepokedex.util.Resource

@Composable
fun MemeDetailScreen(
    viewModel: MemeDetailViewModel = hiltViewModel(),
    navController: NavController,
    memeId: String,
    boxCount: Int
) {

    val textList = viewModel.textList
    val memeTextList = viewModel.memeTextList
    val memeInfoState = viewModel.memeInfoState
    val alertDialogVisible = viewModel.alertDialogVisible

    LaunchedEffect(key1 = true) {
        viewModel.setMemeTextList(boxCount)
        viewModel.setTextList(boxCount)
        viewModel.getMemeInfo(memeTextList.value, memeId)
    }

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
                memeInfo = memeInfoState.value
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        MemeTextField(boxCount = boxCount)
        Spacer(modifier = Modifier.height(8.dp))
        DetailScreenButton(
            { viewModel.getMemeInfo(textList.value, memeId) },
            { viewModel.onSaveDialogOpen() }
        )
        if(alertDialogVisible.value) SaveAlertDialog {
            viewModel.onSaveDialogDismiss()
        }
    }
}

@Composable
fun MemeDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colors.background,
                        Color.Transparent
                    )
                )
            )
            .fillMaxHeight(0.1f)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxHeight()
                .fillMaxWidth(0.2f)
                .clickable {
                    navController.popBackStack()
                }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun MemeDetailStateWrapper(
    memeInfo: MemeInfoState,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    Image(
        painter = rememberCoilPainter
            (request = memeInfo.data?.data?.url, fadeIn = true),
        contentDescription = memeInfo.data?.data?.page_url,
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Fit
    )
    if (memeInfo.isLoading) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary,
            modifier = loadingModifier
        )
    }
    if (memeInfo.error.isNotBlank()) {
        Text(
            text = memeInfo.error!!,
            color = Color.Red,
            modifier = modifier,
            textAlign = TextAlign.Center
        )
    }
}