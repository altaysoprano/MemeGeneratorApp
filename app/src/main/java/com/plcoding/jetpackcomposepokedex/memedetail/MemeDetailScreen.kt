package com.plcoding.jetpackcomposepokedex.memedetail

import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.plcoding.jetpackcomposepokedex.data.models.MemeListEntry
import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme.Meme
import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme_list.MemeList
import com.plcoding.jetpackcomposepokedex.util.Resource

@Composable
fun MemeDetailScreen(
    viewModel: MemeDetailViewModel = hiltViewModel(),
    navController: NavController,
    text0: String,
    text1: String,
    memeId: String,
    memeImageSize: Dp = 200.dp,
    topPadding: Dp = 20.dp
) {
    val memeInfo = produceState<Resource<Meme>>(initialValue = Resource.Loading()) {
        value = viewModel.getMemeInfo(text0, text1, memeId)
    }.value
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
    ) {
        MemeDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
                .align(Alignment.TopCenter)
        )
        MemeDetailStateWrapper(
            memeInfo = memeInfo,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding + memeImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(50.dp)
                .align(Alignment.Center)
        )
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (memeInfo is Resource.Success) {
                memeInfo.data?.data.let {
                    Image(
                        painter = rememberCoilPainter(request = it?.url),
                        contentDescription = it?.page_url,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(memeImageSize)
                            .offset(y = topPadding)
                    )
                }
            }
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
            )
        }
    }
}

@Composable
fun MemeDetailStateWrapper(
    memeInfo: Resource<Meme>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (memeInfo) {
        is Resource.Success -> {

        }
        is Resource.Error -> {
            Text(
                text = memeInfo.message!!,
                color = Color.Red,
                modifier = modifier
            )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
    }

}