package com.plcoding.jetpackcomposepokedex.memedetail

import android.Manifest
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPermissionsApi
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
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scaffoldState = rememberScaffoldState()

    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )

    LaunchedEffect(key1 = true) {
        viewModel.setMemeTextList(boxCount)
        viewModel.setTextList(boxCount)
        viewModel.getMemeInfo(memeTextList.value, memeId)
    }

    LaunchedEffect(key1 = true) {
        viewModel.snackbarFlow.collectLatest { event ->
            when (event) {
                is SnackbarEvent.ShowPhotoSavedGallerySnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is SnackbarEvent.ShowPermanentlyDeniedSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is SnackbarEvent.SomeErrorWhenSavingSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color.Transparent
    ) {
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
                    memeInfo = memeInfoState.value
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            MemeTextField(boxCount = boxCount)
            Spacer(modifier = Modifier.height(8.dp))
            DetailScreenButton(
                { viewModel.getMemeInfo(textList.value, memeId) },
                { viewModel.onSaveDialogOpen(memeId) }
            )
            if (alertDialogVisible.value) SaveAlertDialog(
                { viewModel.onSaveDialogDismiss() },
                {
                    viewModel.viewModelScope.launch {
                        viewModel.onSave(
                            permissionsState,
                            context,
                            memeInfoState.value.data?.data?.url
                        )
                    }
                },
                {
                    viewModel.viewModelScope.launch {
                        viewModel.onShare(
                            memeInfoState.value.data?.data?.url,
                            context,
                            permissionsState,
                        )
                    }
                },
                memeInfoState.value.isLoading
            )
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