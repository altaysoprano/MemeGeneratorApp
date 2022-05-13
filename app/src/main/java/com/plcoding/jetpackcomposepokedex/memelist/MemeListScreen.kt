package com.plcoding.jetpackcomposepokedex.memelist

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.google.accompanist.coil.rememberCoilPainter
import com.plcoding.jetpackcomposepokedex.data.models.MemeListEntry
import com.plcoding.jetpackcomposepokedex.ui.theme.RobotoCondensed

@Composable
fun MemeListScreen(
    navController: NavController,
    viewModel: MemeListViewModel = hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            SearchBar(
                hint = "Search Meme...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onSearch = {
                    viewModel.searchMemeList(it)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            MemeList(navController = navController)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {},
    viewModel: MemeListViewModel = hiltViewModel()
) {

    val searchText by viewModel.searchText

    val isFocused by viewModel.isFocused

    Box(modifier = modifier) {
        TextField(
            value = searchText,
            onValueChange = {
                viewModel.updateText(it)
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .onFocusChanged {
                    viewModel.updateFocused(it.isFocused && searchText.isEmpty())
                },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = hint,
                    color = Color.LightGray
                )
            },
            trailingIcon = {
                if (searchText.isNotBlank()) {
                    IconButton(onClick = {
                        viewModel.updateText("")
                        viewModel.searchMemeList(searchText)
                    }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "Clear",
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                }
            }
        )
        if (!isFocused) {
            viewModel.updateText("")
            viewModel.searchMemeList(searchText)
        }
    }
}

@Composable
fun MemeEntry(
    entry: MemeListEntry,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(modifier = Modifier
            .height(200.dp)
            .clickable {
                navController.navigate("meme_detail_screen/${entry.memeId}/${entry.boxCount}")
            }
        ) {
            Image(
                painter = rememberCoilPainter(
                    request = ImageRequest.Builder(LocalContext.current)
                        .data(entry.imageUrl)
                        .build()
                ),
                contentDescription = entry.memeName,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(entry.memeName, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }
    }
}

@Composable
fun MemeList(
    navController: NavController,
    viewModel: MemeListViewModel = hiltViewModel()
) {
    val memeListState by viewModel.memeListState

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = if (memeListState.data!!.size % 2 == 0) {
            memeListState.data!!.size / 2
        } else {
            memeListState.data!!.size / 2 + 1
        }
        items(itemCount) {
            MemeRow(rowIndex = it, entries = memeListState.data!!, navController = navController)
        }
    }

    if (memeListState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = Modifier.align(Center)
            )
        }
    }
}

@Composable
fun MemeRow(
    rowIndex: Int,
    entries: List<MemeListEntry>,
    navController: NavController
) {
    Column {
        Row {
            MemeEntry(
                entry = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (entries.size >= rowIndex * 2 + 2) {
                MemeEntry(
                    entry = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

